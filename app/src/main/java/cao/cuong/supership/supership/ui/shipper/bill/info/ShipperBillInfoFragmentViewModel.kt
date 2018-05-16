package cao.cuong.supership.supership.ui.shipper.bill.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.request.TakeBillBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class ShipperBillInfoFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()

    internal fun getAccessToken() = localRepository.getAccessToken()

    internal fun getBillInfo(id: Long) = shipperRepository
            .getBillInfo(localRepository.getAccessToken(), id)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }

    internal fun takeBill(takeBillBody: TakeBillBody) = shipperRepository
            .takeBill(takeBillBody)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }
}
