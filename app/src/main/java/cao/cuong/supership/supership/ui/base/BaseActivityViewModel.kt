package cao.cuong.supership.supership.ui.base

import android.content.Context
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus

class BaseActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun clearAccessToken() {
        localRepository.clearAccessToken()
        RxBus.publish(UpdateAccountUI())
    }
}
