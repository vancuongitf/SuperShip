package cao.cuong.supership.supership.ui.order.cart

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class CartFragmentUI:AnkoComponent<CartFragment> {

    internal lateinit var tvBillTitle: TextView

    override fun createView(ui: AnkoContext<CartFragment>) = with(ui){
        verticalLayout {
            toolbar {
                setContentInsetsAbsolute(0, 0)
                backgroundColorResource = R.color.colorWhite

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    imageView(R.drawable.ic_back) {
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }
                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    tvBillTitle = textView {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                        singleLine = true
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    relativeLayout {

                        padding = dimen(R.dimen.accountFragmentLoginPadding)
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onCheckClicked()
                        }

                        imageView(R.drawable.ic_check_button) {

                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            centerInParent()
                        }
                    }.lparams(dimen(R.dimen.toolBarHeight), dimen(R.dimen.toolBarHeight))

                }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            }.lparams(matchParent, wrapContent)

            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1))

            recyclerView {
                id = R.id.recyclerViewOrderDrink
                layoutManager = LinearLayoutManager(ctx)
            }.lparams(matchParent, matchParent)
        }
    }
}
