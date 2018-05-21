package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.StaffDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import io.reactivex.Single

class StaffRemoteDataSource : StaffDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun login(account: String, pass: String) = apiService.staffLogin(account, pass)

    override fun getInfo(token: String) = apiService.staffInfo(token)

    override fun getBills(token: String, status: Int, id: String, page: Int) = apiService.getBillExpressStaff(token, status, id, page)

    override fun getBillInfo(token: String, id: Long) = apiService.getOrderInfo(token, id, 2)

    override fun checkBill(token: String, status: Int, id: Long) = apiService.checkBill(token, status, id)

    override fun getShippers(token: String, search: String, page: Int, status: Int) = apiService.getShippers(token, search, page, status)

    override fun getShipperInfo(token: String, shipperId: Long) = apiService.getShipper(token, shipperId)

    override fun changeUserStatus(token: String, userId: Long, status: Int, isShipper: Boolean) = apiService.changeUserStatus(token, userId, status, isShipper)
}
