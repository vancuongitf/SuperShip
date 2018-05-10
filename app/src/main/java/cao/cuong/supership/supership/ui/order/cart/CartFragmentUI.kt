package cao.cuong.supership.supership.ui.order.cart

import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.extension.commonEditText
import cao.cuong.supership.supership.extension.commonTextView
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class CartFragmentUI(orderedDrink: MutableList<OrderedDrink>) : AnkoComponent<CartFragment> {

    internal lateinit var tvBillTitle: TextView
    internal lateinit var edtCustomerName: EditText
    internal lateinit var tvAddress: TextView
    internal lateinit var edtPhone: EditText
    internal lateinit var tvStoreName: TextView
    internal lateinit var tvBillCost: TextView
    internal lateinit var tvBillShipCost: TextView
    internal lateinit var tvTotalCost: TextView
    internal val orderedDrinkAdapter = BillDrinkAdapter(orderedDrink)

    override fun createView(ui: AnkoContext<CartFragment>) = with(ui){

        verticalLayout {
            lparams(matchParent, matchParent)

            isClickable = true
            backgroundResource = R.drawable.bg_login_image

            toolbar {
                setContentInsetsAbsolute(0, 0)
                backgroundColorResource = R.color.colorWhite

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

                    tvBillTitle = textView(R.string.yourCart) {
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

            scrollView {
                verticalLayout {

                    horizontalPadding = dimen(R.dimen.drinkItemUIPadding)

                    textView(R.string.customerInfo) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorWhite
                        setTypeface(null, Typeface.BOLD)
                        verticalPadding = dimen(R.dimen.drinkItemUIPadding)
                    }

                    view {
                        backgroundResource = R.color.colorGrayVeryLight
                    }.lparams(matchParent, dip(1))

                    verticalLayout {

                        padding = dimen(R.dimen.drinkItemUIPadding)

                        commonEditText(R.string.customerName) {
                            edtCustomerName = editText
                            edtCustomerName.singleLine = true
                        }.lparams(matchParent, wrapContent)

                        commonEditText(R.string.phoneNumber) {
                            edtPhone = editText
                            edtPhone.singleLine = true
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.drinkItemUIPadding)
                        }

                        commonTextView(R.string.address) {
                            tvAddress = textView
                            enableHighLightWhenClicked()
                            onClick {
                                owner.eventChooseAddressClicked()
                            }
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.drinkItemUIPadding)
                        }
                    }.lparams(matchParent, wrapContent)

                    view {
                        backgroundResource = R.color.colorGrayVeryLight
                    }.lparams(matchParent, dip(1))

                    textView(R.string.orderInfo) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorWhite
                        setTypeface(null, Typeface.BOLD)
                        verticalPadding = dimen(R.dimen.drinkItemUIPadding)
                    }

                    view {
                        backgroundResource = R.color.colorGrayVeryLight
                    }.lparams(matchParent, dip(1))

                    verticalLayout {

                        padding = dimen(R.dimen.drinkItemUIPadding)

                        commonTextView(R.string.storeName) {
                            tvStoreName = textView
                            edtCustomerName.singleLine = true
                        }.lparams(matchParent, wrapContent)

                        commonTextView(R.string.orderCost) {
                            tvBillCost = textView
                            edtPhone.singleLine = true
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.drinkItemUIPadding)
                        }

                        commonTextView(R.string.shipCost) {
                            tvBillShipCost = textView
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.drinkItemUIPadding)
                        }

                        commonTextView(R.string.totalCost) {
                            tvTotalCost = textView
                        }.lparams(matchParent, wrapContent) {
                            topMargin = dimen(R.dimen.drinkItemUIPadding)
                        }
                    }.lparams(matchParent, wrapContent)

                    view {
                        backgroundResource = R.color.colorGrayVeryLight
                    }.lparams(matchParent, dip(1))

                    textView(R.string.orderedDrinkList) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorWhite
                        setTypeface(null, Typeface.BOLD)
                        verticalPadding = dimen(R.dimen.drinkItemUIPadding)
                    }

                    view {
                        backgroundResource = R.color.colorGrayVeryLight
                    }.lparams(matchParent, dip(1))

                    recyclerView {
                        id = R.id.cartFragmentRecyclerViewOrderDrinks
                        layoutManager = LinearLayoutManager(context)
                        adapter = orderedDrinkAdapter
                    }.lparams(matchParent, wrapContent) {
                        margin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, matchParent)
        }
    }
}
