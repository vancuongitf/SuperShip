package cao.cuong.supership.supership.ui.user

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class UserActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun isLogin() = localRepository.getAccessToken().isNotEmpty()
}
