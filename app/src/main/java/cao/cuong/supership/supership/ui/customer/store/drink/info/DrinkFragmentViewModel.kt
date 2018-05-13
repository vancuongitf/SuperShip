package cao.cuong.supership.supership.ui.customer.store.drink.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class DrinkFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun deleteDrink(id: Long) = storeRepository
            .deleteDrink(localRepository.getAccessToken(), id)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }
}
