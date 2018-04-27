package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.StoreDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.extension.unAccent

/**
 *
 * @author at-cuongcao.
 */
class StoreRemoteDataSource : StoreDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun getStoreInfo(storeId: Long) = apiService.getStoreInfo(storeId)

    override fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double?, lng: Double?) = apiService.getExpressStore(advanceParam, page, lat, lng)

    override fun searchStore(query: String, page: Int) = apiService.search(query.unAccent(), page)

}
