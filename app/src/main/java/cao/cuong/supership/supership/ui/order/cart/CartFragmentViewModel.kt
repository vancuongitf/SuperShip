package cao.cuong.supership.supership.ui.order.cart

import android.content.Context
import android.util.Log
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.remote.request.BillBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

class CartFragmentViewModel(context: Context) {
    private val localRepository = LocalRepository(context)

    internal fun submitOrder(billBody: BillBody): Single<MessageResponse> {
        billBody.userToken = localRepository.getAccessToken()
        Log.i("tag11", Gson().toJson(billBody).toString())
        return SingleSubject.create()
    }

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()
}
