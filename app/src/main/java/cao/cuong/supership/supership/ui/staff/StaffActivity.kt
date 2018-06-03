package cao.cuong.supership.supership.ui.staff

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.staff.shipper.info.StaffShipperInfoFragment
import org.jetbrains.anko.setContentView

class StaffActivity : BaseActivity() {

    companion object {
        internal const val KEY_SCREEN = "screen"
        internal const val SHIPPER_SCREEN = 1
        internal const val USER_SCREEN = 2
    }

    private val ui = StaffActivityUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)
        val screen = intent.extras.getInt(KEY_SCREEN)
        when (screen) {
            SHIPPER_SCREEN -> {
                val shipperId = intent.extras.getLong(StaffShipperInfoFragment.KEY_SHIPPER_ID)
                replaceFragment(R.id.staffActivityContainer, StaffShipperInfoFragment.getNewInstance(shipperId))
            }
        }
    }

    override fun onBindViewModel() {

    }
}
