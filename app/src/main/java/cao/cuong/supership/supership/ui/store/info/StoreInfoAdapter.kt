package cao.cuong.supership.supership.ui.store.info

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import org.jetbrains.anko.AnkoContext

class StoreInfoAdapter : StoreBaseInfoAdapter(), StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    override fun getHeaderId(position: Int) = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val ui = StoreInfoItemUI()
        return StoreInfoViewHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val ui = MenuHeaderItemUI()
        return StoreMenuHeaderHolder(ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    inner class StoreInfoViewHolder(ui: StoreInfoItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class StoreMenuHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
