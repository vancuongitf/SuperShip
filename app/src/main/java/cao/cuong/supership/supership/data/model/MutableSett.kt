package cao.cuong.supership.supership.data.model

internal fun MutableSet<Long>.isSameOther(other: MutableSet<Long>): Boolean {
    if (this.size != other.size) {
        return false
    }
    return this.containsAll(other)
}
