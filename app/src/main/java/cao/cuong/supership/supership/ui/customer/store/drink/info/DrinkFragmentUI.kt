package cao.cuong.supership.supership.ui.customer.store.drink.info

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getHeightScreen
import cao.cuong.supership.supership.extension.getWidthScreen
import cao.cuong.supership.supership.ui.customer.store.optional.adapter.OptionalAdapter
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
class DrinkFragmentUI(options: MutableList<DrinkOption>, private val isFromOrder: Boolean = false) : AnkoComponent<DrinkFragment> {

    internal lateinit var imgDrinkImage: ImageView
    internal lateinit var tvDrinkTitle: TextView
    internal lateinit var tvDrinkName: TextView
    internal lateinit var tvPrice: TextView
    internal lateinit var tvTotalPrice: TextView
    internal lateinit var tvBillCount: TextView
    internal lateinit var imgMinus: ImageView
    internal lateinit var imgAdd: ImageView
    internal lateinit var tvDrinkCount: TextView
    internal lateinit var edtNote: EditText
    internal lateinit var imgOrderCount: ImageView
    internal val drinkOptionAdapter = OptionalAdapter(options, OptionalAdapter.AdapterType.ORDER)

    override fun createView(ui: AnkoContext<DrinkFragment>) = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite
            isClickable = true

            appBarLayout {
                fitsSystemWindows = true


                collapsingToolbarLayout {

                    verticalLayout {
                        imgDrinkImage = imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams(ctx.getWidthScreen(), ctx.getWidthScreen())

                        verticalLayout {
                            padding = dimen(R.dimen.accountFragmentLoginPadding)

                            linearLayout {

                                gravity = Gravity.CENTER_VERTICAL

                                tvDrinkName = textView {
                                    setTypeface(null, Typeface.BOLD)
                                    textColorResource = R.color.colorBlack
                                    singleLine = true
                                }.lparams(0, wrapContent) {
                                    weight = 1f
                                }

                                linearLayout {

                                    visibility = if (isFromOrder) {
                                        View.VISIBLE
                                    } else {
                                        View.GONE
                                    }

                                    imgMinus = imageView(R.drawable.ic_minus_black) {
                                        enableHighLightWhenClicked()
                                        onClick {
                                            owner.onMinusDrinkClicked()
                                        }
                                    }.lparams(dimen(R.dimen.addDrinkButtonSize), dimen(R.dimen.addDrinkButtonSize)) {
                                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    }

                                    tvDrinkCount = textView {
                                        textColorResource = R.color.colorBlack
                                        setTypeface(null, Typeface.BOLD)
                                    }.lparams {
                                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    }

                                    imgAdd = imageView(R.drawable.ic_add_black) {
                                        enableHighLightWhenClicked()
                                        onClick {
                                            owner.onAddDrinkClicked()
                                        }
                                    }.lparams(dimen(R.dimen.addDrinkButtonSize), dimen(R.dimen.addDrinkButtonSize)) {
                                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    }
                                }

                            }.lparams(matchParent, wrapContent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                tvPrice = textView {
                                    textSizeDimen = R.dimen.secondaryTextSize
                                    textColorResource = R.color.colorBlack
                                    singleLine = true
                                }.lparams {
                                    verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    alignParentLeft()
                                }

                                tvTotalPrice = textView {
                                    textSizeDimen = R.dimen.secondaryTextSize
                                    textColorResource = R.color.colorRed
                                    singleLine = true
                                    if (isFromOrder) {
                                        visibility = View.VISIBLE
                                    } else {
                                        visibility = View.GONE
                                    }
                                }.lparams {
                                    verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    alignParentRight()
                                }
                            }

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imgOrderCount = imageView(R.drawable.ic_cart).lparams(dimen(R.dimen.storeItemUIStarIconSize), dimen(R.dimen.storeItemUIStarIconSize)) {
                                            rightMargin = dimen(R.dimen.storeItemUITvAddressTopMargin)
                                        }

                                tvBillCount = textView {
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

                                imageView(R.drawable.ic_back_button) {
                                    enableHighLightWhenClicked()
                                    onClick {
                                        owner.onBackClicked()
                                    }
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }

                                tvDrinkTitle = textView {
                                    textSizeDimen = R.dimen.storeTitleTextSize
                                    textColorResource = R.color.colorBlue
                                    singleLine = true
                                }.lparams(0, wrapContent) {
                                    weight = 1f
                                }

                                relativeLayout {

                                    padding = dimen(R.dimen.accountFragmentLoginPadding)
                                    enableHighLightWhenClicked()
                                    onClick {
                                        if (isFromOrder) {
                                            owner.onCheckClicked()
                                        } else {
                                            owner.onEditClicked()
                                        }
                                    }

                                    imageView {
                                        backgroundResource = if (isFromOrder) {
                                            R.drawable.ic_check_button
                                        } else {
                                            R.drawable.ic_edit_note
                                        }
                                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                        centerInParent()
                                    }

                                }.lparams(dimen(R.dimen.toolBarHeight), dimen(R.dimen.toolBarHeight))

                                relativeLayout {

                                    padding = dimen(R.dimen.accountFragmentLoginPadding)
                                    visibility = if (isFromOrder) {
                                        View.GONE
                                    } else {
                                        View.VISIBLE
                                    }
                                    enableHighLightWhenClicked()
                                    onClick {
                                        owner.onDeleteDrinkClicked()
                                    }

                                    imageView(R.drawable.ic_trash) {

                                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                        centerInParent()
                                    }
                                }.lparams(dimen(R.dimen.toolBarHeight), dimen(R.dimen.toolBarHeight))

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

                verticalLayout {

                    topPadding = dimen(R.dimen.toolBarHeight)
                    horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    verticalLayout {
                        visibility = if (isFromOrder) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }

                        textView(R.string.note) {
                            textColorResource = R.color.colorBlack
                            bottomPadding = dip(2)
                        }

                        edtNote = editText {
                            backgroundColorResource = R.color.colorGrayVeryLight
                            textColorResource = R.color.colorBlack
                            verticalPadding = dip(5)
                            singleLine = true
                        }.lparams(matchParent, wrapContent)

                        view {
                            backgroundResource = R.color.colorRed
                            alpha = 0.7f
                        }.lparams(matchParent, dip(1)) {
                            bottomMargin = dip(2)
                        }
                    }.lparams(matchParent, wrapContent)

                    recyclerView {
                        id = R.id.recyclerViewDrinkOption
                        layoutManager = LinearLayoutManager(ctx)
                        if (isFromOrder) {
                            drinkOptionAdapter.onOptionSelectedChange = owner::updateDrinkPrice
                        }
                        adapter = drinkOptionAdapter
                    }.lparams(matchParent, ctx.getHeightScreen())
                }.lparams(matchParent, wrapContent)

            }.lparams(matchParent, wrapContent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            linearLayout {
                verticalPadding = dip(1)
                backgroundColorResource = R.color.colorGrayVeryLight
                gravity = Gravity.CENTER_VERTICAL

                textView(R.string.optional) {
                    horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                    textColorResource = R.color.colorBlack
                    backgroundColorResource = R.color.colorWhite
                    setTypeface(null, Typeface.BOLD)
                    gravity = Gravity.CENTER_VERTICAL
                    textSizeDimen = R.dimen.menuTextSize
                }.lparams(0, matchParent) {
                    weight = 1f
                }

            }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}
