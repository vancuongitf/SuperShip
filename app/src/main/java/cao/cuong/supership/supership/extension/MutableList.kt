package cao.cuong.supership.supership.extension

import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.model.DrinkOptionItem
import cao.cuong.supership.supership.data.model.OptionalBody
import cao.cuong.supership.supership.data.model.StoreInfoExpress

/**
 *
 * @author at-cuongcao.
 */
internal fun MutableList<StoreInfoExpress>.addAllDistinct(list: List<StoreInfoExpress>) {
    for (store in list) {
        if (this.find { it.storeId == store.storeId } == null) {
            this.add(store)
        }
    }
}

internal fun MutableList<DrinkOption>.getOrderedOption(orderedOption: OptionalBody): DrinkOption? {
    forEach {
        if (it.id == orderedOption.drinkOptionsId) {
            return it.getOrderedOption(orderedOption.options)
        }
    }
    return null
}

internal fun MutableList<DrinkOption>.getOrderedOptions(orderedOption: MutableSet<OptionalBody>): MutableList<DrinkOption> {
    val result = mutableListOf<DrinkOption>()
    orderedOption.forEach {
        getOrderedOption(it)?.let {
            result.add(it)
        }
    }
    return result
}

internal fun MutableList<DrinkOption>.getDrinkOptionItems(): MutableList<DrinkOptionItem> {
    val result = mutableListOf<DrinkOptionItem>()
    forEach {
        result.addAll(it.items)
    }
    return result
}

