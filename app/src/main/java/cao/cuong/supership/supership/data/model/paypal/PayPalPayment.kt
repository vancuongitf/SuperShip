package cao.cuong.supership.supership.data.model.paypal

import com.google.gson.annotations.SerializedName

class PayPalPaymentInfo(@SerializedName("response") val response: PaymentResponse)

data class PaymentResponse(@SerializedName("id") val id: String,
                           @SerializedName("state") val state: String) {

    internal fun isApproved() = state == "approved"
}
