package cao.cuong.supership.supership.ui.customer.user.login

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
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
class LoginFragmentUI : AnkoComponent<LoginFragment> {

    internal lateinit var edtUserName: EditText
    internal lateinit var edtPassword: EditText
    internal lateinit var tvSignUp: TextView
    internal lateinit var tvChangeModule: TextView

    @SuppressLint("RtlHardcoded")
    override fun createView(ui: AnkoContext<LoginFragment>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundColorResource = R.color.colorWhite
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            view {}.lparams(matchParent, 0) {
                weight = 0.8f
            }

            imageView(R.drawable.ic_shipper) {
            }.lparams(dip(100), dip(100)){
                gravity = Gravity.CENTER_HORIZONTAL
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
                textColorResource = R.color.colorBlack
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

            tvSignUp = textView(R.string.signUp) {
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

            tvChangeModule = textView(R.string.changeModule) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorGrayLight
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventChangeModuleClicked()
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