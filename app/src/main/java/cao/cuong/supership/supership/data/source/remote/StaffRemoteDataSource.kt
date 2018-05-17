package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.source.datasource.StaffDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient

class StaffRemoteDataSource : StaffDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun login(account: String, pass: String) = apiService.staffLogin(account, pass)

    override fun getInfo(token: String) = apiService.staffInfo(token)

    override fun getBills(token: String, status: Int, id: String, page: Int) = apiService.getBillExpressStaff(token, status, id, page)
}
