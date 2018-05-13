package cao.cuong.supership.supership.ui.customer.user.signup

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
class SignUpFragmentUI : AnkoComponent<SignUpFragment> {

    internal lateinit var edtUserName: EditText
    internal lateinit var edtFullName: EditText
    internal lateinit var edtPassword: EditText
    internal lateinit var edtRetypePassword: EditText
    internal lateinit var edtPhoneNumber: EditText
    internal lateinit var edtEmail: EditText

    override fun createView(ui: AnkoContext<SignUpFragment>) = with(ui) {
        scrollView {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            verticalLayout {

                textView(R.string.signUpTitle) {
                    textColorResource = R.color.colorRed
                    textSizeDimen = R.dimen.signUpTitleSize
                    gravity = Gravity.CENTER
                    verticalPadding = dimen(R.dimen.signUpTitlePadding)
                }.lparams(matchParent, wrapContent)

                commonEditText(R.string.fullName) {
                    edtFullName = editText
                }.lparams(matchParent, wrapContent) {

                }

                commonEditText(R.string.userName) {
                    edtUserName = editText
                }.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

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

                commonEditText(R.string.phoneNumber) {
                    edtPhoneNumber = editText
                }.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                commonEditText(R.string.email) {
                    edtEmail = editText
                }.lparams(matchParent, wrapContent) {
                    topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                textView(R.string.signUpButton) {
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
            }
        }
    }
}