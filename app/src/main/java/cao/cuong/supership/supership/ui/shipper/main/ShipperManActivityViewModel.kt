package cao.cuong.supership.supership.ui.shipper.main

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class ShipperManActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun isLogin() = localRepository.isLogin()
}
