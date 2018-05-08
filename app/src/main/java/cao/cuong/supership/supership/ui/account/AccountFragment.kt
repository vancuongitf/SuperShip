package cao.cuong.supership.supership.ui.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.BillAddress
import cao.cuong.supership.supership.data.model.RxEvent.UpdateAccountUI
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.user.UserActivity
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = AccountFragmentUI()
        ui.billAddressAdapter = BillAddressAdapter(billAddresses)
        viewModel = AccountFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxBus.listen(UpdateAccountUI::class.java).observeOnUiThread().subscribe(this::updateUI)
        updateUI(UpdateAccountUI())
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

    internal fun eventChangeFullNameClick() {
    }

    internal fun eventChangePhoneNumberClick() {
    }

    internal fun eventChangeEmailClick() {

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

    private fun updateUI(event: UpdateAccountUI) {
        if (viewModel.isLogin()) {
            ui.llNonLogin.visibility = View.GONE
            ui.llLogin.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
            viewModel.getUserInfo()
                    .observeOnUiThread()
                    .subscribe(this::handleGetUserInfo, this::handleApiGetInfoError)
        } else {
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
        }
    }

    private fun handleGetUserInfo(userInfo: UserInfo) {
        userId = userInfo.id
        ui.edtFullName.editText.setText(userInfo.fullName)
        ui.edtEmail.editText.setText(userInfo.email)
        ui.edtPhoneNumber.editText.setText(userInfo.phoneNumber)
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.VISIBLE
        ui.tvReload.visibility = View.GONE
        viewModel.saveUserInfo(userInfo)
    }

    private fun handleApiGetInfoError(throwable: Throwable){
        handleApiError(throwable)
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.VISIBLE
    }
}
