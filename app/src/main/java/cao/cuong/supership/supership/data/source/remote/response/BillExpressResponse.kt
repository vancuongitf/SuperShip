package cao.cuong.supership.supership.data.source.remote.response

import cao.cuong.supership.supership.data.model.ExpressBill
import com.google.gson.annotations.SerializedName

data class BillExpressResponse(@SerializedName("next_page_flag") var nextPageFlag: Boolean,
                               @SerializedName("bill_list") val orders: MutableList<ExpressBill>)
