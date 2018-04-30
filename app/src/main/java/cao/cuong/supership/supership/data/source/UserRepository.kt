package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.datasource.UserDataSource
import cao.cuong.supership.supership.data.source.remote.UserRemoteDataSource
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single

/**
 *
 * @author at-cuongcao.
 */
class UserRepository : UserDataSource {

    private val userRemoteDataSource = UserRemoteDataSource()

    override fun login(user: String, pass: String) = userRemoteDataSource.login(user, pass)

    override fun getUserInfo(token: String) = userRemoteDataSource.getUserInfo(token)

    override fun requestResetPassword(email: String) = userRemoteDataSource.requestResetPassword(email)

    override fun resetPassword(userId: Int, pass: String, otpCode: Int) = userRemoteDataSource.resetPassword(userId, pass, otpCode)

    override fun createUser(user: CreateUserBody) = userRemoteDataSource.createUser(user)

    override fun getStoreList(token: String, page: Int) = userRemoteDataSource.getStoreList(token, page)
}
