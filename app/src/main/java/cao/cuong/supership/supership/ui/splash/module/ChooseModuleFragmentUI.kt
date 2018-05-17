package cao.cuong.supership.supership.ui.splash.module

import android.view.Gravity
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ChooseModuleFragmentUI : AnkoComponent<ChooseModuleFragment> {
    override fun createView(ui: AnkoContext<ChooseModuleFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            padding = dimen(R.dimen.accountFragmentLoginPadding)
            gravity = Gravity.CENTER

            textView(R.string.chooseModule) {
                textColorResource = R.color.colorBlack
                textSizeDimen = R.dimen.storeTitleTextSize
                gravity = Gravity.CENTER
            }

            textView(R.string.customerModule) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorBlue
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventCustomerClick()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.shipperModule) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorGray
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventShipperClick()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            textView(R.string.staff) {
                gravity = Gravity.CENTER_HORIZONTAL
                backgroundColorResource = R.color.colorGrayVeryLight
                enableHighLightWhenClicked()
                verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                onClick {
                    owner.eventStaffClick()
                }
            }.lparams(matchParent, wrapContent) {
                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }
        }
    }
}
