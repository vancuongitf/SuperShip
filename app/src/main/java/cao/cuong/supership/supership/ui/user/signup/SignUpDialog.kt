package cao.cuong.supership.supership.ui.user.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseBottomSheetDialog
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class SignUpDialog : BaseBottomSheetDialog() {

    internal var onCreateUserSuccess: () -> Unit = {}

    private lateinit var ui: SignUpDialogUI
    private var viewModel = SignUpFragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = SignUpDialogUI()
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
        if (fullName.isValidateFullName()) {
            if (userName.isValidateUserName()) {
                if (pass.isValidatePassWord()) {
                    if (rePass == pass) {
                        if (email.isValidateEmail()) {
                            if (phone.isValidatePhoneNumber()) {
                                pass = pass.sha1()
                                val userBody = CreateUserBody(fullName, userName, pass, email, phone, 0, 0)
                                viewModel.createUser(userBody)
                                        .observeOnUiThread()
                                        .doOnSubscribe {
                                            handleUpdateProgressStatus(true)
                                        }
                                        .doFinally {
                                            handleUpdateProgressStatus(false)
                                        }
                                        .subscribe(this::handleCreateUserSuccess, this::handleApiError)
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
            onCreateUserSuccess()
        }
    }
}
