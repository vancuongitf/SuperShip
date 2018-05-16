package cao.cuong.supership.supership.ui.customer.bill

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class BillFragmentUI(bills: MutableList<ExpressBill>) : AnkoComponent<BillFragment> {

    internal val billAdapter = BillAdapter(bills)
    internal lateinit var llNonLogin: LinearLayout
    internal lateinit var tvReload: TextView
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal lateinit var recyclerViewBills: RecyclerView

    override fun createView(ui: AnkoContext<BillFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    leftPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    gravity = Gravity.CENTER_VERTICAL

                    textView(R.string.yourBills) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(matchParent, wrapContent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            swipeRefreshLayout = swipeRefreshLayout {

                onRefresh {
                    owner.eventReloadClicked()
                }

                relativeLayout {

                    lparams(matchParent, matchParent)

                    tvReload = textView(R.string.reload) {
                        visibility = View.GONE
                        gravity = Gravity.CENTER
                        backgroundColorResource = R.color.colorBlue
                        enableHighLightWhenClicked()
                        verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                        onClick {
                            owner.eventReloadClicked()
                        }
                    }.lparams(matchParent, wrapContent) {
                        centerInParent()
                        margin = dimen(R.dimen.accountFragmentLoginPadding)
                    }

                    recyclerViewBills = recyclerView {
                        id = R.id.recyclerViewBillList
                        backgroundColorResource = R.color.colorWhite
                        layoutManager = LinearLayoutManager(ctx)
                        adapter = billAdapter
                    }.lparams(matchParent, matchParent)

                    llNonLogin = verticalLayout {
                        isClickable = true
                        backgroundColorResource = R.color.colorWhite
                        gravity = Gravity.CENTER
                        visibility = View.GONE

                        textView(R.string.pleaseLogin) {
                            verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)
                            gravity = Gravity.CENTER_HORIZONTAL
                            textColorResource = R.color.colorBlack
                        }.lparams(matchParent, wrapContent)

                        textView(R.string.login) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            backgroundColorResource = R.color.colorBlue
                            enableHighLightWhenClicked()
                            verticalPadding = dimen(R.dimen.accountFragmentLoginPadding)

                            onClick {
                                owner.eventLoginButtonClick()
                            }
                        }.lparams(matchParent, wrapContent)
                    }.lparams(matchParent, matchParent) {
                        margin = dimen(R.dimen.accountFragmentLoginPadding)
                    }
                }

            }.lparams(matchParent, matchParent)
        }
    }
}
