package cao.cuong.supership.supership.ui.customer.user.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */

class LoginFragment : BaseFragment() {

    internal lateinit var viewModel: LoginFragmentViewModel


    private lateinit var ui: LoginFragmentUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = LoginFragmentUI()
        viewModel = LoginFragmentViewModel(context)
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
                        .subscribe(this::handleUpdateProgressDialogStatus))
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
        (activity as? UserActivity)?.openSignUpFragment()
    }

    internal fun eventForgotPasswordClicked() {
        (activity as? UserActivity)?.openForgotPasswordFragment()
    }

    private fun handleLoginStatus(notification: Notification<AccessToken>) {
        if (notification.isOnNext) {
            (activity as? UserActivity)?.let {
                RxBus.publish(UpdateAccountUI())
                it.finish()
            }
        } else if (notification.isOnError) {
            val value = notification.error
            handleApiError(value)
        }
    }
}
