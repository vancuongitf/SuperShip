package cao.cuong.supership.supership.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 *
 * @author at-cuongcao.
 */
data class OpenHour(@SerializedName("open_days") var openDays: MutableList<Int>,
                    @SerializedName("open") var open: Int,
                    @SerializedName("close") var close: Int) {

    internal fun sameWithOther(other: OpenHour): Boolean {
        openDays.sort()
        other.openDays.sort()
        return Gson().toJson(this).toString() == Gson().toJson(other).toString()
    }
}
