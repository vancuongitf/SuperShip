package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.StaffDataSource
import cao.cuong.supership.supership.data.source.remote.StaffRemoteDataSource

class StaffRepository : StaffDataSource {

    private val staffRemoteDataSource = StaffRemoteDataSource()

    override fun login(account: String, pass: String) = staffRemoteDataSource.login(account, pass)

    override fun getInfo(token: String) = staffRemoteDataSource.getInfo(token)

    override fun changePass(staffId: Long, oldPass: String, newPass: String) = staffRemoteDataSource.changePass(staffId, oldPass, newPass)

    override fun requestResetPassword(email: String) = staffRemoteDataSource.requestResetPassword(email)

    override fun resetPassword(staffId: Long, pass: String, otpCode: Int) = staffRemoteDataSource.resetPassword(staffId, pass, otpCode)

    override fun getBills(token: String, status: Int, id: String, page: Int) = staffRemoteDataSource.getBills(token, status, id, page)

    override fun getBillInfo(token: String, id: Long) = staffRemoteDataSource.getBillInfo(token, id)

    override fun checkBill(token: String, status: Int, id: Long) = staffRemoteDataSource.checkBill(token, status, id)

    override fun getShippers(token: String, search: String, page: Int, status: Int) = staffRemoteDataSource.getShippers(token, search, page, status)

    override fun getShipperInfo(token: String, shipperId: Long) = staffRemoteDataSource.getShipperInfo(token, shipperId)

    override fun changeUserStatus(token: String, userId: Long, status: Int, isShipper: Boolean) = staffRemoteDataSource.changeUserStatus(token, userId, status, isShipper)
}
