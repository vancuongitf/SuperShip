package cao.cuong.supership.supership.ui.customer.home.store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.order.OrderActivity
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.toast

/**
 *
 * @author at-cuongcao.
 */
class StoreFragment : BaseFragment() {

    companion object {

        internal const val KEY_ADVANCE = "KEY_ADVANCE"

        internal fun getNewInstance(advanceParam: StoreFragmentAdvanceParam) = StoreFragment().apply {
            arguments = Bundle().apply {
                this.putInt(KEY_ADVANCE, advanceParam.advanceParam)
            }
        }
    }

    private lateinit var viewModel: StoreFragmentViewModel
    private lateinit var ui: StoreFragmentUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = StoreFragmentViewModel(context, arguments.getInt(KEY_ADVANCE))
        ui = StoreFragmentUI(viewModel.stores)
        return ui.createView(AnkoContext.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExpressesStoreObservable
                .observeOnUiThread()
                .subscribe(this::handleGetStoreListCompleted, {})
        viewModel.getExpressesStore()
    }

    override fun onBindViewModel() = Unit

    internal fun eventOnRefresh() {
        viewModel.currentPage = 1
        viewModel.getExpressesStore()
    }

    internal fun onStoreItemClick(storeInfoExpress: StoreInfoExpress) {
        val intent = Intent(context, OrderActivity::class.java)
        intent.putExtras(Bundle().apply {
            putLong(StoreInfoFragment.KEY_STORE_ID, storeInfoExpress.storeId)
        })
        context.startActivity(intent)
    }

    internal fun onLoadMoreClick() {

    }

    private fun handleGetStoreListCompleted(notification: Notification<Boolean>) {
        if (notification.isOnNext) {
            notification.value?.let {
                ui.storeAdapter.nextPageFlag = it
                ui.storeAdapter.notifyDataSetChanged()
            }
            ui.swipeRefreshLayout.isRefreshing = false
        }
        if (notification.isOnError) {
            notification.error.let {
                if (it?.message != null && it.message == "Network") {
                    toast(R.string.networkDisconnect)
                }
            }
        }
    }
}
