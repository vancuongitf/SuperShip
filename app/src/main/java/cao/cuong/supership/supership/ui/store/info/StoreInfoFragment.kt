package cao.cuong.supership.supership.ui.store.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import com.bumptech.glide.Glide
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textResource

/**
 *
 * @author at-cuongcao.
 */
class StoreInfoFragment : BaseFragment() {

    companion object {
        private const val KEY_STORE_ID = "store_id"

        internal fun getNewInstance(storeId: Long): StoreInfoFragment {
            val instance = StoreInfoFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    private var storeId = -1L
    private lateinit var ui: StoreInfoFragmentUI
    private lateinit var viewModel: StoreInfoFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(KEY_STORE_ID)
        viewModel = StoreInfoFragmentViewModel(context, storeId)
        ui = StoreInfoFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStoreInfo().subscribe(this::handleGetStoreInfoSuccess, this::handleApiError)
    }


    override fun onBindViewModel() {
        addDisposables(
                viewModel.progressDialogStatusObservable
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateProgressDialogStatus)
        )
    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    private fun handleGetStoreInfoSuccess(store: Store) {
        Glide.with(context)
                .asBitmap()
                .load("https://vnshipperman.000webhostapp.com/uploads/${store.image}")
                .into(ui.imgStoreAvatar)
        ui.tvStoreNameTitle.text = store.name
        ui.tvStoreName.text = store.name
        ui.tvAddress.text = store.address
        if (store.rate.rateCount == 0) {
            ui.tvStarRate.textResource = R.string.not_rate_yet
        } else {
            ui.tvStarRate.text = context.getString(R.string.store_rate, store.rate.rate.toString(), store.rate.rateCount.toString())
        }
    }
}
