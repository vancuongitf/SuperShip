package cao.cuong.supership.supership.ui.store.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.add.CreateStoreFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

class StoreListFragment : BaseFragment() {

    companion object {
        internal const val KEY_USER_ID = "user_id"

        internal fun getNewInstance(userId: Long): StoreListFragment {
            val instance = StoreListFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_USER_ID, userId)
            }
            return instance
        }
    }

    private lateinit var ui: StoreListFragmentUI
    private lateinit var viewModel: StoreListFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = StoreListFragmentViewModel(context)
        ui = StoreListFragmentUI(viewModel.stores)
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

    private fun handleUpdateList(notification: Notification<Boolean>) {
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
}