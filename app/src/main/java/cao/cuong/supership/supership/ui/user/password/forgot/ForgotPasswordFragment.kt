package cao.cuong.supership.supership.ui.user.password.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.response.RequestResetPassResponse
import cao.cuong.supership.supership.extension.isValidateEmail
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.user.UserActivity
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class ForgotPasswordFragment : BaseFragment() {

    internal lateinit var ui: ForgotPasswordFragmentUI
    internal lateinit var eventRequestResetSuccess: (response: RequestResetPassResponse) -> Unit

    private lateinit var viewModel: ForgotPasswordFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ForgotPasswordFragmentViewModel()
        ui = ForgotPasswordFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {
    }

    internal fun eventResetButtonClicked() {
        val email = ui.edtEmail.text.toString()
        if (email.isValidateEmail()) {
            viewModel.requestResetPassword(email)
                    .observeOnUiThread()
                    .doOnSubscribe({
                        handleUpdateProgressDialogStatus(true)
                    })
                    .doFinally {
                        handleUpdateProgressDialogStatus(false)
                    }
                    .subscribe(this::handleRequestSuccess, this::handleApiError)
        } else {
            context.showOkAlert(R.string.notification, R.string.validateEmail)
        }
    }

    private fun handleRequestSuccess(response: RequestResetPassResponse) {
        context.showOkAlert(R.string.notification, context.getString(R.string.requestResetSuccess, ui.edtEmail.text.toString())) {
            (activity as? UserActivity)?.openResetPasswordFragment()
        }
    }
}
