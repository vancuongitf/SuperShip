package cao.cuong.supership.supership.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.BillAddress
import cao.cuong.supership.supership.data.model.RxEvent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.user.forgotpassword.ForgotDialog
import cao.cuong.supership.supership.ui.user.login.LoginDialog
import cao.cuong.supership.supership.ui.user.resetpassword.ResetPasswordDialog
import cao.cuong.supership.supership.ui.user.signup.SignUpDialog
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class AccountFragment : BaseFragment() {

    private lateinit var ui: AccountFragmentUI
    private lateinit var viewModel: AccountFragmentViewModel
    private val loginDialog = LoginDialog()
    private val forgotDialog = ForgotDialog()
    private val signUpDialog = SignUpDialog()
    private var resetPasswordDialog: ResetPasswordDialog? = null
    private val billAddresses = mutableListOf<BillAddress>()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = AccountFragmentUI()
        ui.billAddressAdapter = BillAddressAdapter(billAddresses)
        viewModel = AccountFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxBus.listen(UpdateAccountUI::class.java).subscribe(this::updateUI)
        updateUI(UpdateAccountUI())
    }

    override fun onBindViewModel() {
    }

    internal fun eventLoginButtonClick() {

        forgotDialog.eventRequestResetSuccess = {
            resetPasswordDialog = ResetPasswordDialog.getNewInstance(it.userId, it.userName)
            resetPasswordDialog?.onResetSuccess = {
                loginDialog.viewModel.saveAccessToken(it)
                loginDialog.dismiss()
                forgotDialog.dismiss()
            }
            resetPasswordDialog?.let {
                if (!it.showsDialog) {
                    it.show(childFragmentManager, null)
                }
            }
        }

        loginDialog.signUpButtonClick = {
            signUpDialog.onCreateUserSuccess = {
                signUpDialog.dismiss()
            }
            if (!signUpDialog.isVisible) {
                signUpDialog.show(childFragmentManager, null)
            }
        }
        loginDialog.forgotPasswordClick = {
            if (!forgotDialog.isVisible) {
                forgotDialog.show(childFragmentManager, null)
            }
        }
        if (!loginDialog.isVisible) {
            loginDialog.show(childFragmentManager, null)
        }
    }

    private fun updateUI(event: UpdateAccountUI) {
        if (viewModel.isLogin()) {
            ui.llNonLogin.visibility = View.GONE
            ui.llLogin.visibility = View.VISIBLE
        } else {
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
        }
    }

    fun logOutClick() {
        viewModel.logOut()
    }

    fun eventChangeFullNameClick() {
    }

    fun eventChangePhoneNumberClick() {
    }

    fun eventChangeEmailClick() {

    }

    fun eventChangePasswordButtonClicked() {

    }
}
