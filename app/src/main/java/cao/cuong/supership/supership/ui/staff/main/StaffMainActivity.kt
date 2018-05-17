package cao.cuong.supership.supership.ui.staff.main

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import org.jetbrains.anko.setContentView

/**
 *
 * @author at-cuongcao.
 */
class StaffMainActivity : BaseActivity() {

    private lateinit var ui: StaffMainActivityUI
    private var dialogShowing = false
    private val mainTabs = listOf<StaffMainTab>(StaffMainTab(StaffMainTab.TabItemType.ITEM_HOME),
            StaffMainTab(StaffMainTab.TabItemType.ITEM_BILL),
            StaffMainTab(StaffMainTab.TabItemType.ITEM_ACCOUNT))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = StaffMainActivityUI(mainTabs)
        ui.setContentView(this)
        RxBus.listen(OpenUserActivityAlert::class.java)
                .observeOnUiThread()
                .subscribe({
                    if (!dialogShowing) {
                        dialogShowing = true
                        showOkAlert(R.string.notification, R.string.unAuthorzation) {
                            startUserActivity()
                            dialogShowing = false
                        }
                    }
                })
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
