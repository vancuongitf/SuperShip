package cao.cuong.supership.supership.ui.location

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.location.search.SearchLocationFragment
import org.jetbrains.anko.setContentView

class LocationActivity : BaseActivity() {

    companion object {
        internal const val REQUEST_CODE_SEARCH_LOCATION = 22
        internal const val KEY_ADDRESS_RESULT = "address_result"
        internal const val KEY_STORE_ADDRESS = "store_address"
    }

    private lateinit var ui: LocationActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = LocationActivityUI()
        ui.setContentView(this)
        if (intent.extras != null) {
            replaceFragment(R.id.searchLocationContainer, SearchLocationFragment.getNewInstance(intent.extras.getParcelable(KEY_STORE_ADDRESS)))
        } else {
            replaceFragment(R.id.searchLocationContainer, SearchLocationFragment.getNewInstance(null))
        }
    }

    override fun onBindViewModel() {
    }
}
