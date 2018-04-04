package cao.cuong.supership.supership.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class StoreRate(@SerializedName("rate") var rate: Double,
                     @SerializedName("rate_count") var rateCount: Int)
