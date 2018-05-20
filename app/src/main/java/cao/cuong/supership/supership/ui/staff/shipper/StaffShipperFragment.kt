package cao.cuong.supership.supership.ui.staff.shipper

import android.content.Intent
import android.os.Bundle
import android.view.View
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.staff.StaffActivity
import cao.cuong.supership.supership.ui.staff.base.StaffBaseFragment
import cao.cuong.supership.supership.ui.staff.shipper.info.StaffShipperInfoFragment
import io.reactivex.Notification
import java.net.HttpURLConnection

class StaffShipperFragment : StaffBaseFragment() {

    internal lateinit var viewModel: StaffShipperFragmentViewModel
    internal lateinit var adapter: ShipperAdapter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.onChangeListStatusClicked = this::onChangeStatusClicked
        ui.llUserStatusChange.visibility = View.VISIBLE
        viewModel = StaffShipperFragmentViewModel(context)
        adapter = ShipperAdapter(viewModel.shippers)
        adapter.onItemClicked = {
            val intent = Intent(context, StaffActivity::class.java)
            val bundle = Bundle().apply {
                putInt(StaffActivity.KEY_SCREEN, StaffActivity.SHIPPER_SCREEN)
                putLong(StaffShipperInfoFragment.KEY_SHIPPER_ID, it)
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }
        ui.recyclerViewBills.adapter = adapter
        viewModel.updateListObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateList)
        getShippers("")
    }

    override fun onRefresh() {
        ui.edtSearch.setText("")
        viewModel.currentPage = 1
        getShippers("")
    }

    private fun onChangeStatusClicked(status: Int) {
        when (status) {
            0 -> {
                ui.imgUserWaiting.setImageResource(R.drawable.ic_user_waiting_red)
                ui.imgUserChecked.setImageResource(R.drawable.ic_user_checked_black)
                ui.imgUserBanned.setImageResource(R.drawable.ic_banned_user_black)
                viewModel.currentPage = 1
                viewModel.currentStatus = 0
                ui.edtSearch.setText("")
                getShippers("")
            }

            1 -> {
                ui.imgUserWaiting.setImageResource(R.drawable.ic_user_waiting_black)
                ui.imgUserChecked.setImageResource(R.drawable.ic_user_checked_red)
                ui.imgUserBanned.setImageResource(R.drawable.ic_banned_user_black)
                viewModel.currentPage = 1
                viewModel.currentStatus = 1
                ui.edtSearch.setText("")
                getShippers("")
            }

            2 -> {
                ui.imgUserWaiting.setImageResource(R.drawable.ic_user_waiting_black)
                ui.imgUserChecked.setImageResource(R.drawable.ic_user_checked_black)
                ui.imgUserBanned.setImageResource(R.drawable.ic_banned_user_red)
                viewModel.currentPage = 1
                viewModel.currentStatus = 2
                ui.edtSearch.setText("")
                getShippers("")
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

    private fun getShippers(search: String) {
        if (viewModel.isLogin()) {
            viewModel.getShipper(search)
        } else {
            RxBus.publish(OpenUserActivityAlert())
        }
    }

    private fun handleOnTextChange(search: String) {
        viewModel.currentPage = 1
        getShippers(search)
    }
}
