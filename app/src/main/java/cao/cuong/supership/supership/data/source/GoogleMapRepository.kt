package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.GoogleMapDataSource
import cao.cuong.supership.supership.data.source.remote.GoogleMapRemoteDataSource
import com.google.android.gms.maps.model.LatLng

class GoogleMapRepository : GoogleMapDataSource {

    private val googleMapRemoteDataSource = GoogleMapRemoteDataSource()

    override fun getAddress(latLng: LatLng) = googleMapRemoteDataSource.getAddress(latLng)

    override fun searchLocation(input: String) = googleMapRemoteDataSource.searchLocation(input)

    override fun getPlaceDetail(placeId: String) = googleMapRemoteDataSource.getPlaceDetail(placeId)
}
