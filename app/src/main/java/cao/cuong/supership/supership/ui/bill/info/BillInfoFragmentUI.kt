package cao.cuong.supership.supership.ui.bill.info

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.getHeightScreen
import cao.cuong.supership.supership.extension.getWidthScreen
import cao.cuong.supership.supership.ui.order.cart.BillDrinkAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class BillInfoFragmentUI(orderedDrink: MutableList<OrderedDrink>) : AnkoComponent<BillInfoFragment> {

    internal lateinit var imgPayment: ImageView
    internal lateinit var imgStoreAvatar: ImageView
    internal lateinit var tvBillId: TextView
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvStoreAddress: TextView
    internal lateinit var tvCustomerName: TextView
    internal lateinit var tvCustomerPhone: TextView
    internal lateinit var tvBillAddress: TextView
    internal lateinit var tvOrderPrice: TextView
    internal lateinit var tvShipPrice: TextView
    internal lateinit var tvTotalPrice: TextView
    internal lateinit var tvOrderTime: TextView
    internal lateinit var tvStatus: TextView
    internal lateinit var imgShowShipRoad: ImageView
    internal lateinit var rlPayment: RelativeLayout
    internal val billDrinkAdapter = BillDrinkAdapter(orderedDrink, BillDrinkAdapter.AdapterType.BILL_INFO)

    override fun createView(ui: AnkoContext<BillInfoFragment>) = with(ui) {
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
                                singleLine = true
                            }

                            tvStoreAddress = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvCustomerName = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvCustomerPhone = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            linearLayout {

                                gravity = Gravity.CENTER_VERTICAL

                                tvBillAddress = textView {
                                    textSizeDimen = R.dimen.secondaryTextSize
                                    textColorResource = R.color.colorBlack
                                    singleLine = true
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams(0, wrapContent) {
                                    weight = 1f
                                    verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }

                                imgShowShipRoad = imageView(R.drawable.ic_show_ship_road) {
                                    visibility = View.GONE
                                    enableHighLightWhenClicked()

                                    onClick {
                                        owner.eventShowShipRoadClick()
                                    }
                                }.lparams(dimen(R.dimen.billInfoButtonShowShipRoad), dimen(R.dimen.billInfoButtonShowShipRoad)) {
                                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }

                            }.lparams(matchParent, wrapContent)

                            tvOrderPrice = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvShipPrice = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvTotalPrice = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvOrderTime = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }

                            tvStatus = textView {
                                textSizeDimen = R.dimen.secondaryTextSize
                                textColorResource = R.color.colorBlack
                                singleLine = true
                            }.lparams {
                                verticalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                            }



                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imageView(R.drawable.ic_star_gold)
                                        .lparams(dimen(R.dimen.storeItemUIStarIconSize), dimen(R.dimen.storeItemUIStarIconSize)) {
                                            rightMargin = dimen(R.dimen.storeItemUITvAddressTopMargin)
                                        }

                                textView(R.string.rate) {
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

                                tvBillId = textView {
                                    textSizeDimen = R.dimen.storeTitleTextSize
                                    textColorResource = R.color.colorBlue
                                    singleLine = true
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams(0, wrapContent) {
                                    weight = 1f
                                }

                                rlPayment = relativeLayout {
                                    visibility = View.GONE
                                    gravity = Gravity.CENTER_VERTICAL
                                    enableHighLightWhenClicked()
                                    onClick {
                                        owner.paymentClicked()
                                    }

                                    imgPayment = imageView(R.drawable.ic_payment) {


                                    }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                        horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                    }
                                }.lparams(wrapContent, matchParent)

                            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

                            view {
                                backgroundResource = R.color.colorGrayVeryLight
                            }.lparams(matchParent, dip(1))

                        }.lparams(matchParent, wrapContent)
                    }.lparams(matchParent, wrapContent) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }

                }.lparams(matchParent, wrapContent) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(matchParent, wrapContent) {

            }

            nestedScrollView {

                recyclerView {
                    topPadding = dimen(R.dimen.toolBarHeight)
                    id = R.id.recyclerViewBillInfoDrinks
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = billDrinkAdapter
                }.lparams(matchParent, context.getHeightScreen())

            }.lparams(matchParent, wrapContent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            linearLayout {
                verticalPadding = dip(1)
                backgroundColorResource = R.color.colorGrayVeryLight
                gravity = Gravity.CENTER_VERTICAL

                textView(R.string.drinks) {
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
