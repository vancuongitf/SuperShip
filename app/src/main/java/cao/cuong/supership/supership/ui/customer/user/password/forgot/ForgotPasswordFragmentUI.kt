package cao.cuong.supership.supership.ui.customer.user.password.forgot

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
class ForgotPasswordFragmentUI : AnkoComponent<ForgotPasswordFragment> {

    internal lateinit var edtEmail: EditText

    override fun createView(ui: AnkoContext<ForgotPasswordFragment>) = with(ui) {
        verticalLayout {
            lparams(context.getWidthScreen(), context.getHeightScreen())
            backgroundColorResource = R.color.colorWhite
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            relativeLayout {
                gravity = Gravity.CENTER

                imageView(R.drawable.ic_shipper) {
                }.lparams(dip(100), dip(100))

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
