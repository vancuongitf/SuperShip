package cao.cuong.supership.supership.ui.staff.main

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cao.cuong.supership.supership.ui.customer.main.MainTab

/**
 *
 * @author at-cuongcao.
 */
class StaffMainPagerAdapter(fm: FragmentManager, private val mainTabs: List<StaffMainTab>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = mainTabs[position].getItem()

    override fun getCount() = MainTab.TabItemType.values().size

    override fun getPageTitle(position: Int) = mainTabs[position].getLabel()
}
