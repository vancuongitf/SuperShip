package cao.cuong.supership.supership.ui.shipper.account

import android.os.Bundle
import android.util.Log
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.shipper.account.signup.ShipperSignUpFragment
import org.jetbrains.anko.setContentView

class ShipperActivity : BaseActivity() {

    companion object {
        internal const val USER_ACTIVITY_REQUEST_CODE = 3011
    }

    private lateinit var ui: ShipperActivityUI
    private lateinit var viewModel: ShipperActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ShipperActivityUI()
        viewModel = ShipperActivityViewModel(this)
        ui.setContentView(this)
        replaceFragment(R.id.shipperActivityContainer, ShipperSignUpFragment())
    }

    override fun onBindViewModel() {

    }
}
