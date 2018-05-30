package cao.cuong.supership.supership.data.source.remote.network

import cao.cuong.supership.supership.data.model.*
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
    fun login(@Field("user") user: String, @Field("pass") pass: String): Single<UserInfo>

    @FormUrlEncoded
    @POST("api/v1/user/password/change/index.php")
    fun changePassword(@Field("old_pass") oldPass: String,
                       @Field("pass") newPass: String,
                       @Field("token") token: String): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/user/user_info.php")
    fun getUserInfo(@Field("token") token: String): Single<UserInfo>

    @FormUrlEncoded
    @POST("api/v1/user/info/edit.php")
    fun updateUserInfo(@Field("user_id") userId: Long, @Field("name") name: String, @Field("phone") phone: String): Single<MessageResponse>

    @GET("api/v1/user/requestresetpass.php")
    fun requestResetPassword(@Query("email") email: String): Single<RequestResetPassResponse>

    @FormUrlEncoded
    @POST("api/v1/user/resetpassword.php")
    fun resetPassword(@Field("user_id") userId: Long, @Field("pass") pass: String, @Field("otp_code") code: Int): Single<AccessToken>

    @GET("api/v1/user/store/express")
    fun getStoreListOfUser(@Query("token") token: String, @Query("page") page: Int): Single<StoreExpressResponse>

    @GET("api/v1/store/store.php")
    fun getStoreInfo(@Query("store_id") storeId: Long): Single<Store>

    @FormUrlEncoded
    @POST("api/v1/store/rating/comments.php")
    fun getStoreComments(@Field("user_id") userId: Long, @Field("store_id") storeId: Long, @Field("page") page: Int): CustomCall<StoreCommentResponse>

    @FormUrlEncoded
    @POST("api/v1/store/rating/rating.php")
    fun storeRating(@Field("user_id") userId: Long, @Field("store_id") storeId: Long, @Field("rate") rate: Int): Single<List<Rating>>

    @FormUrlEncoded
    @POST("api/v1/store/rating/comment.php")
    fun storeComment(@Field("user_id") userId: Long, @Field("store_id") storeId: Long, @Field("comment") comment: String): Single<StoreCommentResponse>

    @POST("api/v1/store/update.php")
    fun editStoreInfo(@Body editStoreBody: EditStoreBody): Single<MessageResponse>

    @POST("api/v1/store/updatestatus.php")
    fun updateStoreStatus(@Body editStoreBody: UpdateStoreStatusBody): Single<MessageResponse>

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

    @GET("api/v1/store/order/order.php")
    fun getOrderInfo(@Query("token") token: String, @Query("id") id: Long, @Query("module") module: Int = 0): Single<BillInfo>

    @POST("api/v1/user/payment/verify.php")
    fun verifyPayment(@Body paymentBody: VerifyPaymentBody): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/store/option/delete.php")
    fun deleteDrinkOption(@Field("token") token: String, @Field("id") id: Long): Single<MessageResponse>

    // Shipper
    @POST("api/v1/shipper/create.php")
    fun createShipper(@Body createShipperBody: CreateShipperBody): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/shipper/login.php")
    fun loginShipper(@Field("user") account: String, @Field("pass") pass: String): Single<Shipper>

    @FormUrlEncoded
    @POST("api/v1/shipper/password/change.php")
    fun changeShipperPassword(@Field("shipper_id") shipperId: Long, @Field("old_pass") oldPass: String, @Field("new_pass") newPass: String): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/shipper/info.php")
    fun getShipperInfo(@Field("token") token: String): Single<Shipper>

    @FormUrlEncoded
    @POST("api/v1/shipper/info/change.php")
    fun updateShipperInfo(@Field("shipper_id") shipperId: Long, @Field("phone") phone: String): Single<MessageResponse>

    @GET("api/v1/shipper/checkedbill.php")
    fun getCheckedBills(@Query("token") token: String, @Query("page") page: Int): Single<BillExpressResponse>

    @POST("api/v1/shipper/takebill.php")
    fun takeBill(@Body takeBillBody: TakeBillBody): Single<MessageResponse>

    @GET("api/v1/shipper/takedbills.php")
    fun getTookBills(@Query("token") token: String, @Query("page") page: Int, @Query("status") status: Int): Single<BillExpressResponse>

    @POST("api/v1/shipper/completebill.php")
    fun completeBill(@Body completeBillBody: CompleteBillBody): Single<MessageResponse>

    @POST("api/v1/shipper/location.php")
    fun submitCurrentLocation(@Body currentLocationBody: CurrentLocationBody): CustomCall<MessageResponse>

    @GET("api/v1/store/order/livelocation.php")
    fun getBillLastLocation(@Query("token") token: String, @Query("id") id: Long): Single<BillLocation>

    @POST("api/v1/shipper/verifycash.php")
    fun verifyCash(@Body verifyCashBody: VerifyCashBody): Single<MessageResponse>

    @GET("api/v1/shipper/password/request.php")
    fun requestResetShipperPassword(@Query("email") email: String): Single<RequestResetPassResponse>

    @FormUrlEncoded
    @POST("api/v1/shipper/password/reset.php")
    fun resetShipperPassword(@Field("shipper_id") shipperId: Long, @Field("pass") pass: String, @Field("otp_code") code: Int): Single<AccessToken>

    // Staff
    @FormUrlEncoded
    @POST("api/v1/staff/login.php")
    fun staffLogin(@Field("user") user: String, @Field("pass") pass: String): Single<AccessToken>

    @FormUrlEncoded
    @POST("api/v1/staff/info.php")
    fun staffInfo(@Field("token") token: String): Single<Staff>

    @FormUrlEncoded
    @POST("api/v1/staff/bill/bills.php")
    fun getBillExpressStaff(@Field("token") token: String, @Field("status") status: Int, @Field("id") id: String, @Field("page") page: Int): CustomCall<BillExpressResponse>

    @FormUrlEncoded
    @POST("api/v1/staff/bill/checkbill.php")
    fun checkBill(@Field("token") token: String, @Field("status") status: Int, @Field("id") id: Long): Single<MessageResponse>

    @FormUrlEncoded
    @POST("api/v1/staff/shipper/shippers.php")
    fun getShippers(@Field("token") token: String, @Field("search") search: String, @Field("page") page: Int, @Field("status") status: Int): CustomCall<ExpressShipperResponse>

    @FormUrlEncoded
    @POST("api/v1/staff/shipper/shipper.php")
    fun getShipper(@Field("token") token: String, @Field("id") shipperId: Long): Single<Shipper>

    @FormUrlEncoded
    @POST("api/v1/staff/shipper/status.php")
    fun changeUserStatus(@Field("token") token: String, @Field("id") userId: Long, @Field("status") status: Int, @Field("is_shipper") isShipper: Boolean): Single<MessageResponse>
}
