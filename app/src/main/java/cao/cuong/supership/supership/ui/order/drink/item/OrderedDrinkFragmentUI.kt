package cao.cuong.supership.supership.ui.order.drink.item

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getWidthScreen
import cao.cuong.supership.supership.ui.store.optional.adapter.OptionalAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class OrderedDrinkFragmentUI(options: MutableList<DrinkOption>) : AnkoComponent<OrderedDrinkFragment> {
    internal lateinit var imgDrinkImage: ImageView
    internal lateinit var tvDrinkName: TextView
    internal lateinit var tvPrice: TextView
    internal lateinit var tvTotalPrice: TextView
    internal lateinit var tvBillCount: TextView
    internal lateinit var imgMinus: ImageView
    internal lateinit var imgAdd: ImageView
    internal lateinit var tvDrinkCount: TextView
    internal lateinit var edtNote: EditText
    internal val drinkOptionAdapter = OptionalAdapter(options, OptionalAdapter.AdapterType.ORDER)

    override fun createView(ui: AnkoContext<OrderedDrinkFragment>) = with(ui) {
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
                                }.lparams {
                                    verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    alignParentRight()
                                }
                            }

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imageView(R.drawable.ic_cart)
                                        .lparams(dimen(R.dimen.storeItemUIStarIconSize), dimen(R.dimen.storeItemUIStarIconSize)) {
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

                }.lparams(matchParent, wrapContent) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(matchParent, wrapContent) {

            }

            nestedScrollView {

                verticalLayout {

                    topPadding = dimen(R.dimen.toolBarHeight)
                    horizontalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    verticalLayout {

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
                        adapter = drinkOptionAdapter
                    }.lparams(matchParent, matchParent)
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
