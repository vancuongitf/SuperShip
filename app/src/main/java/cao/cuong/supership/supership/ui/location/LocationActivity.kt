package cao.cuong.supership.supership.ui.location

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.location.search.SearchLocationFragment
import org.jetbrains.anko.setContentView

class LocationActivity : BaseActivity() {

    companion object {
        const val KEY_ADDRESS_RESULT = "address_result"
    }

    private lateinit var ui: LocationActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = LocationActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.searchLocationContainer, SearchLocationFragment())
    }

    override fun onBindViewModel() {
    }
}
