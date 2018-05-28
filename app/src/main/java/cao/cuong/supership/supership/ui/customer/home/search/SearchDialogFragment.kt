package cao.cuong.supership.supership.ui.customer.home.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.order.OrderActivity
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class SearchDialogFragment : BaseFragment() {

    private lateinit var ui: SearchDialogFragmentUI
    private lateinit var viewModel: SearchDialogViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = SearchDialogViewModel(context)
        ui = SearchDialogFragmentUI(viewModel.stores)
        ui.storeAdapter.onItemClicked = this::adapterItemClick
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.search("")
    }

    override fun onBindViewModel() {
        addDisposables(viewModel.updateListObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateStoreList))
    }

    internal fun handleSearchViewTextChange(query: String) {
        viewModel.stores.clear()
        ui.storeAdapter.notifyDataSetChanged()
        viewModel.search(query)
    }

    private fun handleUpdateStoreList(notification: Notification<Boolean>) {
        if (notification.isOnNext) {
            if (notification.value != null) {
                ui.storeAdapter.nextPageFlag = notification.value!!
            } else {
                ui.storeAdapter.nextPageFlag = false
            }
            ui.storeAdapter.notifyDataSetChanged()
        }
    }

    private fun adapterItemClick(store: StoreInfoExpress) {
        viewModel.saveHistory(store)
        val intent = Intent(context, OrderActivity::class.java)
        intent.putExtras(Bundle().apply {
            putLong(StoreInfoFragment.KEY_STORE_ID, store.storeId)
        })
        context.startActivity(intent)
    }
}
