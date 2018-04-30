package cao.cuong.supership.supership.ui.store.info

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class StoreBaseInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        internal const val ITEM_TYPE_INFO = 0
        internal const val ITEM_TYPE_MENU_HEADER = 1
        internal const val ITEM_TYPE_MENU_ITEM = 2
    }

    override fun getItemCount() = 5

    override fun getItemViewType(position: Int): Int {
        if (position > 1) {
            return 2
        }
        return position
    }
}
