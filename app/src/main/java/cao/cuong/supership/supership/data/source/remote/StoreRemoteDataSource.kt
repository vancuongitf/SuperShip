package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.StoreDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.request.CreateStoreBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
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

    override fun getStoreInfo(storeId: Long) = apiService.getStoreInfo(storeId)

    override fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double?, lng: Double?) = apiService.getExpressStore(advanceParam, page, lat, lng)

    override fun searchStore(query: String, page: Int) = apiService.search(query.unAccent(), page)

    override fun uploadImage(file: File): Single<MessageResponse> {
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val requestFileBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
        return apiService.uploadImage(requestFileBody)
    }

    override fun createStore(createStoreBody: CreateStoreBody) = apiService.createStore(createStoreBody)
}
