package cao.cuong.supership.supership.data.source.datasource

/**
 *
 * @author at-cuongcao.
 */
interface LocalDataSource {

    fun <T> saveOption(key: String, value: T)

    fun isDisableLocationPermission(): Boolean
}
