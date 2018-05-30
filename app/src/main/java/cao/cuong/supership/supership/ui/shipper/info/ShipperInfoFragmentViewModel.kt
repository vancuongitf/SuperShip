package cao.cuong.supership.supership.ui.shipper.info

import android.content.Context
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class ShipperInfoFragmentViewModel(context: Context) {

    internal val updateProgressDialogStatus = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()

    internal fun isLogin() = localRepository.isLogin()

    internal fun getShipperInfo() = shipperRepository.getShipperInfo(localRepository.getAccessToken())

    internal fun updateShipperInfo(phone: String) = shipperRepository
            .updateShipperInfo(localRepository.getShipperInfo()?.id ?: -1L, phone)
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressDialogStatus.onNext(true)
            }.doFinally {
                updateProgressDialogStatus.onNext(false)
            }

    internal fun saveShipperInfo(shipperInfo: Shipper) = localRepository.saveShipperInfo(shipperInfo)

    internal fun getLocalShipperInfo() = localRepository.getShipperInfo()

    internal fun logOut() {
        localRepository.clearAccessToken()
    }
}
