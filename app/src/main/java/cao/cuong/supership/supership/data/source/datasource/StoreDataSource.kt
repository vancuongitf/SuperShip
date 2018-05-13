package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.request.*
import cao.cuong.supership.supership.data.source.remote.response.CreateDrinkOptionResponse
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single
import java.io.File

/**
 *
 * @author at-cuongcao.
 */
interface StoreDataSource {

    fun createStore(createStoreBody: CreateStoreBody): Single<MessageResponse>

    fun editStoreInfo(editStoreBody: EditStoreBody): Single<MessageResponse>

    fun changeStoreStatus(updateStoreStatusBody: UpdateStoreStatusBody): Single<MessageResponse>

    fun getStoreInfo(storeId: Long): Single<Store>

    fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double? = null, lng: Double? = null): Single<StoreExpressResponse>

    fun searchStore(query: String, page: Int): CustomCall<StoreExpressResponse>

    fun uploadImage(file: File): Single<MessageResponse>

    fun createDrink(drinkBody: CreateDrinkBody): Single<MessageResponse>

    fun editDrink(drinkBody: EditDrinkBody): Single<MessageResponse>

    fun deleteDrink(token: String, id: Long): Single<MessageResponse>

    fun createDrinkOption(createOptionBody: CreateDrinkOptionBody): Single<CreateDrinkOptionResponse>

    fun editDrinkOption(editDrinkOptionBody: EditDrinkOptionBody): Single<MessageResponse>

    fun deleteDrinkOption(token: String, id: Long): Single<MessageResponse>

    fun addDrinkItemOption(body: AddDrinkOptionItemBody): Single<MessageResponse>

    fun orderDrink(billBody: BillBody): Single<MessageResponse>
}
