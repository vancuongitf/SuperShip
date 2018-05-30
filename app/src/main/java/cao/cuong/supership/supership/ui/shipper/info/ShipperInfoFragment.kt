package cao.cuong.supership.supership.ui.shipper.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.isValidatePhoneNumber
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import cao.cuong.supership.supership.ui.shipper.cash.CashActivity
import org.jetbrains.anko.AnkoContext
import javax.net.ssl.HttpsURLConnection

class ShipperInfoFragment : BaseFragment() {

    private lateinit var ui: ShipperInfoFragmentUI
    private lateinit var viewModel: ShipperInfoFragmentViewModel
    private var shipperId = -1L
    private var currentPhone = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = ShipperInfoFragmentUI()
        viewModel = ShipperInfoFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi(UpdateAccountUI(), true)
        viewModel.updateProgressDialogStatus
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        RxBus.listen(UpdateAccountUI::class.java)
                .observeOnUiThread()
                .subscribe({ updateUi(UpdateAccountUI(), false) })
    }

    override fun onBindViewModel() {
    }

    override fun handleApiError(throwable: Throwable?) {
        if (throwable is ApiException && throwable.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            RxBus.publish(OpenUserActivityAlert())
        } else {
            super.handleApiError(throwable)
        }
    }

    internal fun onEditInfoClicked() {
        ui.edtPhoneNumber.editText.isEnabled = true
        ui.rlEditInfo.visibility = View.GONE
        ui.rlSubmitNewInfo.visibility = View.VISIBLE
        ui.edtPhoneNumber.editText.requestFocus()
        ui.edtPhoneNumber.editText.setSelection(currentPhone.length)
    }

    internal fun onSubmitNewInfoClicked() {
        hideKeyboard()
        val phone = ui.edtPhoneNumber.editText.text.toString().trim()
        var message = ""
        if (phone == currentPhone) {
            ui.edtPhoneNumber.editText.isEnabled = false
            ui.rlEditInfo.visibility = View.VISIBLE
            ui.rlSubmitNewInfo.visibility = View.GONE
        } else {
            if (phone.isValidatePhoneNumber()) {
                context.showConfirmAlert(R.string.updateInfoConfirm) {
                    viewModel.updateShipperInfo(phone)
                            .observeOnUiThread()
                            .subscribe(this::handleUpdateInfoSuccess, this::handleApiError)
                }
            } else {
                message = "Vui lòng nhập số điện thoại hợp lệ."
            }
        }
        if (message.isNotEmpty()) {
            handleApiError(Throwable(message))
        }
    }

    internal fun eventReloadClicked() {
    }

    internal fun eventLoginButtonClick() {
        val intent = Intent(context, UserActivity::class.java)
        startActivityForResult(intent, UserActivity.USER_ACTIVITY_REQUEST_CODE)
    }

    internal fun eventCashClicked() {
        val intent = Intent(context, CashActivity::class.java)
        intent.putExtras(Bundle().apply {
            putLong(CashActivity.KEY_SHIPPER_ID, shipperId)
        })
        startActivity(intent)
    }

    internal fun eventChangePasswordButtonClicked() {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    internal fun logOutClick() {
        viewModel.logOut()
        ui.llNonLogin.visibility = View.VISIBLE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.GONE
    }

    private fun updateUi(event: UpdateAccountUI, getNewData: Boolean = false) {
        if (viewModel.isLogin()) {
            ui.llNonLogin.visibility = View.GONE
            ui.llLogin.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
            val localShipper = viewModel.getLocalShipperInfo()
            if (localShipper == null || getNewData) {
                viewModel.getShipperInfo()
                        .observeOnUiThread()
                        .subscribe(this::handleGetShipperInfoSuccess, this::handleGetShipperInfoFail)
            } else {
                handleGetShipperInfoSuccess(localShipper)
            }
        } else {
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
        }
    }

    private fun handleGetShipperInfoSuccess(shipper: Shipper) {
        ui.rlEditInfo.visibility = View.VISIBLE
        ui.rlSubmitNewInfo.visibility = View.GONE
        ui.edtPhoneNumber.editText.isEnabled = true
        currentPhone = shipper.phone
        shipperId = shipper.id
        ui.edtFullName.editText.setText(shipper.fullName)
        ui.edtPersonalId.editText.setText(shipper.personalId)
        ui.edtBirthDay.editText.setText(shipper.birthDay)
        ui.edtAddress.editText.setText(shipper.address)
        ui.edtEmail.editText.setText(shipper.email)
        ui.edtPhoneNumber.editText.setText(shipper.phone)
        ui.edtDeposit.editText.setText(context.getString(R.string.billPrice, shipper.deposit))
        ui.llLogin.visibility = View.VISIBLE
        viewModel.saveShipperInfo(shipper)
    }

    private fun handleGetShipperInfoFail(throwable: Throwable) {
        ui.tvReload.visibility = View.VISIBLE
        ui.llLogin.visibility = View.GONE
        ui.llNonLogin.visibility = View.GONE
    }

    private fun handleUpdateInfoSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            ui.edtPhoneNumber.editText.isEnabled = false
            viewModel.getLocalShipperInfo()?.let {
                it.phone = ui.edtPhoneNumber.editText.text.toString().trim()
                handleGetShipperInfoSuccess(it)
            }
        }
    }
}
