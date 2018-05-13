package cao.cuong.supership.supership.ui.customer.home.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cao.cuong.supership.supership.ui.customer.home.store.StoreFragment
import cao.cuong.supership.supership.ui.customer.home.store.StoreFragmentAdvanceParam

/**
 *
 * @author at-cuongcao.
 */
class HomeFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = when (position + 1) {
        StoreFragmentAdvanceParam.LASTED_ORDER.advanceParam -> StoreFragment.getNewInstance(StoreFragmentAdvanceParam.LASTED_ORDER)

        StoreFragmentAdvanceParam.NEAREST.advanceParam -> StoreFragment.getNewInstance(StoreFragmentAdvanceParam.NEAREST)

        else -> StoreFragment.getNewInstance(StoreFragmentAdvanceParam.ORDER_COUNT)
    }

    override fun getCount() = StoreFragmentAdvanceParam.values().size

    override fun getPageTitle(position: Int) = when (position + 1) {
        StoreFragmentAdvanceParam.LASTED_ORDER.advanceParam -> "Vừa đặt"

        StoreFragmentAdvanceParam.NEAREST.advanceParam -> "Gần đây"

        else -> "Đặt nhiều"
    }
}