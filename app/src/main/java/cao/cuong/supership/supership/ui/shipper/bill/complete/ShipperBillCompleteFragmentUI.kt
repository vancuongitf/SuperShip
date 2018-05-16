package cao.cuong.supership.supership.ui.shipper.bill.complete

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ShipperBillCompleteFragmentUI : AnkoComponent<ShipperBillCompleteFragment> {

    internal lateinit var edtConfirmCode: EditText

    override fun createView(ui: AnkoContext<ShipperBillCompleteFragment>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundResource = R.drawable.bg_login_image
            padding = dimen(R.dimen.accountFragmentLoginPadding)

            view {

            }.lparams(matchParent, 0) {
                weight = 1f
            }

            textView(R.string.completeBill) {
                gravity = Gravity.CENTER_HORIZONTAL
                typeface = Typeface.DEFAULT_BOLD
                textSizeDimen = R.dimen.signUpTitleSize
                textColorResource = R.color.colorPink
            }

            view {

            }.lparams(matchParent, 0) {
                weight = 1f
            }

            commonEditText(R.string.confirmCode) {
                edtConfirmCode = editText
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }.lparams(matchParent, wrapContent)

            textView(R.string.confirmMessage) {
                gravity = Gravity.START
                textColorResource = R.color.colorWhite
            }.lparams(wrapContent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.completeBill) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorBlue
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventCompleteClick()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            view {

            }.lparams(matchParent, 0) {
                weight = 2f
            }
        }
    }
}
