package cao.cuong.supership.supership.ui.customer.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.BillAddress
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.isValidateFullName
import cao.cuong.supership.supership.extension.isValidatePhoneNumber
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class AccountFragment : BaseFragment() {

    private lateinit var ui: AccountFragmentUI
    private lateinit var viewModel: AccountFragmentViewModel
    private val billAddresses = mutableListOf<BillAddress>()
    private var userId = -1L
    private lateinit var userInfo: UserInfo

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = AccountFragmentUI()
        ui.billAddressAdapter = BillAddressAdapter(billAddresses)
        viewModel = AccountFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxBus.listen(UpdateAccountUI::class.java).observeOnUiThread().subscribe({ updateUI(it, false) })
        updateUI(UpdateAccountUI(), true)
        viewModel.updateProgressDialogStatus
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
    }

    override fun onBindViewModel() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                UserActivity.USER_ACTIVITY_REQUEST_CODE -> {
                    RxBus.publish(UpdateAccountUI())
                }
            }
        }
    }

    internal fun eventLoginButtonClick() {
        val intent = Intent(context, UserActivity::class.java)
        startActivityForResult(intent, UserActivity.USER_ACTIVITY_REQUEST_CODE)
    }

    internal fun logOutClick() {
        viewModel.logOut()
    }

    internal fun eventStoreListClicked() {
        val intent = Intent(activity, StoreActivity::class.java)
        activity.startActivity(intent)
    }

    internal fun eventChangePasswordButtonClicked() {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    internal fun eventReloadClicked() {
        updateUI(UpdateAccountUI())
    }

    internal fun onEditButtonClicked() {
        ui.rlEditInfo.visibility = View.GONE
        ui.rlSubmitNewInfo.visibility = View.VISIBLE
        ui.edtFullName.editText.isEnabled = true
        ui.edtPhoneNumber.editText.isEnabled = true
    }

    internal fun onSubmitNewInfoClicked() {
        hideKeyboard()
        val newName = ui.edtFullName.editText.text.toString().trim()
        val newPhone = ui.edtPhoneNumber.editText.text.toString().trim()
        if (newName == userInfo.fullName && newPhone == userInfo.phoneNumber) {
            ui.edtFullName.editText.isEnabled = false
            ui.edtPhoneNumber.editText.isEnabled = false
            ui.rlSubmitNewInfo.visibility = View.GONE
            ui.rlEditInfo.visibility = View.VISIBLE
        } else {
            var message = ""
            if (newName.isValidateFullName()) {
                if (newPhone.isValidatePhoneNumber()) {
                    viewModel.updateInfo(userId, newName, newPhone)
                            .observeOnUiThread()
                            .subscribe(this::handleUpdateUserInfoSuccess, this::handleApiGetInfoError)
                } else {
                    message = "Vui lòng nhập số điện thoại hợp lệ."
                }
            } else {
                message = "Vui lòng nhập tên hợp lệ."
            }
            if (message.isNotEmpty()) {
                context.showOkAlert(Throwable(message))
            }
        }
    }

    private fun updateUI(event: UpdateAccountUI, getNewData: Boolean = false) {
        if (viewModel.isLogin()) {
            val localUserInfo = viewModel.getLocalUserInfo()
            if (localUserInfo == null || getNewData) {
                ui.llNonLogin.visibility = View.GONE
                ui.llLogin.visibility = View.GONE
                ui.tvReload.visibility = View.GONE
                viewModel.getUserInfo()
                        .observeOnUiThread()
                        .subscribe(this::handleGetUserInfo, this::handleApiGetInfoError)
            } else {
                handleGetUserInfo(localUserInfo)
            }
        } else {
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
        }
    }

    private fun handleGetUserInfo(userInfo: UserInfo) {
        userId = userInfo.id
        this.userInfo = userInfo
        ui.edtFullName.editText.isEnabled = false
        ui.edtPhoneNumber.editText.isEnabled = false
        ui.edtFullName.editText.setText(userInfo.fullName)
        ui.edtEmail.editText.setText(userInfo.email)
        ui.edtPhoneNumber.editText.setText(userInfo.phoneNumber)
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.VISIBLE
        ui.tvReload.visibility = View.GONE
        ui.rlEditInfo.visibility = View.VISIBLE
        ui.rlSubmitNewInfo.visibility = View.GONE
        viewModel.saveUserInfo(userInfo)
    }

    private fun handleApiGetInfoError(throwable: Throwable) {
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.VISIBLE
        handleApiError(throwable)
    }

    private fun handleUpdateUserInfoSuccess(messageResponse: MessageResponse) {
        userInfo.fullName = ui.edtFullName.editText.text.toString().trim()
        userInfo.phoneNumber = ui.edtPhoneNumber.editText.text.toString().trim()
        ui.edtFullName.editText.isEnabled = false
        ui.edtPhoneNumber.editText.isEnabled = false
        viewModel.saveUserInfo(userInfo)
        context.showOkAlert(R.string.notification, messageResponse.message)
    }
}
