package cao.cuong.supership.supership.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class CompleteBillBody(@SerializedName("token") var token: String,
                            @SerializedName("bill_id") var billId: Long,
                            @SerializedName("confirm_code") var confirmCode: String)
