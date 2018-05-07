package cao.cuong.supership.supership.extension

import cao.cuong.supership.supership.data.model.OptionalBody

internal fun <T> MutableSet<T>.isSameOther(other: MutableSet<T>): Boolean {
    if (this.size != other.size) {
        return false
    }
    return this.containsAll(other)
}

internal fun MutableSet<OptionalBody>.getDrinkOptionItemIds(): MutableSet<Long> {
    val result = mutableSetOf<Long>()
    forEach {
        result.addAll(it.options)
    }
    return result
}
