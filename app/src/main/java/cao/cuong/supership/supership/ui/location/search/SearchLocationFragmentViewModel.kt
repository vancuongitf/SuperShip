package cao.cuong.supership.supership.ui.location.search

import cao.cuong.supership.supership.data.model.rxevent.UpdateConfirmAddressEvent
import cao.cuong.supership.supership.data.model.google.AutoComplete
import cao.cuong.supership.supership.data.source.GoogleMapRepository
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.network.CustomCallback
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.response.google.AutoCompleteResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Notification
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

class SearchLocationFragmentViewModel {

    internal val updateListObservable = PublishSubject.create<Notification<Boolean>>()
    internal val searchObservable = PublishSubject.create<String>()
    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    internal val locations = mutableListOf<AutoComplete>()
    private val googleMapRepository = GoogleMapRepository()
    private var currentCall: CustomCall<AutoCompleteResponse>? = null

    init {
        initSearchObservable()
    }

    internal fun getPlaceDetail(autoComplete: AutoComplete) = googleMapRepository.getPlaceDetail(autoComplete.placeId)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
                RxBus.publish(UpdateConfirmAddressEvent())
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }

    internal fun getAddressByLatLng(latLng: LatLng) = googleMapRepository.getAddress(latLng)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
                RxBus.publish(UpdateConfirmAddressEvent())
            }
            .doFinally {
                updateProgressDialogStatus.onNext(false)
            }

    internal fun getDirection(from: LatLng, to: LatLng) = googleMapRepository.getDirection(from, to)
            .observeOnUiThread()
            .doOnSubscribe {
                RxBus.publish(UpdateConfirmAddressEvent())
            }

    private fun initSearchObservable() {
        searchObservable
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .flatMap {
                    callSearchApi(it)
                }.subscribe({
                    updateListObservable.onNext(it)
                }, {
                    updateListObservable.onNext(Notification.createOnError(it))
                })
    }

    private fun callSearchApi(query: String): PublishSubject<Notification<Boolean>> {
        if (currentCall != null || query.isEmpty()) {
            currentCall?.cancel()
        }
        val result = PublishSubject.create<Notification<Boolean>>()
        currentCall = googleMapRepository.searchLocation(query)
        currentCall?.enqueue(object : CustomCallback<AutoCompleteResponse> {
            override fun success(call: Call<AutoCompleteResponse>, response: Response<AutoCompleteResponse>) {
                val data = response.body()
                if (data != null) {
                    locations.clear()
                    locations.addAll(data.predictions)
                    result.onNext(Notification.createOnNext(true))
                } else {
                    result.onNext(Notification.createOnError(Throwable()))
                }
            }

            override fun onError(t: Throwable) {
                result.onNext(Notification.createOnError(t))
            }
        })
        return result
    }
}
