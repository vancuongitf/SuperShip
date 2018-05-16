package cao.cuong.supership.supership.ui.shipper.main

import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.ui.shipper.bill.checked.CheckedBillFragment
import cao.cuong.supership.supership.ui.shipper.bill.receive.ReceiveBillFragment
import cao.cuong.supership.supership.ui.shipper.info.ShipperInfoFragment

/**
 * MainTab
 * @author cuongcaov
 */
class ShipperMainTab(val itemType: TabItemType) {

    private val checked = CheckedBillFragment()
    private val receiver = ReceiveBillFragment()
    private val info = ShipperInfoFragment()

    /**
     * TabItemType
     */
    enum class TabItemType(@DrawableRes val icon: Int, @DrawableRes val iconRed: Int, var isSelected: Boolean = false) {
        /**
         * First Item On Tab
         */
        ITEM_CHECKED(R.drawable.ic_home, R.drawable.ic_home_red, true),

        /**
         * Second Item On Tab
         */
        ITEM_RECEIVE(R.drawable.ic_bill, R.drawable.ic_bill_red),

        /**
         * Third Item On Tab
         */
        ITEM_INFO(R.drawable.ic_account, R.drawable.ic_account_red)
    }

    /**
     * Method return item of tab each position
     */
    fun getItem(): Fragment = when (itemType) {
        TabItemType.ITEM_CHECKED -> checked
        TabItemType.ITEM_RECEIVE -> receiver
        TabItemType.ITEM_INFO -> info
    }
}
