package cao.cuong.supership.supership.data.source

import android.content.Context
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.data.source.datasource.LocalDataSource
import com.google.gson.Gson

/**
 *
 * @author at-cuongcao.
 */
class LocalRepository(private val context: Context) : LocalDataSource {

    companion object {
        internal const val SHARED_KEY_DISABLE_LOCATION_PERMISSION = "key_location_permission"
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
}
