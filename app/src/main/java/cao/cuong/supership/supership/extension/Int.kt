package cao.cuong.supership.supership.extension

internal fun Int.numberToString(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}
