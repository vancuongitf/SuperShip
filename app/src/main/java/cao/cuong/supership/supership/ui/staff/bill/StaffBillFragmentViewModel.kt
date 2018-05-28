package cao.cuong.supership.supership.ui.staff.bill

import android.content.Context
import cao.cuong.supership.supership.data.model.ExpressBill
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.response.BillExpressResponse
import io.reactivex.Notification
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Response

class StaffBillFragmentViewModel(context: Context) {

    internal val updateListObservable = BehaviorSubject.create<Notification<Boolean>>()
    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    internal var currentPage = 1
    internal var currentStatus = 0
    internal val bills = mutableListOf<ExpressBill>()
    private var nextPageFlag = false
    private val localRepository = LocalRepository(context)
    private val staffRepository = StaffRepository()
    private var currentCall: CustomCall<BillExpressResponse>? = null

    internal fun isLogin() = localRepository.isLogin()

    internal fun getBills(id: String) {
        if (currentCall != null) {
            currentCall?.cancel()
            currentCall = null
        }
        if (currentPage == 1) {
            bills.clear()
            updateListObservable.onNext(Notification.createOnNext(true))
        }
        updateProgressDialogStatus.onNext(true)
        currentCall = staffRepository.getBills(localRepository.getAccessToken(), currentStatus, id, currentPage)
        currentCall?.enqueue(object : CustomCallback<BillExpressResponse> {
            override fun success(call: Call<BillExpressResponse>, response: Response<BillExpressResponse>) {
                response.body()?.let {
                    nextPageFlag = it.nextPageFlag
                }
                response.body()?.orders?.let {
                    bills.addAll(it)
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
