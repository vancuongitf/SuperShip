package cao.cuong.supership.supership.ui.user.forgotpassword

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
class ForgotDialogUI : AnkoComponent<ForgotDialog> {

    internal lateinit var edtEmail: EditText

    override fun createView(ui: AnkoContext<ForgotDialog>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            relativeLayout {
                gravity = Gravity.CENTER

                textView(R.string.app_name) {
                    textColorResource = R.color.colorPink
                    gravity = Gravity.CENTER
                    textSizeDimen = R.dimen.splashActivityAppNameSize
                }

            }.lparams(matchParent, 0) {
                weight = 1f
            }

            commonEditText(R.string.email) {
                edtEmail = editText
            }.lparams(matchParent, wrapContent)

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
