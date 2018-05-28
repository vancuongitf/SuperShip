package cao.cuong.supership.supership.ui.shipper.info

import android.content.Context
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus

class ShipperInfoFragmentViewModel(context: Context) {

    private val localRepository = LocalRepository(context)
    private val shipperRepository = ShipperRepository()

    internal fun isLogin() = localRepository.isLogin()

    internal fun getShipperInfo() = shipperRepository.getShipperInfo(localRepository.getAccessToken())

    internal fun saveShipperInfo(shipperInfo: Shipper) = localRepository.saveShipperInfo(shipperInfo)

    internal fun getLocalShipperInfo() = localRepository.getShipperInfo()

    internal fun logOut() {
        localRepository.clearAccessToken()
    }
}
