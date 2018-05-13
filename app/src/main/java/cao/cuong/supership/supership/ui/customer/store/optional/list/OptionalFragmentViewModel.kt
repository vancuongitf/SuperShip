package cao.cuong.supership.supership.ui.customer.store.optional.list

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import io.reactivex.subjects.BehaviorSubject

class OptionalFragmentViewModel(context: Context) {

    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun deleteDrinkOption(id: Long) = storeRepository
            .deleteDrinkOption(localRepository.getAccessToken(), id)
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }
}
