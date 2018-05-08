package cao.cuong.supership.supership.ui.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.RxEvent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseBottomSheetDialog
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */

class LoginDialog : BaseBottomSheetDialog() {

    internal lateinit var signUpButtonClick: () -> Unit
    internal lateinit var forgotPasswordClick: () -> Unit
    internal lateinit var viewModel: LoginDialogViewModel


    private lateinit var ui: LoginDialogUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = LoginDialogUI()
        viewModel = LoginDialogViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {
        addDisposables(
                viewModel
                        .loginStatusObserver
                        .observeOnUiThread()
                        .subscribe(this::handleLoginStatus),
                viewModel.updateProgressStatus
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateProgressStatus))
    }

    internal fun eventLoginButtonClicked() {
        val user = ui.edtUserName.text.toString()
        val pass = ui.edtPassword.text.toString()
        if (user.isValidateUserName() && pass.isValidatePassWord()) {
            viewModel.login(user, pass.sha1())
        } else {
            context.showOkAlert(R.string.notification, R.string.validateLogin)
        }
    }

    internal fun eventSignUpButtonClicked() {
        signUpButtonClick()
    }

    internal fun eventForgotPasswordClicked() {
        forgotPasswordClick()
    }

    private fun handleLoginStatus(notification: Notification<AccessToken>) {
        if (notification.isOnNext) {
            RxBus.publish(UpdateAccountUI())
            dismiss()
        } else if (notification.isOnError) {
            val value = notification.error
            handleApiError(value)
        }
    }
}
