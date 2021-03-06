package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.StoreDataSource
import cao.cuong.supership.supership.data.source.remote.StoreRemoteDataSource
import cao.cuong.supership.supership.data.source.remote.request.*
import java.io.File

/**
 *
 * @author at-cuongcao.
 */
class StoreRepository : StoreDataSource {

    private val storeRemoteDataSource = StoreRemoteDataSource()

    override fun createStore(createStoreBody: CreateStoreBody) = storeRemoteDataSource.createStore(createStoreBody)

    override fun editStoreInfo(editStoreBody: EditStoreBody) = storeRemoteDataSource.editStoreInfo(editStoreBody)

    override fun changeStoreStatus(updateStoreStatusBody: UpdateStoreStatusBody) = storeRemoteDataSource.changeStoreStatus(updateStoreStatusBody)

    override fun getStoreInfo(storeId: Long) = storeRemoteDataSource.getStoreInfo(storeId)

    override fun storeRating(userId: Long, storeId: Long, rate: Int) = storeRemoteDataSource.storeRating(userId, storeId, rate)

    override fun getStoreComments(userId: Long, storeId: Long, page: Int) = storeRemoteDataSource.getStoreComments(userId, storeId, page)

    override fun storeComment(userId: Long, storeId: Long, comment: String) = storeRemoteDataSource.storeComment(userId, storeId, comment)

    override fun getStoreExpressList(advanceParam: Int, page: Int, lat: Double?, lng: Double?) = storeRemoteDataSource.getStoreExpressList(advanceParam, page, lat, lng)

    override fun searchStore(query: String, page: Int) = storeRemoteDataSource.searchStore(query, page)

    override fun uploadImage(file: File) = storeRemoteDataSource.uploadImage(file)

    override fun createDrink(drinkBody: CreateDrinkBody) = storeRemoteDataSource.createDrink(drinkBody)

    override fun editDrink(drinkBody: EditDrinkBody) = storeRemoteDataSource.editDrink(drinkBody)

    override fun deleteDrink(token: String, id: Long) = storeRemoteDataSource.deleteDrink(token, id)

    override fun createDrinkOption(createOptionBody: CreateDrinkOptionBody) = storeRemoteDataSource.createDrinkOption(createOptionBody)

    override fun editDrinkOption(editDrinkOptionBody: EditDrinkOptionBody) = storeRemoteDataSource.editDrinkOption(editDrinkOptionBody)

    override fun deleteDrinkOption(token: String, id: Long) = storeRemoteDataSource.deleteDrinkOption(token, id)

    override fun addDrinkItemOption(body: AddDrinkOptionItemBody) = storeRemoteDataSource.addDrinkItemOption(body)

    override fun orderDrink(billBody: BillBody) = storeRemoteDataSource.orderDrink(billBody)

    override fun getLastedBillLocation(token: String, id: Long) = storeRemoteDataSource.getLastedBillLocation(token, id)
}
