package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single

/**
 *
 * @author at-cuongcao.
 */
interface LocalDataSource {

    fun <T> saveOption(key: String, value: T)

    fun isDisableLocationPermission(): Boolean

    fun saveSearchHistory(storeInfoExpress: StoreInfoExpress)

    fun getSearchHistory(): Single<StoreExpressResponse>

    fun saveAccessToken(token: String)

    fun getAccessToken(): String

    fun clearAccessToken()

    fun saveUserInfo(userInfo: UserInfo)

    fun getUserInfo(): UserInfo?

    fun chooseModule(module: Int)

    fun getModule(): Int

    fun saveShipperInfo(shipper: Shipper)

    fun getShipperInfo(): Shipper?
}
