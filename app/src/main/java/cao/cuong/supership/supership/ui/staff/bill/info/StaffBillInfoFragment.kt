package cao.cuong.supership.supership.ui.staff.bill.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.rxevent.DeleteItemList
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext


class StaffBillInfoFragment : BaseFragment() {

    companion object {
        internal const val KEY_BILL_ID = "key_id"

        internal fun getNewInstance(id: Long): StaffBillInfoFragment {
            val instance = StaffBillInfoFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_BILL_ID, id)
            }
            return instance
        }
    }

    private lateinit var ui: StaffBillInfoFragmentUI
    private lateinit var viewModel: StaffBillInfoFragmentViewModel
    private var bill: BillInfo? = null
    private val drinks = mutableListOf<OrderedDrink>()
    private var billId = -1L
    private var expectStatus = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        billId = arguments.getLong(KEY_BILL_ID)
        viewModel = StaffBillInfoFragmentViewModel(context)
        ui = StaffBillInfoFragmentUI(drinks)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.tvBillId.text = context.getString(R.string.billInfoTitle, billId)
        viewModel.updateProgressStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        viewModel.getBillInfo(billId)
                .observeOnUiThread()
                .subscribe(this::handleGetInfoSuccess, this::handleApiError)
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun eventShowShipRoadClick() {
        ui.imgShowShipRoad.visibility = View.GONE
        bill?.let {
            val billInfo = it
            (activity as? BillActivity)?.openShipRoadFragment(billId, billInfo.store.address, billInfo.address, billInfo.shipRoad)
        }
    }

    internal fun checkBill(status: Int) {
        expectStatus = status
        var ms = R.string.banBillConfirm
        if (status == 1) {
            ms = R.string.checkBillConfirm
        }
        context.showConfirmAlert(ms) {
            viewModel.checkBill(billId, status)
                    .observeOnUiThread()
                    .subscribe(this::handleCheckBillSuccess, this::handleApiError)
        }
    }

    private fun handleGetInfoSuccess(bill: BillInfo) {
        this.bill = bill
        with(ui) {
            val option = RequestOptions()
                    .placeholder(R.drawable.glide_place_holder)
            Glide.with(context)
                    .applyDefaultRequestOptions(option)
                    .asBitmap()
                    .load(BuildConfig.BASE_IMAGE_URL + bill.store.image)
                    .into(imgStoreAvatar)
            tvStoreName.text = context.getString(R.string.billStoreName, bill.store.name)
            tvStoreAddress.text = context.getString(R.string.billStoreAddress, bill.store.address.address)
            tvCustomerName.text = context.getString(R.string.billCustomerName, bill.customerName)
            tvCustomerPhone.text = context.getString(R.string.billCustomerPhone, bill.customerPhone)
            tvBillAddress.text = context.getString(R.string.billUserAddress, bill.address.address)
            tvOrderTime.text = context.getString(R.string.orderAt, bill.time.getDateTimeFormat())
            tvOrderPrice.text = context.getString(R.string.drinkPrice, bill.price)
            tvShipPrice.text = context.getString(R.string.shipPrice, bill.shipPrice)
            tvTotalPrice.text = context.getString(R.string.totalPrice, bill.price + bill.shipPrice)
            tvStatus.text = context.getString(R.string.status, bill.status.getBillStatus())
            drinks.addAll(bill.drinks)
            if (bill.status == 0) {
                ui.rlChecked.visibility = View.VISIBLE
                ui.rlBanned.visibility = View.VISIBLE
            } else {
                ui.rlChecked.visibility = View.GONE
                ui.rlBanned.visibility = View.GONE
            }
            imgShowShipRoad.visibility = View.VISIBLE
            ui.billDrinkAdapter.notifyDataSetChanged()
        }
    }

    private fun handleCheckBillSuccess(messageResponse: MessageResponse) {
        RxBus.publish(DeleteItemList(billId))
        ui.rlChecked.visibility = View.GONE
        ui.rlBanned.visibility = View.GONE
        ui.tvStatus.text = context.getString(R.string.status, expectStatus.getBillStatus())
        context.showOkAlert(R.string.notification, messageResponse.message)
    }
}
