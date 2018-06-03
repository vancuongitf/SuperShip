package cao.cuong.supership.supership.data.source

import android.content.Context
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.datasource.LocalDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.json.JSONArray
import kotlin.concurrent.thread

/**
 *
 * @author at-cuongcao.
 */
class LocalRepository(private val context: Context) : LocalDataSource {

    companion object {
        internal const val SHARED_KEY_DISABLE_LOCATION_PERMISSION = "key_location_permission"
        internal const val SHARED_KEY_SEARCH_HISTORY = "key_search_history"
        internal const val SHARED_KEY_ACCESS_TOKEN = "key_access_token"
        internal const val SHARED_KEY_USER_INFO = "user_info"
        internal const val SHARED_KEY_MODULE = "key_module"
        internal const val SHARED_KEY_SHIPPER_INFO = "key_shipper_info"
        internal const val SHARED_KEY_STAFF_INFO = "key_staff_info"
    }

    private val pref by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    override fun <T> saveOption(key: String, value: T) {
        val editor = pref.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }

            is Boolean -> {
                editor.putBoolean(key, value)
            }

            is String -> {
                editor.putString(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            else -> {
                editor.putString(key, Gson().toJson(value).toString())
            }
        }
        editor.apply()
    }

    override fun isDisableLocationPermission() = pref.getBoolean(SHARED_KEY_DISABLE_LOCATION_PERMISSION, false)

    override fun saveSearchHistory(storeInfoExpress: StoreInfoExpress) {
        val histories = getHistoryList()
        histories.remove(histories.find {
            it.storeId == storeInfoExpress.storeId
        })
        histories.add(0, storeInfoExpress)
        if (histories.size > 20) {
            histories.removeAt(20)
        }
        pref.edit().putString(SHARED_KEY_SEARCH_HISTORY, Gson().toJson(histories).toString()).apply()
    }

    override fun getSearchHistory(): Single<StoreExpressResponse> {
        val result = SingleSubject.create<StoreExpressResponse>()
        thread {
            result.onSuccess(StoreExpressResponse(false, getHistoryList()))
        }
        return result
    }

    override fun saveAccessToken(token: String) {
        ApiClient.getInstance(null).token = token
        pref.edit().putString(SHARED_KEY_ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken(): String {
        val token = pref.getString(SHARED_KEY_ACCESS_TOKEN, "") ?: ""
        ApiClient.getInstance(null).token = token
        return token
    }

    override fun isLogin(): Boolean {
        ApiClient.getInstance(null).token = getAccessToken()
        return getAccessToken().isNotEmpty()
    }

    override fun clearAccessToken() {
        pref.edit().putString(SHARED_KEY_ACCESS_TOKEN, "").apply()
        pref.edit().putString(SHARED_KEY_USER_INFO, "").apply()
        pref.edit().putString(SHARED_KEY_SHIPPER_INFO, "").apply()
    }

    override fun saveUserInfo(userInfo: UserInfo) {
        pref.edit().putString(SHARED_KEY_USER_INFO, Gson().toJson(userInfo).toString()).apply()
    }

    override fun getUserInfo(): UserInfo? {
        if (getAccessToken().isEmpty()) {
            return null
        }
        val userStr = pref.getString(SHARED_KEY_USER_INFO, "")
        return try {
            Gson().fromJson<UserInfo>(userStr, UserInfo::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    override fun chooseModule(module: Int) {
        pref.edit().putInt(SHARED_KEY_MODULE, module).apply()
    }

    override fun getModule() = pref.getInt(SHARED_KEY_MODULE, -1)

    override fun saveShipperInfo(shipper: Shipper) {
        pref.edit().putString(SHARED_KEY_SHIPPER_INFO, Gson().toJson(shipper).toString()).apply()
    }

    override fun getShipperInfo(): Shipper? {
        val shipperString = pref.getString(SHARED_KEY_SHIPPER_INFO, "")
        return try {
            Gson().fromJson<Shipper>(shipperString, Shipper::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun saveStaffInfo(staff: Staff) {
        pref.edit().putString(SHARED_KEY_STAFF_INFO, Gson().toJson(staff).toString()).apply()
    }

    override fun getStaffInfo(): Staff? {
        val staffString = pref.getString(SHARED_KEY_STAFF_INFO, "")
        return try {
            Gson().fromJson<Staff>(staffString, Staff::class.java)
        } catch (e: Exception) {
            null
        }
    }

    private fun getHistoryList(): MutableList<StoreInfoExpress> {
        val result = mutableListOf<StoreInfoExpress>()
        try {
            val gson = Gson()
            val jsonArray = JSONArray(pref.getString(SHARED_KEY_SEARCH_HISTORY, "[]"))
            (0 until jsonArray.length()).mapTo(result) {
                gson.fromJson(jsonArray.getJSONObject(it).toString(), StoreInfoExpress::class.java)
            }
        } catch (e: Exception) {

        }
        return result
    }
}
