package cao.cuong.supership.supership.data.source

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.data.source.datasource.LocalDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

    override fun clearAccessToken() {
        Log.i("tag11","clear")
        pref.edit().putString(SHARED_KEY_ACCESS_TOKEN, "").apply()
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
