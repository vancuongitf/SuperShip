package cao.cuong.supership.supership.data.source.remote.network.google

import cao.cuong.supership.supership.data.model.google.Direction
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.response.google.AutoCompleteResponse
import cao.cuong.supership.supership.data.source.remote.response.google.GeoCodingResponse
import cao.cuong.supership.supership.data.source.remote.response.google.PlaceDetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApiService {

    @GET("geocode/json")
    fun getAddress(@Query("latlng") latLng: String,
                   @Query("key") key: String = "AIzaSyCabmLe7-aIFWUOgNW8oRnnmGwlgndcOVc"): Single<GeoCodingResponse>

    @GET("place/autocomplete/json")
    fun searchAddress(@Query("input") input: String,
                      @Query("key") key: String = "AIzaSyDcXFSC2I6ZqxQeAbUMFvJKMrA98217H9U"): CustomCall<AutoCompleteResponse>

    @GET("place/details/json")
    fun getPlaceDetail(@Query("place_id") placeId: String,
                       @Query("key") key: String = "AIzaSyDcXFSC2I6ZqxQeAbUMFvJKMrA98217H9U"): Single<PlaceDetailResponse>

    @GET("directions/json")
    fun direction(@Query("origin") from: String,
                  @Query("destination") to: String): Single<Direction>
}
