package cao.cuong.supership.supership.ui.shipper.account.signup

import cao.cuong.supership.supership.data.source.ShipperRepository
import cao.cuong.supership.supership.data.source.remote.request.CreateShipperBody

/**
 *
 * @author at-cuongcao.
 */
class ShipperSignUpFragmentViewModel {

    private val shipperRepository = ShipperRepository()

    internal fun createUser(createShipperBody: CreateShipperBody) = shipperRepository.createShipper(createShipperBody)
}
