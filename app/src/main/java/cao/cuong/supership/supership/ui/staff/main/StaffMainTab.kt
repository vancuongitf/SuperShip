package cao.cuong.supership.supership.ui.staff.main

import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.ui.staff.bill.StaffBillFragment
import cao.cuong.supership.supership.ui.staff.info.StaffInfoFragment
import cao.cuong.supership.supership.ui.staff.shipper.StaffShipperFragment

/**
 * MainTab
 * @author cuongcaov
 */
class StaffMainTab(val itemType: TabItemType) {

    private val billFragment = StaffBillFragment()
    private val shipperFragment = StaffShipperFragment()
    private val accountFragment = StaffInfoFragment()

    /**
     * TabItemType
     */
    enum class TabItemType(@DrawableRes val icon: Int, @DrawableRes val iconRed: Int, var isSelected: Boolean = false) {
        /**
         * First Item On Tab
         */
        ITEM_HOME(R.drawable.ic_bill_list_black, R.drawable.ic_bill_list_red, true),

        /**
         * Second Item On Tab
         */
        ITEM_BILL(R.drawable.ic_shipper_black, R.drawable.ic_shipper_red),

        /**
         * Third Item On Tab
         */
        ITEM_ACCOUNT(R.drawable.ic_user_info_black, R.drawable.ic_user_info_red)
    }

    /**
     * Method return item of tab each position
     */
    fun getItem(): Fragment = when (itemType) {
        TabItemType.ITEM_HOME -> billFragment
        TabItemType.ITEM_BILL -> shipperFragment
        TabItemType.ITEM_ACCOUNT -> accountFragment
    }

    fun getLabel() = when (itemType) {
        TabItemType.ITEM_HOME -> "Đơn hàng"
        TabItemType.ITEM_BILL -> "Shipper"
        TabItemType.ITEM_ACCOUNT -> "Cá nhân"
    }
}
