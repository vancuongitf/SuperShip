package cao.cuong.supership.supership.ui.store.info

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 *
 * @author at-cuongcao.
 */
class StoreInfoFragmentUI : AnkoComponent<StoreInfoFragment> {

    internal lateinit var imgStoreAvatar: ImageView
    internal lateinit var tvStoreNameTitle: TextView
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvAddress: TextView
    internal lateinit var tvStarRate: TextView

    override fun createView(ui: AnkoContext<StoreInfoFragment>) = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            isClickable = true

            appBarLayout {
                fitsSystemWindows = true


                collapsingToolbarLayout {

                    verticalLayout {
                        imgStoreAvatar = imageView {
                            backgroundResource = R.mipmap.ic_launcher_round
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams(ctx.getWidthScreen(), ctx.getWidthScreen())

                        verticalLayout {
                            padding = dimen(R.dimen.accountFragmentLoginPadding)

                            tvStoreName = textView {
                                setTypeface(null, Typeface.BOLD)
                                textColorResource = R.color.colorBlack
                            }

                            tvAddress = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorGray
                                maxLines = 2
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imageView(R.drawable.ic_star_gold)
                                        .lparams(dimen(R.dimen.storeItemUIStarIconSize), dimen(R.dimen.storeItemUIStarIconSize)) {
                                            rightMargin = dimen(R.dimen.storeItemUITvAddressTopMargin)
                                        }

                                tvStarRate = textView {
                                    textSizeDimen = R.dimen.secondaryTextSize
                                    textColorResource = R.color.colorBlue
                                }
                            }.lparams(matchParent, wrapContent) {
                                bottomMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                        }.lparams(matchParent, wrapContent)
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dimen(R.dimen.toolBarHeight)
                    }

                    toolbar {
                        setContentInsetsAbsolute(0, 0)
                        backgroundColorResource = R.color.colorWhite

                        verticalLayout {
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

                                tvStoreNameTitle = textView {
                                    textSizeDimen = R.dimen.storeTitleTextSize
                                    textColorResource = R.color.colorBlue
                                    setTypeface(null, Typeface.BOLD)
                                }
                            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

                            view {
                                backgroundResource = R.color.colorGrayVeryLight
                            }.lparams(matchParent, dip(1))

                        }.lparams(matchParent, wrapContent)
                    }.lparams(matchParent, wrapContent) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }

                }.lparams(matchParent, wrapContent) {
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(matchParent, wrapContent) {

            }

            nestedScrollView {

                recyclerView {
                    topPadding = dimen(R.dimen.toolBarHeight)
                    id = R.id.recyclerViewDrink
                    layoutManager = LinearLayoutManager(ctx)
                    val drinkAdapter = DrinkAdapter(mutableListOf())
                    adapter = drinkAdapter
                }.lparams(matchParent, matchParent)

            }.lparams(matchParent, wrapContent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            verticalLayout {
                verticalPadding = dip(1)
                backgroundColorResource = R.color.colorGrayVeryLight

                textView(R.string.menu) {
                    horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                    textColorResource = R.color.colorBlack
                    backgroundColorResource = R.color.colorWhite
                    setTypeface(null, Typeface.BOLD)
                    gravity = Gravity.CENTER_VERTICAL
                    textSizeDimen = R.dimen.menuTextSize
                }.lparams(matchParent, matchParent)

            }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}
