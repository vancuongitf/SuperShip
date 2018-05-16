package cao.cuong.supership.supership.ui.shipper.account

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class ShipperActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()
}
