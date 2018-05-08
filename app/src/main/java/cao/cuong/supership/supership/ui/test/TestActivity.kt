package cao.cuong.supership.supership.ui.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import cao.cuong.supership.supership.ui.base.BaseActivity
import com.google.gson.Gson
import com.paypal.android.sdk.payments.*
import org.jetbrains.anko.setContentView
import org.json.JSONException
import java.math.BigDecimal


class TestActivity : BaseActivity() {

    private var uri: Uri? = null
    val paymentConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AUYh1YT3xnk6DyTizuZ0GxEH599saIIFnfagSWSqta5U3MtgZlPxuFFe4Qz7SI_BaeKasySJlb9V90JF")

    private lateinit var ui: TestActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = TestActivityUI()
        ui.setContentView(this)

        val intent = Intent(this, PayPalService::class.java)

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)

        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, PayPalService::class.java))
    }

    override fun onBindViewModel() {

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        if (resultCode == Activity.RESULT_OK){
////            when(requestCode){
////                22 -> {
////                    uri = data?.data
////                }
////
////                2 -> {
////
////                }
////            }
////        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val resultUri = result.uri
//                val x = StoreRepository()
//                x.uploadImage(File(resultUri.path))
//                        .observeOnUiThread()
//                        .subscribe({
//                            Glide.with(this)
//                                    .load("http://vnshipperman.000webhostapp.com/uploads/${it.message}" )
//                                    .into(ui.img)
//                            Log.i("tag11", it.message)
//                        }, {
//                            Log.i("tag11", Gson().toJson(it))
//                        })
//                Log.i("tag11", "ok")
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//                Log.i("tag11", Gson().toJson(error))
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            Log.i("paymentExample", "xxxx")
            Log.i("paymentExample", data.extras.toString())
            val confirm = data.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4))

                    Log.i("paymentExample", Gson().toJson(confirm))
                    // TODO: send 'confirm' to your server for verification. 201666023
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.


                } catch (e: JSONException) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e)
                }

            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.")
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.")
        }
    }

    fun chon() {
        val payment = PayPalPayment(BigDecimal("0.01"), "USD", "sample item",
                PayPalPayment.PAYMENT_INTENT_SALE)

        val intent = Intent(this, PaymentActivity::class.java)

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paymentConfig)

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

        startActivityForResult(intent, 22)
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, 22)
    }

    fun crop() {

    }


}