package cao.cuong.supership.supership.ui.customer.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *
 * @author at-cuongcao.
 */
class MainPagerAdapter(fm: FragmentManager, val mainTabs: List<MainTab>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = mainTabs[position].getItem()

    override fun getCount() = MainTab.TabItemType.values().size
}
