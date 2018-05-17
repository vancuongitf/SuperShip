package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.source.datasource.StaffDataSource
import cao.cuong.supership.supership.data.source.remote.StaffRemoteDataSource

class StaffRepository : StaffDataSource {

    private val staffRemoteDataSource = StaffRemoteDataSource()

    override fun login(account: String, pass: String) = staffRemoteDataSource.login(account, pass)

    override fun getInfo(token: String) = staffRemoteDataSource.getInfo(token)

    override fun getBills(token: String, status: Int, id: String, page: Int) = staffRemoteDataSource.getBills(token, status, id, page)
}
