package cao.cuong.supership.supership.ui.shipper.bill.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.rxevent.UpdateBillStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.TakeBillBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext


class ShipperBillInfoFragment : BaseFragment() {

    companion object {
        internal const val KEY_BILL_ID = "key_id"

        internal fun getNewInstance(id: Long): ShipperBillInfoFragment {
            val instance = ShipperBillInfoFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_BILL_ID, id)
            }
            return instance
        }
    }

    private lateinit var ui: ShipperBillInfoFragmentUI
    private lateinit var viewModel: ShipperBillInfoFragmentViewModel
    private var bill: BillInfo? = null
    private val drinks = mutableListOf<OrderedDrink>()
    private var billId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        billId = arguments.getLong(KEY_BILL_ID)
        viewModel = ShipperBillInfoFragmentViewModel(context)
        ui = ShipperBillInfoFragmentUI(drinks)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.tvBillId.text = context.getString(R.string.billInfoTitle, billId)
        viewModel.updateProgressStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        getBillInfo()
        RxBus.listen(UpdateBillStatus::class.java)
                .observeOnUiThread()
                .subscribe({
                    ui.rlCompleted.visibility = View.GONE
                })
    }

    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun eventShowShipRoadClick() {
        bill?.let {
            val billInfo = it
            (activity as? BillActivity)?.openShipRoadFragment(billId, billInfo.store.address, billInfo.address, billInfo.shipRoad)
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
            if (bill.shipperId != bill.requestShipper) {
                tvCustomerName.visibility = View.GONE
                tvCustomerPhone.visibility = View.GONE
            } else {
                tvCustomerName.visibility = View.VISIBLE
                tvCustomerPhone.visibility = View.VISIBLE
            }
            if (bill.status == 1) {
                ui.rlRegister.visibility = View.VISIBLE
                ui.rlCompleted.visibility = View.GONE
                ui.imgShowShipRoad.visibility = View.GONE
            } else if (bill.status == 2) {
                ui.rlRegister.visibility = View.GONE
                if (bill.shipperId != bill.requestShipper) {
                    ui.rlCompleted.visibility = View.GONE
                    ui.imgShowShipRoad.visibility = View.GONE
                } else {
                    ui.rlCompleted.visibility = View.VISIBLE
                    ui.imgShowShipRoad.visibility = View.VISIBLE
                }
            }
            ui.billDrinkAdapter.notifyDataSetChanged()
        }
    }

    internal fun onRegisterClick() {
        context.showConfirmAlert(R.string.takeBill) {
            viewModel.takeBill(TakeBillBody(viewModel.getAccessToken(), billId))
                    .observeOnUiThread()
                    .subscribe(this::handleTakeBillSuccess, this::handleApiError)
        }
    }

    internal fun onCompleteClick() {
        (activity as? BillActivity)?.openCompleteBillFragment(billId)
    }

    internal fun handleTakeBillSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            getBillInfo()
        }
    }

    private fun getBillInfo() {
        viewModel.getBillInfo(billId)
                .observeOnUiThread()
                .subscribe(this::handleGetInfoSuccess, this::handleApiError)
    }
}
