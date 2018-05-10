package cao.cuong.supership.supership.ui.store

import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.model.Store
import cao.cuong.supership.supership.ui.base.BaseActivity

open class BaseStoreInfoActivity : BaseActivity() {
    internal var shouldReload = false
    internal lateinit var store: Store
    override fun onBindViewModel() {

    }

    internal fun updateStore(store: Store) {
        this.store = store
    }

    internal fun getDrinkOptions(): MutableList<DrinkOption> {
        val result = mutableListOf<DrinkOption>()
        store.options.forEach {
            result.add(it.copy())
        }
        return result
    }

    internal fun getDrinkOptions(ids: MutableSet<Long>): MutableList<DrinkOption> {
        val result = mutableListOf<DrinkOption>()
        store.options.forEach {
            if (ids.contains(it.id)) {
                result.add(it.copy())
            }
        }
        return result
    }
}
