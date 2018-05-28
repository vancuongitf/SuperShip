package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.StoreDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.request.*
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.StoreCommentResponse
import cao.cuong.supership.supership.extension.unAccent
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 *
 * @author at-cuongcao.
 */
class StoreRemoteDataSource : StoreDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun createStore(createStoreBody: CreateStoreBody) = apiService.createStore(createStoreBody)

    override fun editStoreInfo(editStoreBody: EditStoreBody) = apiService.editStoreInfo(editStoreBody)

    override fun changeStoreStatus(updateStoreStatusBody: UpdateStoreStatusBody) = apiService.updateStoreStatus(updateStoreStatusBody)

    override fun getStoreInfo(storeId: Long) = apiService.getStoreInfo(storeId)

    override fun storeRating(userId: Long, storeId: Long, rate: Int) = apiService.storeRating(userId, storeId, rate)

    override fun getStoreComments(userId: Long, storeId: Long, page: Int) = apiService.getStoreComments(userId, storeId, page)

    override fun storeComment(userId: Long, storeId: Long, comment: String) = apiService.storeComment(userId, storeId, comment)

    override fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double?, lng: Double?) = apiService.getExpressStore(advanceParam, page, lat, lng)

    override fun searchStore(query: String, page: Int) = apiService.search(query.unAccent(), page)

    override fun uploadImage(file: File): Single<MessageResponse> {
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val requestFileBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
        return apiService.uploadImage(requestFileBody)
    }

    override fun createDrink(drinkBody: CreateDrinkBody) = apiService.createDrink(drinkBody)

    override fun editDrink(drinkBody: EditDrinkBody) = apiService.editDrink(drinkBody)

    override fun deleteDrink(token: String, id: Long) = apiService.deleteDrink(token, id)

    override fun createDrinkOption(createOptionBody: CreateDrinkOptionBody) = apiService.createDrinkOption(createOptionBody)

    override fun editDrinkOption(editDrinkOptionBody: EditDrinkOptionBody) = apiService.editDrinkOption(editDrinkOptionBody)

    override fun deleteDrinkOption(token: String, id: Long) = apiService.deleteDrinkOption(token, id)

    override fun addDrinkItemOption(body: AddDrinkOptionItemBody) = apiService.addDrinkItemOption(body)

    override fun orderDrink(billBody: BillBody) = apiService.orderDrink(billBody)

    override fun getLastedBillLocation(token: String, id: Long) = apiService.getBillLastLocation(token, id)
}
