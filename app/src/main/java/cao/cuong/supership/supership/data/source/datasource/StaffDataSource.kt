package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.BillInfo
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.response.BillExpressResponse
import cao.cuong.supership.supership.data.source.remote.response.ExpressShipperResponse
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.RequestResetPassResponse
import io.reactivex.Single

interface StaffDataSource {

    fun login(account: String, pass: String): Single<Staff>

    fun getInfo(token: String): Single<Staff>

    fun changePass(staffId: Long, oldPass: String, newPass: String): Single<MessageResponse>

    fun requestResetPassword(email: String): Single<RequestResetPassResponse>

    fun resetPassword(staffId: Long, pass: String, otpCode: Int): Single<AccessToken>

    fun getBills(token: String, status: Int, id: String, page: Int): CustomCall<BillExpressResponse>

    fun getBillInfo(token: String, id: Long): Single<BillInfo>

    fun checkBill(token: String, status: Int, id: Long): Single<MessageResponse>

    fun getShippers(token: String, search: String, page: Int, status: Int): CustomCall<ExpressShipperResponse>

    fun getShipperInfo(token: String, shipperId: Long): Single<Shipper>

    fun changeUserStatus(token: String, userId: Long, status: Int, isShipper: Boolean): Single<MessageResponse>
}
