package cao.cuong.supership.supership.ui.shipper.bill.receive

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.ImageView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.Status
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.ui.shipper.bill.checked.CheckedBillAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ReceiveBillFragmentUI(checkedBills: MutableList<ExpressBill>) : AnkoComponent<ReceiveBillFragment> {

    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal val checkedBillAdapter = CheckedBillAdapter(checkedBills)
    internal lateinit var imgInTransit: ImageView
    internal lateinit var imgCompleted: ImageView

    override fun createView(ui: AnkoContext<ReceiveBillFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    leftPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    gravity = Gravity.CENTER_VERTICAL

                    textView(R.string.yourBills) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.changeStatus(Status.BILL_IN_TRANSIT)
                        }

                        imgInTransit = imageView(R.drawable.ic_transit_red) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }

                    relativeLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        enableHighLightWhenClicked()
                        onClick {
                            owner.changeStatus(Status.BILL_COMPLETED)
                        }

                        imgCompleted = imageView(R.drawable.ic_done) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))


            swipeRefreshLayout = swipeRefreshLayout {
                recyclerView {
                    lparams(matchParent, matchParent)
                    layoutManager = LinearLayoutManager(context)
                    adapter = checkedBillAdapter

                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                        }
                    })
                }

                onRefresh {
                    owner.onRefreshData()
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
