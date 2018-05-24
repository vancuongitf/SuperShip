package cao.cuong.supership.supership.ui.customer.bill.info

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.paypal.PayPalPaymentInfo
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.getBillStatus
import cao.cuong.supership.supership.extension.getDateTimeFormat
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.bill.activity.BillActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.paypal.android.sdk.payments.*
import org.jetbrains.anko.AnkoContext
import org.json.JSONException
import java.math.BigDecimal


class BillInfoFragment : BaseFragment() {

    companion object {
        internal const val KEY_BILL_ID = "key_id"
        internal const val REQUEST_PAYMENT_PAY_PAL = 2203

        internal fun getNewInstance(id: Long): BillInfoFragment {
            val instance = BillInfoFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_BILL_ID, id)
            }
            return instance
        }
    }

    private lateinit var ui: BillInfoFragmentUI
    private lateinit var viewModel: BillInfoFragmentViewModel
    private var bill: BillInfo? = null
    private val drinks = mutableListOf<OrderedDrink>()
    private var billId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        billId = arguments.getLong(KEY_BILL_ID)
        viewModel = BillInfoFragmentViewModel(context)
        ui = BillInfoFragmentUI(drinks)
        ui.billDrinkAdapter.onItemClicked = this::drinkAdapterItemClicked
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_PAYMENT_PAY_PAL -> {
                    val confirm = data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                    if (confirm != null) {
                        try {
                            val paymentInfo = Gson().fromJson<PayPalPaymentInfo>(confirm.toJSONObject().toString(4), PayPalPaymentInfo::class.java)
                            if (paymentInfo.response.isApproved()) {
                                viewModel.getUserId()?.let {
                                    viewModel.verifyPayment(it, billId, paymentInfo.response.id)
                                            .observeOnUiThread()
                                            .subscribe(this::handleVerifyPaymentSuccess, this::handleApiError)
                                }
                            }
                        } catch (e: JSONException) {
                            handleApiError(Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
                        }
                    }
                }
            }
        }
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

    internal fun paymentClicked() {
        bill?.let {
            val paymentConfig = PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId("AUYh1YT3xnk6DyTizuZ0GxEH599saIIFnfagSWSqta5U3MtgZlPxuFFe4Qz7SI_BaeKasySJlb9V90JF")
            val payPalServiceIntent = Intent(context, PayPalService::class.java)
            payPalServiceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)
            context.startService(payPalServiceIntent)
            val dolar = it.getUSDPrice()
            val payment = PayPalPayment(BigDecimal(dolar), "USD", "Order Id: ${it.id}",
                    PayPalPayment.PAYMENT_INTENT_SALE)
            val payPalActivityIntent = Intent(context, PaymentActivity::class.java)

            // send the same configuration for restart resiliency
            payPalActivityIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)
            payPalActivityIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivityForResult(payPalActivityIntent, REQUEST_PAYMENT_PAY_PAL)
        }
    }

    private fun handleGetInfoSuccess(bill: BillInfo) {
        this.bill = bill
        (activity as? BillActivity)?.billInfo = bill
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
            if (bill.isOnlinePayment() || bill.status == 3) {
                ui.rlPayment.visibility = View.GONE
            } else {
                ui.rlPayment.visibility = View.VISIBLE
            }
            imgShowShipRoad.visibility = View.VISIBLE
            ui.billDrinkAdapter.notifyDataSetChanged()
        }
    }

    private fun handleVerifyPaymentSuccess(messageResponse: MessageResponse) {
        ui.rlPayment.visibility = View.GONE
        context.showOkAlert(R.string.notification, messageResponse.message)
    }

    private fun drinkAdapterItemClicked(position: Int) {
        (activity as? BillActivity)?.openOrderedDrinkFragment(position)
    }
}
