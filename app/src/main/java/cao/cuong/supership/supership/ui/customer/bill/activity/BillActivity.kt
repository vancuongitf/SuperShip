package cao.cuong.supership.supership.ui.customer.bill.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Address
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.customer.bill.info.BillInfoFragment
import cao.cuong.supership.supership.ui.customer.bill.ship.BillShipRoadFragment
import cao.cuong.supership.supership.ui.customer.order.drink.item.OrderedDrinkFragment
import cao.cuong.supership.supership.ui.customer.store.comment.StoreCommentFragment
import cao.cuong.supership.supership.ui.shipper.bill.complete.ShipperBillCompleteFragment
import cao.cuong.supership.supership.ui.shipper.bill.info.ShipperBillInfoFragment
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import cao.cuong.supership.supership.ui.staff.bill.info.StaffBillInfoFragment
import org.jetbrains.anko.setContentView

class BillActivity : BaseActivity() {

    internal var billInfo: BillInfo? = null
    private lateinit var ui: BillActivityUI
    private lateinit var viewModel: BillActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BillActivityViewModel(this)
        ui = BillActivityUI()
        ui.setContentView(this)
        val billId = intent.extras.getLong(BillInfoFragment.KEY_BILL_ID)
        if (billId > 0) {

            when (viewModel.getModule()) {

                SplashFragment.CUSTOMER_MODULE -> {
                    replaceFragment(R.id.billActivityContainer, BillInfoFragment.getNewInstance(billId))
                }

                SplashFragment.SHIPPER_MODULE -> {
                    replaceFragment(R.id.billActivityContainer, ShipperBillInfoFragment.getNewInstance(billId))
                }

                SplashFragment.STAFF_MODULE -> {
                    replaceFragment(R.id.billActivityContainer, StaffBillInfoFragment.getNewInstance(billId))
                }
            }

        } else {
            showOkAlert(Throwable("Xãy ra lỗi.")) {
                finish()
            }
        }
    }

    override fun onBindViewModel() {

    }

    internal fun openShipRoadFragment(billId: Long, storeAddress: Address, billAddress: Address, points: String) {
        addFragment(R.id.billActivityContainer, BillShipRoadFragment.getNewInstance(billId, storeAddress, billAddress, points), { it.animRightToLeft() }, BillShipRoadFragment::class.java.simpleName)
    }

    internal fun openCompleteBillFragment(billId: Long) {
        addFragment(R.id.billActivityContainer, ShipperBillCompleteFragment.getNewInstance(billId), { it.animRightToLeft() }, ShipperBillCompleteFragment::class.java.simpleName)
    }

    internal fun openOrderedDrinkFragment(position: Int) {
        addFragment(R.id.billActivityContainer, OrderedDrinkFragment.getNewInstance(position), { it.animRightToLeft() }, "Bill-" + OrderedDrinkFragment::class.java.simpleName)
    }

    internal fun openStoreCommentFragment(id: Long) {
        addFragment(R.id.billActivityContainer, StoreCommentFragment.getNewInstance(id), { it.animRightToLeft() }, "Bill-" + StoreCommentFragment::class.java.simpleName)
    }
}
