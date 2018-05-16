package cao.cuong.supership.supership.ui.shipper.account.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.request.CreateShipperBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class ShipperSignUpFragment : BaseFragment() {

    private lateinit var ui: ShipperSignUpFragmentUI
    private var viewModel = ShipperSignUpFragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = ShipperSignUpFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() = Unit

    internal fun eventSignUpButtonClicked() {
        val fullName = ui.edtFullName.text.toString().trim()
        val userName = ui.edtUserName.text.toString()
        var pass = ui.edtPassword.text.toString()
        val rePass = ui.edtRetypePassword.text.toString()
        val email = ui.edtEmail.text.toString()
        val phone = ui.edtPhoneNumber.text.toString()
        val personalId = ui.edtPersonalId.text.toString().trim()
        val address = ui.edtAddress.text.toString()
        if (fullName.isValidateFullName()) {
            if (userName.isValidateUserName()) {
                if (pass.isValidatePassWord()) {
                    if (rePass == pass) {
                        if (email.isValidateEmail()) {
                            if (phone.isValidatePhoneNumber()) {
                                if (personalId.isValidatePersonalId()) {
                                    if (address.isValidateStoreName()) {
                                        pass = pass.sha1()
                                        val shipperBody = CreateShipperBody(fullName, userName, pass, personalId, address, "1995-01-20", phone, email)
                                        viewModel.createUser(shipperBody)
                                                .observeOnUiThread()
                                                .doOnSubscribe {
                                                    handleUpdateProgressDialogStatus(true)
                                                }
                                                .doFinally {
                                                    handleUpdateProgressDialogStatus(false)
                                                }
                                                .subscribe(this::handleCreateUserSuccess, this::handleApiError)
                                    } else {
                                        context.showOkAlert(R.string.notification, "Địa chỉ không hợp lệ.")
                                    }
                                } else {
                                    context.showOkAlert(R.string.notification, "Số chứng minh nhân dân không đúng định dạng.")
                                }
                            } else {
                                context.showOkAlert(R.string.notification, R.string.validatePhone)
                            }
                        } else {
                            context.showOkAlert(R.string.notification, R.string.validateEmail)
                        }
                    } else {
                        context.showOkAlert(R.string.notification, R.string.validateRePassword)
                    }
                } else {
                    context.showOkAlert(R.string.notification, R.string.validatePassword)
                }
            } else {
                context.showOkAlert(R.string.notification, R.string.validateUserName)
            }
        } else {
            context.showOkAlert(R.string.notification, R.string.validateFullName)
        }
    }

    private fun handleCreateUserSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            activity.onBackPressed()
        }
    }
}
