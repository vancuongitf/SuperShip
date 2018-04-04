package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.Store
import io.reactivex.Single
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse

/**
 *
 * @author at-cuongcao.
 */
interface StoreDataSource {

    fun getStoreInfo(storeId: Long): Single<Store>

    fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double? = null, lng: Double? = null): Single<StoreExpressResponse>

    fun searchStore(query: String, page: Int): Single<StoreExpressResponse>
}
