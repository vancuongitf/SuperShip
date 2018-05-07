package cao.cuong.supership.supership.extension

import cao.cuong.supership.supership.BuildConfig

/**
 *
 * @author at-cuongcao.
 */

internal fun Long.getDistanceString(): String {
    if (this < 1000) {
        return ">${this}m"
    } else {
        val value = Math.ceil(this.toDouble() / 100) / 10
        return ">${value}Km"
    }
}

internal fun Long.getDistanceShipString(): String {
    val value = Math.ceil(this.toDouble() / 100) / 10
    return "${value}Km"
}

internal fun Long.getShipFee(): Long {
    val value = Math.ceil(this.toDouble() / 100) / 10 * BuildConfig.SHIP_FEE
    var shipFee = (Math.ceil(value / 1000) * 1000).toLong()
    if (shipFee < BuildConfig.MIN_SHIP_FEE) {
        shipFee = BuildConfig.MIN_SHIP_FEE
    }
    return shipFee
}
