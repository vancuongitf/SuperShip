package cao.cuong.supership.supership.ui.store

import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.ui.base.BaseActivity

open class BaseStoreInfoActivity : BaseActivity() {
    private val options = mutableListOf<DrinkOption>()
    internal var shouldReload = false
    override fun onBindViewModel() {

    }

    internal fun getDrinkOptions() = options

    internal fun setDrinkOption(drinkOptions: MutableList<DrinkOption>) {
        options.clear()
        options.addAll(drinkOptions)
    }

    internal fun getDrinkOptions(ids: MutableSet<Long>): MutableList<DrinkOption> {
        val result = mutableListOf<DrinkOption>()
        options.forEach {
            if (ids.contains(it.id)) {
                result.add(it.copy())
            }
        }
        return result
    }
}
