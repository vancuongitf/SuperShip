package cao.cuong.supership.supership.ui.order.drink

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.FragmentManager
import android.view.Gravity
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager

class CartDrinkFragmentUI(fm: FragmentManager, adapterSize: Int) : AnkoComponent<CartDrinkFragment> {

    internal val cartDrinkAdapter = CartDrinkAdapter(fm, adapterSize)

    override fun createView(ui: AnkoContext<CartDrinkFragment>) = with(ui) {
        verticalLayout {
            isClickable = true
            backgroundResource = R.drawable.bg_login_image

            appBarLayout {
                fitsSystemWindows = true

                collapsingToolbarLayout {

                    toolbar {
                        setContentInsetsAbsolute(0, 0)
                        backgroundColorResource = R.color.colorWhite

                        verticalLayout {
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

                                textView(R.string.orderedDrinkList) {
                                    textSizeDimen = R.dimen.storeTitleTextSize
                                    textColorResource = R.color.colorBlack
                                    singleLine = true
                                }.lparams(wrapContent, wrapContent)
                            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

                        }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

                        view {
                            backgroundResource = R.color.colorGrayVeryLight
                        }.lparams(matchParent, dip(1))

                    }.lparams(matchParent, wrapContent) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }

                }.lparams(matchParent, wrapContent) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(matchParent, wrapContent) {

            }



            view {
                backgroundResource = R.color.colorGrayVeryLight
            }.lparams(matchParent, dip(1))

            viewPager {
                id = R.id.viewPagerOrderedDrinks
                adapter = cartDrinkAdapter
            }.lparams(matchParent, matchParent)
        }
    }
}
