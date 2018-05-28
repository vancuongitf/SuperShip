package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.request.*
import cao.cuong.supership.supership.data.source.remote.response.BillExpressResponse
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import io.reactivex.Single

interface ShipperDataSource {

    fun createShipper(createShipperBody: CreateShipperBody): Single<MessageResponse>

    fun login(account: String, pass: String): Single<Shipper>

    fun changeShipperPassword(shipperId: Long, oldPass: String, newPass: String): Single<MessageResponse>

    fun getShipperInfo(token: String): Single<Shipper>

    fun getCheckedBills(token: String, page: Int): Single<BillExpressResponse>

    fun getBillInfo(token: String, id: Long): Single<BillInfo>

    fun takeBill(takeBillBody: TakeBillBody): Single<MessageResponse>

    fun getTookBills(token: String, page: Int, status: Int): Single<BillExpressResponse>

    fun completeBill(completeBillBody: CompleteBillBody): Single<MessageResponse>

    fun submitCurrentLocation(currentLocationBody: CurrentLocationBody): CustomCall<MessageResponse>

    fun verifyCash(verifyCashBody: VerifyCashBody): Single<MessageResponse>
}
