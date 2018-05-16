package cao.cuong.supership.supership.ui.splash.module

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class ChooseModuleFragmentViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun chooseModule(module: Int) = localRepository.chooseModule(module)
}
