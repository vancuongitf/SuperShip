package cao.cuong.supership.supership.ui.location.confirm

import android.view.Gravity
import android.view.View
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onClick

class ConfirmLocationFragmentUI:AnkoComponent<ConfirmLocationFragment> {
    override fun createView(ui: AnkoContext<ConfirmLocationFragment>) = with(ui) {
        verticalLayout {

            toolbar {

                backgroundColorResource = R.color.colorWhite
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    imageView(R.drawable.ic_back_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    textView(R.string.createStore) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    imageView(R.drawable.ic_check_button) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onConfirmClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            view {
                backgroundResource = R.color.colorGray
                alpha = 0.7f
            }.lparams(matchParent, dip(1)) {
                bottomMargin = dip(2)
            }
        }
    }
}
