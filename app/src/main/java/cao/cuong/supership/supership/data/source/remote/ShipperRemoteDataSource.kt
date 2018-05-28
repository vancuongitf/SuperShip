package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.ShipperDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.request.*

class ShipperRemoteDataSource : ShipperDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun createShipper(createShipperBody: CreateShipperBody) = apiService.createShipper(createShipperBody)

    override fun login(account: String, pass: String) = apiService.loginShipper(account, pass)

    override fun changeShipperPassword(shipperId: Long, oldPass: String, newPass: String) = apiService.changeShipperPassword(shipperId, oldPass, newPass)

    override fun getShipperInfo(token: String) = apiService.getShipperInfo(token)

    override fun getCheckedBills(token: String, page: Int) = apiService.getCheckedBills(token, page)

    override fun getBillInfo(token: String, id: Long) = apiService.getOrderInfo(token, id, 1)

    override fun takeBill(takeBillBody: TakeBillBody) = apiService.takeBill(takeBillBody)

    override fun getTookBills(token: String, page: Int, status: Int) = apiService.getTookBills(token, page, status)

    override fun completeBill(completeBillBody: CompleteBillBody) = apiService.completeBill(completeBillBody)

    override fun submitCurrentLocation(currentLocationBody: CurrentLocationBody) = apiService.submitCurrentLocation(currentLocationBody)

    override fun verifyCash(verifyCashBody: VerifyCashBody) = apiService.verifyCash(verifyCashBody)
}
