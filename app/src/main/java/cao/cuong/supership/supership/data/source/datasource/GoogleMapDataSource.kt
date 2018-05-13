package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.google.Direction
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.response.google.AutoCompleteResponse
import cao.cuong.supership.supership.data.source.remote.response.google.GeoCodingResponse
import cao.cuong.supership.supership.data.source.remote.response.google.PlaceDetailResponse
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface GoogleMapDataSource {

    fun getAddress(latLng: LatLng): Single<GeoCodingResponse>

    fun searchLocation(input: String): CustomCall<AutoCompleteResponse>

    fun getPlaceDetail(placeId: String): Single<PlaceDetailResponse>

    fun getDirection(from: LatLng, to: LatLng): CustomCall<Direction>
}
