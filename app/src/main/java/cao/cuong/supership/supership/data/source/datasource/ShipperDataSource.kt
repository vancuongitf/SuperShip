package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.request.*
import cao.cuong.supership.supership.data.source.remote.response.BillExpressResponse
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.RequestResetPassResponse
import io.reactivex.Single

interface ShipperDataSource {

    fun createShipper(createShipperBody: CreateShipperBody): Single<MessageResponse>

    fun login(account: String, pass: String): Single<Shipper>

    fun changeShipperPassword(shipperId: Long, oldPass: String, newPass: String): Single<MessageResponse>

    fun requestResetPassword(email: String): Single<RequestResetPassResponse>

    fun resetShipperPassword(shipperId: Long, pass: String, otpCode: Int): Single<AccessToken>

    fun getShipperInfo(token: String): Single<Shipper>

    fun updateShipperInfo(shipperId: Long, phone: String): Single<MessageResponse>

    fun getCheckedBills(token: String, page: Int): Single<BillExpressResponse>

    fun getBillInfo(token: String, id: Long): Single<BillInfo>

    fun takeBill(takeBillBody: TakeBillBody): Single<MessageResponse>

    fun getTookBills(token: String, page: Int, status: Int): Single<BillExpressResponse>

    fun completeBill(completeBillBody: CompleteBillBody): Single<MessageResponse>

    fun submitCurrentLocation(currentLocationBody: CurrentLocationBody): CustomCall<MessageResponse>

    fun verifyCash(verifyCashBody: VerifyCashBody): Single<MessageResponse>
}
