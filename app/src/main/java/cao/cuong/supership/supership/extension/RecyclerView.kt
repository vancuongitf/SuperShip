package cao.cuong.supership.supership.extension

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 *
 * Get first item's position is displaying in RecyclerView.
 *
 * @author at-cuongcao.
 */
internal fun RecyclerView.findFirstVisibleItemPosition(): Int {
    val layoutManager = this.layoutManager
    return when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()

        is StaggeredGridLayoutManager -> {
            val firstItemList = layoutManager.findFirstVisibleItemPositions(null)
            if (firstItemList == null) {
                -1
            } else {
                val min = firstItemList.min()
                if (min != null && min == -1) {
                    firstItemList.max() ?: -1
                } else {
                    min ?: -1
                }
            }
        }

        is GridLayoutManager -> layoutManager.findFirstVisibleItemPosition()

        else -> -1
    }
}

/**
 * Get last item's position is displaying in RecyclerView.
 */
internal fun RecyclerView.findLastVisibleItemPosition(): Int {
    val layoutManager = this.layoutManager
    return when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()

        is StaggeredGridLayoutManager -> layoutManager.findLastVisibleItemPositions(null).max()
                ?: -1

        is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()

        else -> -1
    }
}
