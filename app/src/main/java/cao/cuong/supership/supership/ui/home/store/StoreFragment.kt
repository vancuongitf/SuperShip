package cao.cuong.supership.supership.ui.home.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

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

    private fun handleGetStoreListCompleted(notification: Notification<Boolean>) {
        if (notification.isOnNext) {
            notification.value?.let {
                ui.storeAdapter.nextPageFlag = true
                ui.storeAdapter.notifyDataSetChanged()
            }
            ui.swipeRefreshLayout.isRefreshing = false
        }
        if (notification.isOnError) {

        }
    }
}
