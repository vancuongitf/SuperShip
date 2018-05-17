package cao.cuong.supership.supership.ui.shipper.cash

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.paypal.PayPalPaymentInfo
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.VerifyCashBody
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.customer.bill.info.BillInfoFragment
import com.google.gson.Gson
import com.paypal.android.sdk.payments.*
import org.jetbrains.anko.setContentView
import org.json.JSONException
import java.math.BigDecimal

class CashActivity : BaseActivity() {

    companion object {
        internal const val KEY_SHIPPER_ID = "key_shipper_id"
    }

    private lateinit var ui: CashActivityUI
    private lateinit var viewModel: CashActivityViewModel
    private var shipperId = -1L
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shipperId = intent.extras.getLong(KEY_SHIPPER_ID)
        ui = CashActivityUI()
        ui.setContentView(this)
        viewModel = CashActivityViewModel(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        viewModel.updateProgressStatusObservable
                .observeOnUiThread()
                .subscribe({
                    if (it) {
                        progressDialog.show()
                    } else {
                        progressDialog.dismiss()
                    }
                })
    }

    override fun onBindViewModel() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                BillInfoFragment.REQUEST_PAYMENT_PAY_PAL -> {
                    val confirm = data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                    if (confirm != null) {
                        try {
                            val paymentInfo = Gson().fromJson<PayPalPaymentInfo>(confirm.toJSONObject().toString(4), PayPalPaymentInfo::class.java)
                            if (paymentInfo.response.isApproved()) {
                                viewModel.verifyCash(VerifyCashBody(shipperId, paymentInfo.response.id))
                                        .observeOnUiThread()
                                        .subscribe({
                                            showOkAlert(R.string.notification, "Nạp tiền vào tài khoản thành công.") {
                                                finish()
                                                RxBus.publish(UpdateAccountUI())
                                            }
                                        }, {
                                            showOkAlert(it)
                                        })
                            }
                        } catch (e: JSONException) {
                            showOkAlert(Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
                        }
                    }
                }
            }
        }
    }

    internal fun onBackClicked() {
        hideKeyBoard()
        onBackPressed()
    }

    internal fun eventOnCashClicked() {
        try {
            val cash = ui.edtCash.text.toString().trim().toInt()
            val paymentConfig = PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId("AUYh1YT3xnk6DyTizuZ0GxEH599saIIFnfagSWSqta5U3MtgZlPxuFFe4Qz7SI_BaeKasySJlb9V90JF")
            val payPalServiceIntent = Intent(this, PayPalService::class.java)
            payPalServiceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)
            startService(payPalServiceIntent)
            val dolar = cash.toDouble() / BuildConfig.USD_EXCHANGE_RATE
            val payment = PayPalPayment(BigDecimal(dolar), "USD", "Nạp tiền cho shipper: ${shipperId}",
                    PayPalPayment.PAYMENT_INTENT_SALE)
            val payPalActivityIntent = Intent(this, PaymentActivity::class.java)

            // send the same configuration for restart resiliency
            payPalActivityIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)
            payPalActivityIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivityForResult(payPalActivityIntent, BillInfoFragment.REQUEST_PAYMENT_PAY_PAL)
        } catch (e: Exception) {
            showOkAlert(Throwable("Vui lòng nhập số tiền đúng định dạng."))
        }
    }
}
