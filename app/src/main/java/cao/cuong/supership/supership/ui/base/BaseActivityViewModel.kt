package cao.cuong.supership.supership.ui.base

import android.content.Context
import android.util.Log
import cao.cuong.supership.supership.data.model.RxEvent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.remote.network.RxBus

class BaseActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun clearAccessToken() {
        Log.i("tag11","xxx")
        localRepository.clearAccessToken()
        RxBus.publish(UpdateAccountUI())
    }
}
