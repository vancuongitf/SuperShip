package cao.cuong.supership.supership.ui.store.optional.add

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.data.source.remote.request.AddDrinkOptionItemBody
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkOptionBody
import cao.cuong.supership.supership.data.source.remote.request.EditDrinkOptionBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class AddDrinkOptionFragmentViewModel(context: Context) {

    internal val progressDialogStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()

    internal fun createDrinkOption(createDrinkOptionBody: CreateDrinkOptionBody, addDrinkOptionItemBody: AddDrinkOptionItemBody): Single<MessageResponse> {
        createDrinkOptionBody.token = localRepository.getAccessToken()
        addDrinkOptionItemBody.token = localRepository.getAccessToken()
        return storeRepository.createDrinkOption(createDrinkOptionBody)
                .observeOnUiThread()
                .flatMap {
                    val optionId = it.id
                    addDrinkOptionItemBody.items.forEach {
                        it.optionId = optionId
                    }
                    storeRepository.addDrinkItemOption(addDrinkOptionItemBody)
                            .observeOnUiThread()
                }
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }
    }

    internal fun editDrinkOption(editDrinkOptionBody: EditDrinkOptionBody): Single<MessageResponse> {
        editDrinkOptionBody.token = localRepository.getAccessToken()
        return storeRepository
                .editDrinkOption(editDrinkOptionBody)
                .observeOnUiThread()
                .doOnSubscribe {
                    progressDialogStatusObservable.onNext(true)
                }
                .doFinally {
                    progressDialogStatusObservable.onNext(false)
                }
    }
}
