package cao.cuong.supership.supership.ui.store.optional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment.Companion.KEY_STORE_ID
import org.jetbrains.anko.AnkoContext

class OptionalFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeId: Long): OptionalFragment {
            val instance = OptionalFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    private lateinit var ui: OptionalFragmentUI
    private var options = mutableListOf<DrinkOption>()
    private var storeId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? StoreActivity)?.let {
            options = it.getDrinkOptions()
        }
        storeId = arguments.getLong(KEY_STORE_ID)
        ui = OptionalFragmentUI(options)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun addOptionalClicked() {
        (activity as? StoreActivity)?.openAddDrinkOptionFragment(storeId)
    }
}
