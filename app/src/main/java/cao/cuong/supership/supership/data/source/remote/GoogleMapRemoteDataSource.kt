package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.GoogleMapDataSource
import cao.cuong.supership.supership.data.source.remote.network.google.GoogleClient
import cao.cuong.supership.supership.data.source.remote.response.google.PlaceDetailResponse
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

class GoogleMapRemoteDataSource : GoogleMapDataSource {

    private val apiService = GoogleClient.getInstance(null).service

    override fun getAddress(latLng: LatLng) = apiService.getAddress("${latLng.latitude},${latLng.longitude}")

    override fun searchLocation(input: String) = apiService.searchAddress(input)

    override fun getPlaceDetail(placeId: String) = apiService.getPlaceDetail(placeId)
}
