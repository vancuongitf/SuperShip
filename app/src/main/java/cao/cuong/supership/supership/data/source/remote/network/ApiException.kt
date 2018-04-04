package cao.cuong.supership.supership.data.source.remote.network

import com.google.gson.annotations.SerializedName

/**
 * Use this file to handle error from api
 */
data class ApiException(
        @SerializedName("code") val code: Int,
        @SerializedName("message") val messageError: String) : Throwable(messageError) {
    companion object {
        internal const val CUSTOM_HTTP_CODE = 678
    }
}
