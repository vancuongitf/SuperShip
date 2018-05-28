package cao.cuong.supership.supership.ui.customer.store.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.model.rxevent.UpdateCartStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.UpdateStoreStatusBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.order.OrderActivity
import cao.cuong.supership.supership.ui.customer.store.BaseStoreInfoActivity
import cao.cuong.supership.supership.ui.customer.store.activity.StoreActivity
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
        loadData()
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

    internal fun onCartClicked() {
        (activity as? OrderActivity)?.openCartFragment()
    }

    internal fun optionalButtonClick() {
        (activity as? StoreActivity)?.openDrinkOptionFragment(storeId)
    }

    internal fun reloadData() {
        loadData()
    }

    internal fun onEditInfoClicked() {
        (activity as? StoreActivity)?.openEditStoreInfoFragment()
    }

    internal fun changeStoreStatus() {
        (activity as? StoreActivity)?.let {
            if (it.store.status) {
                context.showConfirmAlert(R.string.closeConfirm) {
                    changeStoreStatus(0)
                }
            } else {
                context.showConfirmAlert(R.string.openConfirm) {
                    changeStoreStatus(1)
                }
            }
        }
    }

    fun onCommentClicked() {
        (activity as? StoreActivity)?.openCommentFragment(storeId)
        (activity as? OrderActivity)?.openCommentFragment(storeId)
    }

    private fun handleGetStoreInfoSuccess(store: Store) {
        (activity as? BaseStoreInfoActivity)?.updateStore(store)
        val option = RequestOptions()
                .placeholder(R.drawable.glide_place_holder)
        Glide.with(context)
                .applyDefaultRequestOptions(option)
                .asBitmap()
                .load(BuildConfig.BASE_IMAGE_URL + store.image)
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
        if (!orderCase) {
            ui.rlEditInfo.visibility = View.VISIBLE
        }
        if (store.status) {
            ui.imgChangeStoreStatus.setImageResource(R.drawable.ic_close)
        } else {
            ui.imgChangeStoreStatus.setImageResource(R.drawable.ic_open)
        }
    }

    private fun drinkAdapterOnItemClicked(drink: Drink) {
        if (orderCase) {
            (activity as? OrderActivity)?.openDrinkOrderFragment(drink)
        } else {
            (activity as? StoreActivity)?.openDrinkFragment(drink)
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

    private fun loadData() {
        viewModel.getStoreInfo().subscribe(this::handleGetStoreInfoSuccess, this::handleGetStoreApiError)
    }

    private fun handleGetStoreApiError(throwable: Throwable) {
        context.showOkAlert(throwable) {
            activity.onBackPressed()
        }
    }

    private fun changeStoreStatus(newStatus: Int) {
        viewModel.changeStoreStatus(UpdateStoreStatusBody(viewModel.getAccessToken(), storeId, newStatus))
                .observeOnUiThread()
                .subscribe(this::handleChangeStoreStatusSuccess, this::handleApiError)
    }

    private fun handleChangeStoreStatusSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            reloadData()
        }
    }
}
