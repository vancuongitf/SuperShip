package cao.cuong.supership.supership.data.source.remote.response.google

import cao.cuong.supership.supership.data.model.google.AutoComplete
import com.google.gson.annotations.SerializedName

data class AutoCompleteResponse(@SerializedName("predictions") val predictions: List<AutoComplete>)
