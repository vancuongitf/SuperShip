package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.GoogleMapDataSource
import cao.cuong.supership.supership.data.source.remote.network.google.GoogleClient
import cao.cuong.supership.supership.extension.toRequestString
import com.google.android.gms.maps.model.LatLng

class GoogleMapRemoteDataSource : GoogleMapDataSource {

    private val apiService = GoogleClient.getInstance(null).service

    override fun getAddress(latLng: LatLng) = apiService.getAddress(latLng.toRequestString())

    override fun searchLocation(input: String) = apiService.searchAddress(input)

    override fun getPlaceDetail(placeId: String) = apiService.getPlaceDetail(placeId)

    override fun getDirection(from: LatLng, to: LatLng) = apiService.direction(from.toRequestString(), to.toRequestString())
}
