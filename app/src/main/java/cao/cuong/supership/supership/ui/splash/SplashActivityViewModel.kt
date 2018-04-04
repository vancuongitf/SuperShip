package cao.cuong.supership.supership.ui.splash

import cao.cuong.supership.supership.data.source.LocalRepository

/**
 *
 * @author at-cuongcao.
 */
class SplashActivityViewModel(private val localRepository: LocalRepository) {

    internal fun isDisableLocationPermission() = localRepository.isDisableLocationPermission()

    internal fun saveStatusOfLocationPermission(status: Boolean) = localRepository.saveOption(LocalRepository.SHARED_KEY_DISABLE_LOCATION_PERMISSION, status)
}
