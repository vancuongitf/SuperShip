package cao.cuong.supership.supership.ui.shipper.bill.checked

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import cao.cuong.supership.supership.ui.customer.bill.info.BillInfoFragment
import io.reactivex.Notification
import org.jetbrains.anko.AnkoContext

class CheckedBillFragment : BaseFragment() {

    private lateinit var ui: CheckedBillFragmentUI
    private lateinit var viewModel: CheckedBillFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = CheckedBillFragmentViewModel(context)
        ui = CheckedBillFragmentUI(viewModel.checkedBills)
        ui.checkedBillAdapter.onBillExpressClicked = {
            val intent = Intent(context, BillActivity::class.java)
            intent.putExtras(Bundle().apply {
                putLong(BillInfoFragment.KEY_BILL_ID, it)
            })
            startActivity(intent)
        }
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateListObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateList)
        getCheckedBills()
        RxBus.listen(UpdateAccountUI::class.java)
                .observeOnUiThread()
                .subscribe({
                    getCheckedBills()
                }, {})
    }

    override fun onBindViewModel() {
    }

    internal fun onRefreshData() {
        getCheckedBills()
    }

    private fun handleUpdateList(notification: Notification<Boolean>) {
        ui.swipeRefreshLayout.isRefreshing = false
        if (notification.isOnNext) {
            ui.checkedBillAdapter.notifyDataSetChanged()
        } else if (notification.isOnError) {
            handleApiError(notification.error)
        }
    }

    private fun getCheckedBills() {
        if (viewModel.isLogin()) {
            viewModel.getCheckedBills()
        } else {
            ui.swipeRefreshLayout.isRefreshing = false
            RxBus.publish(OpenUserActivityAlert())
        }
    }
}
