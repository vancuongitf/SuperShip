package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.StoreDataSource
import cao.cuong.supership.supership.data.source.remote.StoreRemoteDataSource
import cao.cuong.supership.supership.data.source.remote.request.CreateStoreBody
import java.io.File

/**
 *
 * @author at-cuongcao.
 */
class StoreRepository : StoreDataSource {

    private val storeRemoteDataSource = StoreRemoteDataSource()

    override fun getStoreInfo(storeId: Long) = storeRemoteDataSource.getStoreInfo(storeId)

    override fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double?, lng: Double?) = storeRemoteDataSource.getStoreExpressList(advanceParam, page, lat, lng)

    override fun searchStore(query: String, page: Int) = storeRemoteDataSource.searchStore(query, page)

    override fun uploadImage(file: File) = storeRemoteDataSource.uploadImage(file)

    override fun createStore(createStoreBody: CreateStoreBody) = storeRemoteDataSource.createStore(createStoreBody)
}
