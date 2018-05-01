package cao.cuong.supership.supership.ui.order

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import org.jetbrains.anko.setContentView

class OrderActivity : BaseActivity() {

    private lateinit var ui: OrderActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = OrderActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.orderActivityContainer, StoreInfoFragment())
    }

    override fun onBindViewModel() {

    }
}