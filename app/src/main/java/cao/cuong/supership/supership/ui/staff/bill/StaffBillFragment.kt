package cao.cuong.supership.supership.ui.staff.bill

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.DeleteItemList
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import cao.cuong.supership.supership.ui.customer.bill.info.BillInfoFragment
import cao.cuong.supership.supership.ui.shipper.bill.checked.CheckedBillAdapter
import cao.cuong.supership.supership.ui.staff.base.StaffBaseFragment
import io.reactivex.Notification
import java.net.HttpURLConnection

class StaffBillFragment : StaffBaseFragment() {

    private lateinit var adapter: CheckedBillAdapter
    private lateinit var viewModel: StaffBillFragmentViewModel

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = StaffBillFragmentViewModel(context)
        adapter = CheckedBillAdapter(viewModel.bills)
        ui.recyclerViewBills.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.onBillExpressClicked = {
            val intent = Intent(context, BillActivity::class.java)
            intent.putExtras(Bundle().apply {
                putLong(BillInfoFragment.KEY_BILL_ID, it)
            })
            startActivity(intent)
        }
        ui.edtSearch.hint = "Tìm bằng id đơn hàng"
        ui.onChangeListStatusClicked = this::onChangeStatusClicked
        ui.onTextChange = this::handleOnTextChange
        ui.tvTitle.text = context.getString(R.string.bills)
        viewModel.updateProgressDialogStatus
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        viewModel.updateListObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateList)
        RxBus.listen(UpdateAccountUI::class.java)
                .observeOnUiThread()
                .subscribe({
                    this.getBills(ui.edtSearch.text.toString().trim())
                })
        RxBus.listen(DeleteItemList::class.java)
                .observeOnUiThread()
                .subscribe {
                    val id = it.id
                    viewModel.bills.remove(viewModel.bills.find { it.id == id })
                    adapter.notifyDataSetChanged()
                }
        getBills("")
        Handler().postDelayed({
            hideKeyboard()
        }, 1000L)
    }

    override fun onBindViewModel() {
    }

    override fun onRefresh() {
        ui.edtSearch.setText("")
        viewModel.currentPage = 1
        getBills("")
    }

    private fun onChangeStatusClicked(status: Int) {
        when (status) {
            0 -> {
                ui.imgWaiting.setImageResource(R.drawable.ic_waiting_red)
                ui.imgChecked.setImageResource(R.drawable.ic_check_list)
                ui.imgTransit.setImageResource(R.drawable.ic_transit)
                ui.imgDone.setImageResource(R.drawable.ic_done)
                viewModel.currentPage = 1
                viewModel.currentStatus = 0
                ui.edtSearch.setText("")
                getBills("")
            }

            1 -> {
                ui.imgWaiting.setImageResource(R.drawable.ic_waiting)
                ui.imgChecked.setImageResource(R.drawable.ic_check_list_red)
                ui.imgTransit.setImageResource(R.drawable.ic_transit)
                ui.imgDone.setImageResource(R.drawable.ic_done)
                viewModel.currentPage = 1
                viewModel.currentStatus = 1
                ui.edtSearch.setText("")
                getBills("")
            }

            2 -> {
                ui.imgWaiting.setImageResource(R.drawable.ic_waiting)
                ui.imgChecked.setImageResource(R.drawable.ic_check_list)
                ui.imgTransit.setImageResource(R.drawable.ic_transit_red)
                ui.imgDone.setImageResource(R.drawable.ic_done)
                viewModel.currentPage = 1
                viewModel.currentStatus = 2
                ui.edtSearch.setText("")
                getBills("")
            }

            3 -> {
                ui.imgWaiting.setImageResource(R.drawable.ic_waiting)
                ui.imgChecked.setImageResource(R.drawable.ic_check_list)
                ui.imgTransit.setImageResource(R.drawable.ic_transit)
                ui.imgDone.setImageResource(R.drawable.ic_done_red)
                viewModel.currentPage = 1
                viewModel.currentStatus = 3
                ui.edtSearch.setText("")
                getBills("")
            }
        }
    }

    private fun handleUpdateList(notification: Notification<Boolean>) {
        ui.swipeRefreshLayout.isRefreshing = false
        hideKeyboard()
        if (notification.isOnNext) {
            adapter.notifyDataSetChanged()
        } else if (notification.isOnError) {
            notification.error?.let {
                if (it is ApiException) {
                    if (it.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        RxBus.publish(OpenUserActivityAlert())
                    }
                }
            }
        }
    }

    private fun getBills(id: String) {
        if (viewModel.isLogin()) {
            viewModel.getBills(id)
        } else {
            RxBus.publish(OpenUserActivityAlert())
        }
    }

    private fun handleOnTextChange(id: String) {
        viewModel.currentPage = 1
        getBills(id)
    }
}
