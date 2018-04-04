package cao.cuong.supership.supership.ui.home.store

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 *
 * @author at-cuongcao.
 */
class StoreFragmentUI(storeInfoExpresses: MutableList<StoreInfoExpress>) : AnkoComponent<StoreFragment> {
    internal lateinit var recyclerViewStores: RecyclerView
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal val storeAdapter = StoreAdapter(storeInfoExpresses)

    override fun createView(ui: AnkoContext<StoreFragment>) = with(ui) {

        relativeLayout {
            lparams(matchParent, matchParent)
            backgroundResource = R.color.colorWhite
            swipeRefreshLayout = swipeRefreshLayout {
                onRefresh {
                    owner.eventOnRefresh()
                }
                relativeLayout {
                    lparams(matchParent, matchParent)
                    lparams(matchParent, matchParent)
                    recyclerViewStores = recyclerView {
                        clipToPadding = false
                        id = R.id.storeFragmentRecyclerViewStores
                        layoutManager = LinearLayoutManager(context)
                        adapter = storeAdapter
                        storeAdapter.notifyDataSetChanged()
                    }.lparams(matchParent, matchParent)
                }
            }
        }
    }
}
