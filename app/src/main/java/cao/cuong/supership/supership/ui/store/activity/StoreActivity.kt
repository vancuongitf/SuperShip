package cao.cuong.supership.supership.ui.store.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.store.list.StoreListFragment
import org.jetbrains.anko.setContentView

class StoreActivity : BaseActivity() {

    internal var shouldReload = false

    private lateinit var ui: StoreActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = StoreActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.storeActivityContainer, StoreListFragment())
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.storeActivityContainer)
            if (currentFragment is StoreListFragment && shouldReload) {
                shouldReload = false
                currentFragment.reloadData()
            }
        }
    }

    override fun onBindViewModel() = Unit
}