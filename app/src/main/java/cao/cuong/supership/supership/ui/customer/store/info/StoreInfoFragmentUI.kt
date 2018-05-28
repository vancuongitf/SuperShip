package cao.cuong.supership.supership.ui.customer.store.info

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.enableHighLightWhenClickedForListItem
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
class StoreInfoFragmentUI(drinks: MutableList<Drink>, private val orderCase: Boolean = false) : AnkoComponent<StoreInfoFragment> {

    internal lateinit var imgStoreAvatar: ImageView
    internal lateinit var imgChangeStoreStatus: ImageView
    internal lateinit var tvStoreNameTitle: TextView
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvAddress: TextView
    internal lateinit var tvStarRate: TextView
    internal lateinit var tvCartCount: TextView
    internal lateinit var imgCart: ImageView
    internal lateinit var rlEditInfo: RelativeLayout
    internal val drinkAdapter = DrinkAdapter(drinks)

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
                                enableHighLightWhenClickedForListItem()

                                onClick {
                                    owner.onCommentClicked()
                                }

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
                        backgroundColorResource = R.color.colorGrayLight

                        verticalLayout {
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

                                tvStoreNameTitle = textView {
                                    textSizeDimen = R.dimen.storeTitleTextSize
                                    textColorResource = R.color.colorBlue
                                }.lparams(0, wrapContent) {
                                    weight = 1f
                                }

                                rlEditInfo = relativeLayout {
                                    visibility = View.GONE
                                    relativeLayout {

                                        gravity = Gravity.CENTER_VERTICAL
                                        enableHighLightWhenClicked()
                                        onClick {
                                            owner.onEditInfoClicked()
                                        }

                                        imageView(R.drawable.ic_edit_note) {
                                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                        }
                                    }.lparams(wrapContent, matchParent)
                                }

                                relativeLayout {

                                    visibility = if (orderCase) {
                                        View.GONE
                                    } else {
                                        View.VISIBLE
                                    }

                                    gravity = Gravity.CENTER_VERTICAL
                                    enableHighLightWhenClicked()
                                    onClick {
                                        owner.changeStoreStatus()
                                    }

                                    imgChangeStoreStatus = imageView {
                                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    }
                                }.lparams(wrapContent, matchParent)

                                relativeLayout {

                                    padding = dimen(R.dimen.accountFragmentLoginPadding)
                                    enableHighLightWhenClicked()
                                    onClick {
                                        owner.onCartClicked()
                                    }

                                    visibility = if (orderCase) {
                                        View.VISIBLE
                                    } else {
                                        View.GONE
                                    }

                                    imgCart = imageView(R.drawable.ic_cart) {

                                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                        centerInParent()
                                    }

                                    tvCartCount = textView {
                                        textSizeDimen = R.dimen.cartCountTextSize
                                        setTypeface(null, Typeface.BOLD)
                                        textColorResource = R.color.colorRed
                                    }.lparams {
                                        alignParentRight()
                                        alignParentTop()
                                    }
                                }.lparams(dimen(R.dimen.toolBarHeight), dimen(R.dimen.toolBarHeight))

                            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

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
                    adapter = drinkAdapter
                }.lparams(matchParent, matchParent)

            }.lparams(matchParent, wrapContent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            linearLayout {
                verticalPadding = dip(1)
                backgroundColorResource = R.color.colorGrayVeryLight
                gravity = Gravity.CENTER_VERTICAL

                textView(R.string.menu) {
                    horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                    textColorResource = R.color.colorBlack
                    backgroundColorResource = R.color.colorWhite
                    setTypeface(null, Typeface.BOLD)
                    gravity = Gravity.CENTER_VERTICAL
                    textSizeDimen = R.dimen.menuTextSize
                }.lparams(0, matchParent) {
                    weight = 1f
                }

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    backgroundColorResource = R.color.colorWhite
                    visibility = if (orderCase) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }

                    relativeLayout {
                        onClick {
                            owner.optionalButtonClick()
                        }
                        enableHighLightWhenClicked()
                        imageView(R.drawable.ic_optional_list) {
                        }.lparams {
                            horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }

                    relativeLayout {
                        onClick {
                            owner.addDrinkClicked()
                        }
                        enableHighLightWhenClicked()
                        imageView(R.drawable.ic_add_button) {
                        }.lparams {
                            horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }
                }.lparams(wrapContent, matchParent)

            }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}
