package cao.cuong.supership.supership.data.source.remote.network

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single
import retrofit2.http.*

/**
 *
 * @author at-cuongcao.
 */
interface ApiService {

    @FormUrlEncoded
    @POST("api/v1/user/login.php")
    fun login(@Field("user") user: String, @Field("pass") pass: String): CustomCall<AccessToken>

    fun getStoreInfo(@Query("store_id") storeId: Long): Single<Store>

    @GET("api/v1/store/express.php")
    fun getExpressStore(@Query("advance_param") advanceParam: Int, @Query("page") page: Int, @Query("lat") lat: Double?, @Query("lng") lng: Double?): Single<StoreExpressResponse>

    @GET("api/v1/store/search.php")
    fun search(@Query("query") query: String, @Query("page") page: Int): CustomCall<StoreExpressResponse>
}
