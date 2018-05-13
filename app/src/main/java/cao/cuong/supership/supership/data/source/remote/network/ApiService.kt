package cao.cuong.supership.supership.data.source.remote.network

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.model.paypal.VerifyPaymentBody
import cao.cuong.supership.supership.data.source.remote.request.*
import cao.cuong.supership.supership.data.source.remote.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
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
    @POST("api/v1/user/password/change/index.php")
    fun changePassword(@Field("old_pass") oldPass: String,
                       @Field("pass") newPass: String,
                       @Field("token") token: String): Single<MessageResponse>

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

    @POST("api/v1/store/create/create.php")
    fun createStore(@Body createStoreBody: CreateStoreBody): Single<MessageResponse>

    @POST("api/v1/store/drink/create.php")
    fun createDrink(@Body drinkBody: CreateDrinkBody): Single<MessageResponse>

    @POST("api/v1/store/drink/edit.php")
    fun editDrink(@Body drinkBody: EditDrinkBody): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/store/drink/delete.php")
    fun deleteDrink(@Field("token") token: String, @Field("id") id: Long): Single<MessageResponse>

    @POST("api/v1/store/option/create.php")
    fun createDrinkOption(@Body createOptionBody: CreateDrinkOptionBody): Single<CreateDrinkOptionResponse>

    @POST("api/v1/store/option/edit.php")
    fun editDrinkOption(@Body editDrinkOptionBody: EditDrinkOptionBody): Single<MessageResponse>

    @POST("api/v1/store/option/addoptionitem.php")
    fun addDrinkItemOption(@Body body: AddDrinkOptionItemBody): Single<MessageResponse>

    @POST("/api/v1/store/drink/order.php")
    fun orderDrink(@Body billBody: BillBody): Single<MessageResponse>

    @GET("api/v1/store/order/userorders.php")
    fun getOrdersOfUser(@Query("token") token: String, @Query("page") page: Int): Single<BillExpressResponse>

    @GET("api/v1/store/order/order.php")
    fun getOrderInfo(@Query("token") token: String, @Query("id") id: Long): Single<BillInfo>

    @POST("api/v1/user/payment/verify.php")
    fun verifyPayment(@Body paymentBody: VerifyPaymentBody): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/store/option/delete.php")
    fun deleteDrinkOption(@Field("token") token: String, @Field("id") id: Long): Single<MessageResponse>
}
