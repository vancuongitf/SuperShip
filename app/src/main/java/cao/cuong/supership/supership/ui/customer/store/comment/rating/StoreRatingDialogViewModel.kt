package cao.cuong.supership.supership.ui.customer.store.comment.rating

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class StoreRatingDialogViewModel(context: Context) {

    internal val updateProgressDialogObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun isLogin() = localRepository.isLogin()

    internal fun storeRating(storeId: Long, rate: Int) = storeRepository
            .storeRating(localRepository.getUserInfo()?.id ?: -1, storeId, rate)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogObservable.onNext(true)
            }
            .doFinally {
                updateProgressDialogObservable.onNext(false)
            }
}
