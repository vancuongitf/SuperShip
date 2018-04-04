package cao.cuong.supership.supership.data.source.remote.network

import cao.cuong.supership.supership.data.model.UnAuthorizeException
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.*
import java.io.EOFException
import java.net.HttpURLConnection

/**
 *
 * @author at-hoavo.
 */
interface CustomCallback<T> {
    /** Called for [200] responses.  */
    fun success(call: Call<T>, response: Response<T>)

    /** Called for error responses.  */
    fun onError(t: Throwable)
}

/**
 * CustomCall
 */
interface CustomCall<T> {
    /**
     * Cancel call
     */
    fun cancel()

    /**
     * Enqueue call
     */
    fun enqueue(callback: CustomCallback<T>)

    /**
     * Execute call
     */
    fun execute(): Response<T>

    /**
     * Clone
     */
    fun clone(): CustomCall<T>

    /**
     * Request call
     */
    fun request(): Request

    /**
     * Check Call is canceled
     */
    fun isCanceled(): Boolean

    /**
     * Check Call is executed
     */
    fun isExecuted(): Boolean
}

internal class CustomCallAdapter<T>(private val call: Call<T>, private val retrofit: Retrofit) : CustomCall<T> {
    override fun execute() = call.execute()

    override fun clone() = this

    override fun request() = Request.Builder().build()

    override fun isCanceled() = call.isCanceled

    override fun isExecuted() = call.isExecuted

    override fun cancel() {
        call.cancel()
    }

    override fun enqueue(callback: CustomCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.errorBody()
                val code = response.code()
                try {
                    when (code) {
                        HttpURLConnection.HTTP_OK -> callback.success(call, response)

                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            RxBus.publish(UnAuthorizeException())
                            callback.onError(ApiException(HttpURLConnection.HTTP_UNAUTHORIZED, "Internal server error."))
                        }

                        ApiException.CUSTOM_HTTP_CODE -> {
                            val converter: Converter<ResponseBody, ApiException> = retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())
                            callback.onError(responseAfterConvert)
                        }

                        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                            callback.onError(ApiException(ApiException.CUSTOM_HTTP_CODE, "Internal server error."))
                        }

                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            callback.onError(ApiException(ApiException.CUSTOM_HTTP_CODE, "Bad request error."))
                        }

                    //Todo: Handle another status code
                        else -> callback.onError(Throwable("Error unknow"))
                    }
                } catch (e: EOFException) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}
