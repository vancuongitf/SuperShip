package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class TakeBillBody(@SerializedName("token") var token: String,
                        @SerializedName("bill_id") var billId: Long)
