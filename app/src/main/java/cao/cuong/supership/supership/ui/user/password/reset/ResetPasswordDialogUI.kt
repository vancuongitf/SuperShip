package cao.cuong.supership.supership.ui.user.password.reset

import android.view.Gravity
import android.widget.EditText
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getHeightScreen
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author at-cuongcao.
 */
class ResetPasswordDialogUI(private val userName: String) : AnkoComponent<ResetPasswordDialog> {

    internal lateinit var edtUSerName: EditText
    internal lateinit var edtPassword: EditText
    internal lateinit var edtRetypePassword: EditText
    internal lateinit var edtOTPCode: EditText

    override fun createView(ui: AnkoContext<ResetPasswordDialog>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            relativeLayout {
                gravity = Gravity.CENTER

                textView(R.string.app_name) {
                    textColorResource = R.color.colorRed
                    gravity = Gravity.CENTER
                    textSizeDimen = R.dimen.splashActivityAppNameSize
                }

            }.lparams(matchParent, 0) {
                weight = 1f
            }

            commonEditText(R.string.userName) {
                editText.isEnabled = false
                edtUSerName = editText
                edtUSerName.setText(userName)
            }.lparams(matchParent, wrapContent)

            commonEditText(R.string.password, true) {
                edtPassword = editText
            }.lparams(matchParent, wrapContent) {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            commonEditText(R.string.retypePassword, true) {
                edtRetypePassword = editText
            }.lparams(matchParent, wrapContent) {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            commonEditText(R.string.otpCode) {
                edtOTPCode = editText
            }.lparams(matchParent, wrapContent) {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.resetPassword) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorPink
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventResetButtonClicked()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            view {}.lparams(matchParent, 0) {
                weight = 1f
            }
        }
    }
}
