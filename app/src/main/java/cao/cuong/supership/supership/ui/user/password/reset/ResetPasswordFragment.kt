package cao.cuong.supership.supership.ui.user.password.reset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class ResetPasswordFragment : BaseFragment() {

    companion object {

        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"

        internal fun getNewInstance(userId: Int, userName: String): ResetPasswordFragment {
            val instance = ResetPasswordFragment()
            instance.arguments = Bundle().apply {
                putInt(KEY_USER_ID, userId)
                putString(KEY_USER_NAME, userName)
            }
            return instance
        }
    }

    internal var onResetSuccess: (accessToken: AccessToken) -> Unit = {}

    private lateinit var ui: ResetPasswordFragmentUI
    private lateinit var viewModel: ResetPassworFragmentViewModel
    private var userId: Int = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ResetPassworFragmentViewModel()
        ui = ResetPasswordFragmentUI(arguments.getString(KEY_USER_NAME))
        userId = arguments.getInt(KEY_USER_ID)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {
    }

    internal fun eventResetButtonClicked() {
        var pass = ui.edtPassword.text.toString()
        val rePass = ui.edtRetypePassword.text.toString()
        val otp = ui.edtOTPCode.text.toString()
        if (pass.isValidatePassWord() && otp.isValidateOTPCode() && pass == rePass) {
            pass = pass.sha1()
            viewModel.resetPassword(userId, pass, otp.toInt())
                    .observeOnUiThread()
                    .doOnSubscribe {
                        handleUpdateProgressDialogStatus(true)
                    }
                    .doFinally {
                        handleUpdateProgressDialogStatus(false)
                    }
                    .subscribe(this::handleResetSuccess, this::handleApiError)
        } else {
            context.showOkAlert(R.string.notification, R.string.validateInfo)
        }
    }

    private fun handleResetSuccess(accessToken: AccessToken) {
        context.showOkAlert(R.string.notification, R.string.resetPasswordSuccess) {
            onResetSuccess(accessToken)
        }
    }
}
