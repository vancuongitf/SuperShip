package cao.cuong.supership.supership.ui.customer.main

import android.os.Bundle
import cao.cuong.supership.supership.ui.base.BaseActivity
import org.jetbrains.anko.setContentView

/**
 *
 * @author at-cuongcao.
 */
class MainActivity : BaseActivity() {

    private lateinit var ui: MainActivityUI
    private val mainTabs = listOf<MainTab>(MainTab(MainTab.TabItemType.ITEM_HOME),
            MainTab(MainTab.TabItemType.ITEM_BILL),
            MainTab(MainTab.TabItemType.ITEM_ACCOUNT))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = MainActivityUI(mainTabs)
        ui.setContentView(this)
    }

    override fun onBackPressed() {
        val currentFragment = mainTabs[ui.viewPager.currentItem].getItem()
        if (currentFragment.childFragmentManager.backStackEntryCount > 0) {
            currentFragment.childFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onBindViewModel() {

    }
}