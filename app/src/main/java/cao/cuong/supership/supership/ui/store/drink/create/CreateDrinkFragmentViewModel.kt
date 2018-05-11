package cao.cuong.supership.supership.ui.store.drink.create

import android.content.Context
import android.net.Uri
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkBody
import cao.cuong.supership.supership.data.source.remote.request.EditDrinkBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Single
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

    internal fun editDrink(uri: Uri?, editDrinkBody: EditDrinkBody): Single<MessageResponse> {
        editDrinkBody.token = localRepository.getAccessToken()
        return if (uri != null) {
            storeRepository.uploadImage(File(uri.path))
                    .observeOnUiThread()
                    .flatMap {
                        editDrinkBody.image = it.message
                        storeRepository.editDrink(editDrinkBody)
                                .observeOnUiThread()
                    }
                    .doOnSubscribe {
                        progressDialogStatusObservable.onNext(true)
                    }
                    .doFinally {
                        progressDialogStatusObservable.onNext(false)
                    }
        } else {
            storeRepository.editDrink(editDrinkBody)
                    .observeOnUiThread()
                    .doOnSubscribe {
                        progressDialogStatusObservable.onNext(true)
                    }
                    .doFinally {
                        progressDialogStatusObservable.onNext(false)
                    }
        }
    }
}
