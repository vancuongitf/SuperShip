package cao.cuong.supership.supership.extension

/**
 *
 * @author at-cuongcao.
 */
internal fun Long.getDistanceString(): String {
    if (this < 1000) {
        return ">${this}m"
    } else {
        var value = Math.ceil(this.toDouble() / 10) / 100
        return ">${value}Km"
    }
}
