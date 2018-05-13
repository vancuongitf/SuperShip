package cao.cuong.supership.supership.ui.store.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.store.create.CreateStoreFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

class StoreListFragment : BaseFragment() {

    private lateinit var ui: StoreListFragmentUI
    private lateinit var viewModel: StoreListFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = StoreListFragmentViewModel(context)
        ui = StoreListFragmentUI(viewModel.stores)
        ui.storeAdapter.onItemClicked = this::storeAdapterItemClicked
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observableUpdateList
                .observeOnUiThread()
                .subscribe(this::handleUpdateList)
        viewModel.observableProgressStatus
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressStatus)
        viewModel.getStoreListOfUser()
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun addStoreClicked() {
        activity.addFragment(R.id.storeActivityContainer, CreateStoreFragment(), {}, CreateStoreFragment::class.java.simpleName)
    }

    internal fun reloadData(){
        viewModel.getStoreListOfUser()
    }

    private fun handleUpdateList(notification: Notification<Boolean>) {
        ui.swipeRefreshLayout.isRefreshing = false
        if (notification.isOnNext) {
            ui.storeAdapter.notifyDataSetChanged()
            if (viewModel.stores.isEmpty()) {
                ui.tvNonStore.visibility = View.VISIBLE
                ui.recyclerView.visibility = View.GONE
            } else {
                ui.tvNonStore.visibility = View.GONE
                ui.recyclerView.visibility = View.VISIBLE
            }
        } else if (notification.isOnError) {
            handleApiError(notification.error)
        }
    }

    private fun handleUpdateProgressStatus(status: Boolean) {
        if (status) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    private fun storeAdapterItemClicked(storeInfoExpress: StoreInfoExpress) {
        (activity as? StoreActivity)?.openStoreInfoFragment(storeInfoExpress.storeId)
    }
}
