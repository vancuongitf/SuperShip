package cao.cuong.supership.supership.ui.store.drink.info

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository

class DrinkFragmentViewModel(context: Context) {
    private val localRepository = LocalRepository(context)
    private val storeRepository = StoreRepository()
}
