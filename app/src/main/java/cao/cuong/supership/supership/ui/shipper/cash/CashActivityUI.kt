package cao.cuong.supership.supership.ui.shipper.cash

import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class CashActivityUI : AnkoComponent<CashActivity> {

    internal lateinit var edtCash: EditText

    override fun createView(ui: AnkoContext<CashActivity>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundResource = R.drawable.bg_login_image

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }

                        imageView(R.drawable.ic_back_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                    textView(R.string.cash) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }


                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            verticalLayout {

                padding = dimen(R.dimen.accountFragmentLoginPadding)
                gravity = Gravity.CENTER_VERTICAL

                commonEditText(R.string.cashCount) {
                    edtCash = editText
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                }.lparams(matchParent, wrapContent)

                textView(R.string.cash) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    backgroundColorResource = R.color.colorBlue
                    enableHighLightWhenClicked()
                    verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                    onClick {
                        owner.eventOnCashClicked()
                    }
                }.lparams(matchParent, wrapContent) {
                    verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
