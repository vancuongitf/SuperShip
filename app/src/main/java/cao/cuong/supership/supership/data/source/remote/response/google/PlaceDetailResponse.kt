package cao.cuong.supership.supership.data.source.remote.response.google

import cao.cuong.supership.supership.data.model.google.PlaceDetail
import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(@SerializedName("result") val placeDetail: PlaceDetail)
