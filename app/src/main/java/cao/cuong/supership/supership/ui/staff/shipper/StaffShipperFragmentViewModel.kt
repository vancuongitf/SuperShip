package cao.cuong.supership.supership.ui.staff.shipper

import android.content.Context
import cao.cuong.supership.supership.R.string.bills
import cao.cuong.supership.supership.data.model.ShipperExpress
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.response.ExpressShipperResponse
import io.reactivex.Notification
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Response

class StaffShipperFragmentViewModel(context: Context) {

    internal val updateListObservable = BehaviorSubject.create<Notification<Boolean>>()
    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    internal var currentPage = 1
    internal var currentStatus = 0
    internal val shippers = mutableListOf<ShipperExpress>()
    private var nextPageFlag = false
    private val localRepository = LocalRepository(context)
    private val staffRepository = StaffRepository()
    private var currentCall: CustomCall<ExpressShipperResponse>? = null

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()

    internal fun getShipper(search: String) {
        if (currentCall != null) {
            currentCall?.cancel()
            currentCall = null
        }
        if (currentPage == 1) {
            shippers.clear()
            updateListObservable.onNext(Notification.createOnNext(true))
        }
        updateProgressDialogStatus.onNext(true)
        currentCall = staffRepository.getShippers(localRepository.getAccessToken(), search, currentPage, currentStatus)
        currentCall?.enqueue(object : CustomCallback<ExpressShipperResponse> {
            override fun success(call: Call<ExpressShipperResponse>, response: Response<ExpressShipperResponse>) {
                response.body()?.let {
                    nextPageFlag = it.nextPageFlag
                }
                response.body()?.shippers?.let {
                    shippers.addAll(it)
                    updateListObservable.onNext(Notification.createOnNext(true))
                }
                updateProgressDialogStatus.onNext(false)
            }

            override fun onError(t: Throwable) {
                if (currentPage > 1) {
                    currentPage--
                }
                updateProgressDialogStatus.onNext(false)
                updateListObservable.onNext(Notification.createOnError(t))
            }
        })
    }
}
