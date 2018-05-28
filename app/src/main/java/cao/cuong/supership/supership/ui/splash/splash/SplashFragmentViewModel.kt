package cao.cuong.supership.supership.ui.splash.splash

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class SplashFragmentViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun getModule() = localRepository.getModule()

    internal fun isLogin() = localRepository.isLogin()
}
