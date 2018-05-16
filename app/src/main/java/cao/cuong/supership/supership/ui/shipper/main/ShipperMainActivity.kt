package cao.cuong.supership.supership.ui.shipper.main

import android.os.Bundle
import android.util.Log
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import org.jetbrains.anko.setContentView

class ShipperMainActivity : BaseActivity() {

    private lateinit var ui: ShipperMainActivityUI
    private lateinit var viewModel: ShipperManActivityViewModel
    private var dialogShowing = false
    private val mainTabs = listOf<ShipperMainTab>(ShipperMainTab(ShipperMainTab.TabItemType.ITEM_CHECKED), ShipperMainTab(ShipperMainTab.TabItemType.ITEM_RECEIVE), ShipperMainTab(ShipperMainTab.TabItemType.ITEM_INFO))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ShipperMainActivityUI(mainTabs)
        viewModel = ShipperManActivityViewModel(this)
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
        ui.setContentView(this)
    }

    override fun onBindViewModel() {

    }


}
