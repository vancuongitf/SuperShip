package cao.cuong.supership.supership.data.source.remote.util

import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import retrofit2.Call
import retrofit2.Response

/**
 *
 * @author at-hoavo.
 */
class CallBackApi private constructor() {
    companion object {
        internal fun <T> callback(
                onSuccess: (Call<T>, Response<T>) -> Unit = { _, _ -> },
                onError: (Throwable) -> Unit = { }
        ): CustomCallback<T> = object : CustomCallback<T> {
            override fun success(call: Call<T>, response: Response<T>) {
                onSuccess.invoke(call, response)
            }

            override fun onError(t: Throwable) {
                onError.invoke(t)
            }
        }
    }
}
