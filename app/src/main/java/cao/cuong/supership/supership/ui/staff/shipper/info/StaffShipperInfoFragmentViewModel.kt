package cao.cuong.supership.supership.ui.staff.shipper.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class StaffShipperInfoFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val staffRepository = StaffRepository()

    internal fun getShipperInfo(shipperId: Long) =
            staffRepository.getShipperInfo(localRepository.getAccessToken(), shipperId)
                    .observeOnUiThread()
                    .doOnSubscribe {
                        updateProgressStatusObservable.onNext(true)
                    }
                    .doFinally {
                        updateProgressStatusObservable.onNext(false)
                    }
}
