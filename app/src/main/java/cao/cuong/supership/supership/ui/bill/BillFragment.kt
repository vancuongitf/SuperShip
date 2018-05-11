package cao.cuong.supership.supership.ui.bill

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.model.rxevent.UpdateOrderUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.bill.activity.BillActivity
import cao.cuong.supership.supership.ui.bill.info.BillInfoFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class BillFragment: BaseFragment() {

    private lateinit var ui: BillFragmentUI
    private lateinit var viewModel: BillFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = BillFragmentViewModel(context)
        ui = BillFragmentUI(viewModel.bills)
        ui.billAdapter.onBillExpressClicked = this::onBillItemClicked
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notification
                .observeOnUiThread()
                .subscribe(this::handleGetOrdersComplete)
        RxBus.listen(UpdateAccountUI::class.java).observeOnUiThread().subscribe(this::updateUI)
        RxBus.listen(UpdateOrderUI::class.java).observeOnUiThread().subscribe(this::updateUI)
        updateUI(UpdateAccountUI())
    }

    override fun onBindViewModel() {
    }

    internal fun eventReloadClicked() {
        updateUI(UpdateAccountUI())
    }

    internal fun eventLoginButtonClick() {
        (activity as? BaseActivity)?.startUserActivity()
    }

    private fun handleGetOrdersComplete(notification: Notification<Boolean>) {
        ui.swipeRefreshLayout.isRefreshing = false
        if (notification.isOnNext) {
            ui.recyclerViewBills.visibility = View.VISIBLE
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.GONE
            ui.billAdapter.notifyDataSetChanged()
        } else if (notification.isOnError) {
            ui.recyclerViewBills.visibility = View.GONE
            ui.tvReload.visibility = View.VISIBLE
            ui.llNonLogin.visibility = View.GONE
        }
    }

    private fun updateUI(updateAccountUI: UpdateAccountUI) {
        if (viewModel.isLogin()) {
            ui.recyclerViewBills.visibility = View.VISIBLE
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.GONE
            viewModel.getOrders()
        } else {
            ui.recyclerViewBills.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
        }
    }

    private fun updateUI(updateOrderUI: UpdateOrderUI) {
        if (viewModel.isLogin()) {
            ui.recyclerViewBills.visibility = View.VISIBLE
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.GONE
            viewModel.getOrders()
        } else {
            ui.recyclerViewBills.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
        }
    }

    private fun onBillItemClicked(id: Long) {
        val intent = Intent(context, BillActivity::class.java)
        intent.putExtras(Bundle().apply {
            putLong(BillInfoFragment.KEY_BILL_ID, id)
        })
        startActivity(intent)
    }
}
