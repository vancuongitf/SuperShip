package cao.cuong.supership.supership.data.source.remote.network

import android.util.Log
import cao.cuong.supership.supership.data.model.RxEvent.UnAuthorizeException
import okhttp3.ResponseBody
import retrofit2.*
import cao.cuong.supership.supership.data.source.remote.util.BaseRxCallAdapterWrapper
import java.lang.reflect.Type
import javax.net.ssl.HttpsURLConnection

/**
 *
 * @author at-hoavo.
 */
class RxCallAdapterWrapper<R>(type: Type, retrofit: Retrofit, wrapped: CallAdapter<R, *>?) : BaseRxCallAdapterWrapper<R>(type, retrofit, wrapped) {

    override fun convertRetrofitExceptionToCustomException(throwable: Throwable, retrofit: Retrofit): Throwable {

        if (throwable is HttpException) {
            val converter: Converter<ResponseBody, ApiException> = retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
            val response: Response<*>? = throwable.response()
            val result = response?.errorBody()?.let {
                converter.convert(it)
            }
            when (result?.code) {
                ApiException.CUSTOM_HTTP_CODE -> {
                    return result
                }

                HttpsURLConnection.HTTP_UNAUTHORIZED -> response.errorBody()?.let {
                    Log.i("tag11xxzy", "401")
                    RxBus.publish(UnAuthorizeException())
                    return result
                }
            }
        }
        return throwable
    }

    override fun createExceptionForSuccessResponse(response: Any?): Throwable? = super.createExceptionForSuccessResponse(response)
}
