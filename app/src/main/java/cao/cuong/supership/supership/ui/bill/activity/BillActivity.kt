package cao.cuong.supership.supership.ui.bill.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Address
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.bill.info.BillInfoFragment
import cao.cuong.supership.supership.ui.bill.ship.BillShipRoadFragment
import org.jetbrains.anko.setContentView

class BillActivity : BaseActivity() {

    private lateinit var ui: BillActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = BillActivityUI()
        ui.setContentView(this)
        val billId = intent.extras.getLong(BillInfoFragment.KEY_BILL_ID)
        if (billId > 0) {
            replaceFragment(R.id.billActivityContainer, BillInfoFragment.getNewInstance(billId))
        } else {
            showOkAlert(Throwable("Xãy ra lỗi.")) {
                finish()
            }
        }
    }

    override fun onBindViewModel() {

    }

    internal fun openShipRoadFragment(storeAddress: Address, billAddress: Address, points: String) {
        addFragment(R.id.billActivityContainer, BillShipRoadFragment.getNewInstance(storeAddress, billAddress, points), { it.animRightToLeft() }, BillShipRoadFragment::class.java.simpleName)
    }
}
