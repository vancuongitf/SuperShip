package cao.cuong.supership.supership.extension

internal fun Int.numberToString(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

internal fun Int.getBillStatus() = when (this) {
    0 -> "Đang chờ xác nhận"

    1 -> "Đã đặt hàng"

    2 -> "Đang vận chuyển"

    3 -> "Đã giao hàng"

    else -> "Đã từ chối"
}
