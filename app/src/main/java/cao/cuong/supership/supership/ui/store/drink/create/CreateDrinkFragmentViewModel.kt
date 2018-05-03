package cao.cuong.supership.supership.ui.store.drink.create

import android.content.Context
import android.net.Uri
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkBody
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject
import java.io.File

class CreateDrinkFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()

    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun createDrink(uri: Uri, drinkBody: CreateDrinkBody) = storeRepository
            .uploadImage(File(uri.path))
            .observeOnUiThread()
            .flatMap {
                drinkBody.image = it.message
                drinkBody.token = localRepository.getAccessToken()
                storeRepository.createDrink(drinkBody)
                        .observeOnUiThread()
            }
            .doOnSubscribe {
                progressDialogStatusObservable.onNext(true)
            }
            .doFinally {
                progressDialogStatusObservable.onNext(false)
            }
}
