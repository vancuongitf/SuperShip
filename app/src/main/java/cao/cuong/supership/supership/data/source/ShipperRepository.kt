package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.ShipperDataSource
import cao.cuong.supership.supership.data.source.remote.ShipperRemoteDataSource
import cao.cuong.supership.supership.data.source.remote.request.*

class ShipperRepository : ShipperDataSource {

    private val shipperRemoteDataSource = ShipperRemoteDataSource()

    override fun createShipper(createShipperBody: CreateShipperBody) = shipperRemoteDataSource.createShipper(createShipperBody)

    override fun login(account: String, pass: String) = shipperRemoteDataSource.login(account, pass)

    override fun changeShipperPassword(shipperId: Long, oldPass: String, newPass: String) = shipperRemoteDataSource.changeShipperPassword(shipperId, oldPass, newPass)

    override fun requestResetPassword(email: String) = shipperRemoteDataSource.requestResetPassword(email)

    override fun resetShipperPassword(shipperId: Long, pass: String, otpCode: Int) = shipperRemoteDataSource.resetShipperPassword(shipperId, pass, otpCode)

    override fun getShipperInfo(token: String) = shipperRemoteDataSource.getShipperInfo(token)

    override fun updateShipperInfo(shipperId: Long, phone: String) = shipperRemoteDataSource.updateShipperInfo(shipperId, phone)

    override fun getCheckedBills(token: String, page: Int) = shipperRemoteDataSource.getCheckedBills(token, page)

    override fun getBillInfo(token: String, id: Long) = shipperRemoteDataSource.getBillInfo(token, id)

    override fun takeBill(takeBillBody: TakeBillBody) = shipperRemoteDataSource.takeBill(takeBillBody)

    override fun getTookBills(token: String, page: Int, status: Int) = shipperRemoteDataSource.getTookBills(token, page, status)

    override fun completeBill(completeBillBody: CompleteBillBody) = shipperRemoteDataSource.completeBill(completeBillBody)

    override fun submitCurrentLocation(currentLocationBody: CurrentLocationBody) = shipperRemoteDataSource.submitCurrentLocation(currentLocationBody)

    override fun verifyCash(verifyCashBody: VerifyCashBody) = shipperRemoteDataSource.verifyCash(verifyCashBody)
}
