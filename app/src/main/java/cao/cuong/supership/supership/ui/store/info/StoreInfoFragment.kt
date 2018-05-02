package cao.cuong.supership.supership.ui.store.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.RxEvent.UpdateCartStatus
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.order.OrderActivity
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textResource

/**
 *
 * @author at-cuongcao.
 */
class StoreInfoFragment : BaseFragment() {

    companion object {
        internal const val KEY_STORE_ID = "store_id"

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
    private val drinks = mutableListOf<Drink>()
    private var orderCase = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(KEY_STORE_ID)
        viewModel = StoreInfoFragmentViewModel(context, storeId)
        checkOrderCase()
        ui = StoreInfoFragmentUI(drinks, orderCase)
        ui.drinkAdapter.onItemClicked = this::drinkAdapterOnItemClicked
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStoreInfo().subscribe(this::handleGetStoreInfoSuccess, this::handleApiError)
        RxBus.listen(UpdateCartStatus::class.java)
                .observeOnUiThread()
                .subscribe(this::handleUpdateCartStatus)
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

    internal fun addDrinkClicked() {
        (activity as? StoreActivity)?.openCreateDrinkFragment(storeId)
    }

    private fun handleGetStoreInfoSuccess(store: Store) {
        val option = RequestOptions()
                .placeholder(R.drawable.glide_place_holder)
        Glide.with(context)
                .applyDefaultRequestOptions(option)
                .asBitmap()
                .load("https://vnshipperman.000webhostapp.com/uploads/${store.image}")
                .into(ui.imgStoreAvatar)
        ui.tvStoreNameTitle.text = store.name
        ui.tvStoreName.text = store.name
        ui.tvAddress.text = store.address
        drinks.clear()
        drinks.addAll(store.menu)
        ui.drinkAdapter.notifyDataSetChanged()
        if (store.rate.rateCount == 0) {
            ui.tvStarRate.textResource = R.string.not_rate_yet
        } else {
            ui.tvStarRate.text = context.getString(R.string.store_rate, store.rate.rate.toString(), store.rate.rateCount.toString())
        }
    }

    internal fun onCartClicked() {
    }

    private fun drinkAdapterOnItemClicked(drink: Drink) {
        if (orderCase) {
            (activity as? OrderActivity)?.openDrinkOrderFragment(drink)
        } else {
            // TODO: Hanlde later
        }
    }

    private fun checkOrderCase() {
        when (activity) {
            is OrderActivity -> orderCase = true

            is StoreActivity -> orderCase = false
        }
    }

    private fun handleUpdateCartStatus(updateCartStatus: UpdateCartStatus) {
        if (updateCartStatus.status) {
            ui.imgCart.setImageResource(R.drawable.ic_cart_red)
        } else {
            ui.imgCart.setImageResource(R.drawable.ic_cart)
        }
    }
}
