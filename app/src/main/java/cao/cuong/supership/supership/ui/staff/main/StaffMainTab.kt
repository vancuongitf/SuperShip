package cao.cuong.supership.supership.ui.staff.main

import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.R.id.homeContainer
import cao.cuong.supership.supership.ui.staff.base.StaffBaseFragment
import cao.cuong.supership.supership.ui.staff.bill.StaffBillFragment

/**
 * MainTab
 * @author cuongcaov
 */
class StaffMainTab(val itemType: TabItemType) {

    private val billFragment = StaffBillFragment()
    private val shipperFragment = StaffBillFragment()
    private val accountFragment = StaffBillFragment()

    /**
     * TabItemType
     */
    enum class TabItemType(@DrawableRes val icon: Int, @DrawableRes val iconRed: Int, var isSelected: Boolean = false) {
        /**
         * First Item On Tab
         */
        ITEM_HOME(R.drawable.ic_home, R.drawable.ic_home_red, true),

        /**
         * Second Item On Tab
         */
        ITEM_BILL(R.drawable.ic_bill, R.drawable.ic_bill_red),

        /**
         * Third Item On Tab
         */
        ITEM_ACCOUNT(R.drawable.ic_account, R.drawable.ic_account_red)
    }

    /**
     * Method return item of tab each position
     */
    fun getItem(): Fragment = when (itemType) {
        TabItemType.ITEM_HOME -> billFragment
        TabItemType.ITEM_BILL -> shipperFragment
        TabItemType.ITEM_ACCOUNT -> accountFragment
    }
}
