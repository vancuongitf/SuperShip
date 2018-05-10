package cao.cuong.supership.supership.ui.user

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.user.login.LoginFragment
import cao.cuong.supership.supership.ui.user.password.change.ChangePasswordFragment
import cao.cuong.supership.supership.ui.user.password.forgot.ForgotPasswordFragment
import cao.cuong.supership.supership.ui.user.password.reset.ResetPasswordFragment
import cao.cuong.supership.supership.ui.user.signup.SignUpFragment
import org.jetbrains.anko.setContentView

class UserActivity : BaseActivity() {

    companion object {
        internal const val USER_ACTIVITY_REQUEST_CODE = 3011
    }

    private lateinit var ui: UserActivityUI
    private lateinit var viewModel: UserActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = UserActivityUI()
        viewModel = UserActivityViewModel(this)
        ui.setContentView(this)
        if (viewModel.isLogin()) {
            replaceFragment(R.id.userActivityContainer, ChangePasswordFragment())
        } else {
            replaceFragment(R.id.userActivityContainer, LoginFragment())
        }
    }

    override fun onBindViewModel() {

    }

    internal fun openSignUpFragment() {
        addFragment(R.id.userActivityContainer, SignUpFragment(), { it.animRightToLeft() }, SignUpFragment::class.java.simpleName)
    }

    internal fun openForgotPasswordFragment() {
        addFragment(R.id.userActivityContainer, ForgotPasswordFragment(), { it.animRightToLeft() }, ForgotPasswordFragment::class.java.simpleName)
    }

    internal fun openResetPasswordFragment() {
        addFragment(R.id.userActivityContainer, ResetPasswordFragment(), { it.animRightToLeft() }, ResetPasswordFragment::class.java.simpleName)
    }
}
