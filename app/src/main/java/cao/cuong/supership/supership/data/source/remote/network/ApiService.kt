package cao.cuong.supership.supership.data.source.remote.network

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.model.User
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.RequestResetPassResponse
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 *
 * @author at-cuongcao.
 */
interface ApiService {

    @POST("api/v1/user/user.php")
    fun createUser(@Body user: CreateUserBody): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/user/login.php")
    fun login(@Field("user") user: String, @Field("pass") pass: String): Single<AccessToken>

    @FormUrlEncoded
    @POST("api/v1/user/user_info.php")
    fun getUserInfo(@Field("token") token: String): Single<UserInfo>

    @GET("api/v1/user/requestresetpass.php")
    fun requestResetPassword(@Query("email") email: String): Single<RequestResetPassResponse>

    @FormUrlEncoded
    @POST("api/v1/user/resetpassword.php")
    fun resetPassword(@Field("user_id") userId: Int, @Field("pass") pass: String, @Field("otp_code") code: Int): Single<AccessToken>

    @GET("api/v1/user/store/express")
    fun getStoreListOfUser(@Query("token") token: String, @Query("page") page: Int): Single<StoreExpressResponse>

    @GET("api/v1/store/store.php")
    fun getStoreInfo(@Query("store_id") storeId: Long): Single<Store>

    @GET("api/v1/store/express.php")
    fun getExpressStore(@Query("advance_param") advanceParam: Int, @Query("page") page: Int, @Query("lat") lat: Double?, @Query("lng") lng: Double?): Single<StoreExpressResponse>

    @GET("api/v1/store/search.php")
    fun search(@Query("query") query: String, @Query("page") page: Int): CustomCall<StoreExpressResponse>

    @Multipart
    @POST("api/v1/upload/upload.php")
    fun uploadImage(@Part imageFile: MultipartBody.Part?): Single<MessageResponse>
}
