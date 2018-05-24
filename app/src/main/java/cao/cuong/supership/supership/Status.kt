package cao.cuong.supership.supership

object Status {

    // User status

    // Bill status
    internal const val BILL_WAITING_FOR_CHECK = 0
    internal const val BILL_BANNED = -1
    internal const val BILL_CHECKED = 1
    internal const val BILL_IN_TRANSIT = 2
    internal const val BILL_COMPLETED = 3

    // Shipper status
}
