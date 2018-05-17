package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.source.remote.network.CustomCall
import cao.cuong.supership.supership.data.source.remote.response.BillExpressResponse
import io.reactivex.Single

interface StaffDataSource {

    fun login(account: String, pass: String): Single<AccessToken>

    fun getInfo(token: String): Single<Staff>

    fun getBills(token: String, status: Int, id: String, page: Int): CustomCall<BillExpressResponse>
}
