package cao.cuong.supership.supership.ui.store.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.store.list.StoreListFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import org.jetbrains.anko.setContentView

class StoreActivity : BaseActivity() {

    private lateinit var ui: StoreActivityUI
    private var userId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = StoreActivityUI()
        ui.setContentView(this)
        userId = intent.extras.getLong(StoreListFragment.KEY_USER_ID)
        replaceFragment(R.id.storeActivityContainer, StoreListFragment.getNewInstance(userId))
    }

    override fun onBindViewModel() = Unit
}