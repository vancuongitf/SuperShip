package cao.cuong.supership.supership.ui.user.login

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.style.UnderlineSpan
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
class LoginDialogUI : AnkoComponent<LoginDialog> {

    internal lateinit var edtUserName: EditText
    internal lateinit var edtPassword: EditText

    @SuppressLint("RtlHardcoded")
    override fun createView(ui: AnkoContext<LoginDialog>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            view {}.lparams(matchParent, 0) {
                weight = 0.8f
            }

            textView(R.string.app_name) {
                textColorResource = R.color.colorPink
                gravity = Gravity.CENTER
                textSizeDimen = R.dimen.splashActivityAppNameSize
            }

            view {}.lparams(matchParent, 0) {
                weight = 0.8f
            }

            commonEditText(R.string.userName) {
                edtUserName = editText
            }.lparams(matchParent, wrapContent)

            commonEditText(R.string.password, true) {
                edtPassword = editText
            }.lparams(matchParent, wrapContent) {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView {
                val source = context.getString(R.string.forgotPassword)
                val spannableString = SpannableString(source)
                textColorResource = R.color.colorWhite
                enableHighLightWhenClicked()
                spannableString.setSpan(UnderlineSpan(), 0, source.length, 0)
                text = spannableString
                gravity = Gravity.RIGHT
                onClick {
                    owner.eventForgotPasswordClicked()
                }
            }.lparams(matchParent, wrapContent)

            textView(R.string.login) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorBlue
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventLoginButtonClicked()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.signUp) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorPink
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventSignUpButtonClicked()
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