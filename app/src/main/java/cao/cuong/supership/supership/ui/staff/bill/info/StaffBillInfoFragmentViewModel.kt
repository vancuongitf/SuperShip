package cao.cuong.supership.supership.ui.staff.bill.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class StaffBillInfoFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val staffRepository = StaffRepository()

    internal fun getBillInfo(id: Long) = staffRepository
            .getBillInfo(localRepository.getAccessToken(), id)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }

    internal fun checkBill(billId: Long, status: Int) = staffRepository
            .checkBill(localRepository.getAccessToken(), status, billId)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }
}
