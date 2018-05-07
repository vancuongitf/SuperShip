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
//
//    internal fun setDrinkOption(drinkOptions: MutableList<DrinkOption>) {
//        options.clear()
//        options.addAll(drinkOptions)
//    }
//

//
//    internal fun setDrinks(newDrinks: MutableList<Drink>) {
//        drinks.clear()
//        drinks.addAll(newDrinks)
//    }
//
////    internal fun getOrderedDrink(orderedDrink: OrderDrink): Drink? {
////        val orderedDrink = drinks.filter {
////            it.id == orderedDrink.drinkId
////        }
////        orderedDrink.forEach {
////
////        }
////    }
////
////    internal fun getOrderedDrinkOption(itemId: Long): DrinkOption? {
////        options.forEach {
////            if (it.containDrinkOptionItem(itemId)) {
////
////            }
////        }
////    }
}
