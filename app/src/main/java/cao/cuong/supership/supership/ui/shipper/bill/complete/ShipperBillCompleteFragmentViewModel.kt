package cao.cuong.supership.supership.ui.shipper.bill.complete

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.request.CompleteBillBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class ShipperBillCompleteFragmentViewModel(context: Context) {

    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()

    internal fun getAccessToken() = localRepository.getAccessToken()

    internal fun completeBill(completeBillBody: CompleteBillBody) = shipperRepository
            .completeBill(completeBillBody)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }
}
