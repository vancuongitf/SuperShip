package cao.cuong.supership.supership.data.model.paypal

import com.google.gson.annotations.SerializedName

data class VerifyPaymentBody(@SerializedName("user_id") val userId: Long,
                             @SerializedName("bill_id") val billId: Long,
                             @SerializedName("pay_id") val payId: String)
