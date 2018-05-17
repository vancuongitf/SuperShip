package cao.cuong.supership.supership.ui.shipper.cash

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.request.VerifyCashBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class CashActivityViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()

    internal fun verifyCash(verifyCashBody: VerifyCashBody) = shipperRepository
            .verifyCash(verifyCashBody)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }
}
