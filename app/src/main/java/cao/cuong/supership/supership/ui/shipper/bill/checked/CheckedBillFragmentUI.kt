package cao.cuong.supership.supership.ui.shipper.bill.checked

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.ExpressBill
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CheckedBillFragmentUI(checkedBills: MutableList<ExpressBill>) : AnkoComponent<CheckedBillFragment> {

    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal val checkedBillAdapter = CheckedBillAdapter(checkedBills)

    override fun createView(ui: AnkoContext<CheckedBillFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {

                backgroundColorResource = R.color.colorGrayLight
                setContentInsetsAbsolute(0, 0)

                linearLayout {

                    leftPadding = dimen(R.dimen.accountFragmentLoginPadding)

                    gravity = Gravity.CENTER_VERTICAL

                    textView(R.string.checkedBills) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlack
                    }.lparams(matchParent, wrapContent)

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
