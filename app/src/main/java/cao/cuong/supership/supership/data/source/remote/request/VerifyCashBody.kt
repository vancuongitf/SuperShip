package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class VerifyCashBody(@SerializedName("shipper_id") var shipperId: Long,
                          @SerializedName("pay_id") var payId: String)
