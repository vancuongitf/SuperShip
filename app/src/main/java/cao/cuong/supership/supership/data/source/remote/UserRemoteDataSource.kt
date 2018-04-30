package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.model.UserInfo
import cao.cuong.supership.supership.data.source.datasource.UserDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody
import cao.cuong.supership.supership.data.source.remote.response.StoreExpressResponse
import io.reactivex.Single

/**
 *
 * @author at-cuongcao.
 */
class UserRemoteDataSource : UserDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun login(user: String, pass: String) = apiService.login(user, pass)

    override fun getUserInfo(token: String) = apiService.getUserInfo(token)

    override fun requestResetPassword(email: String) = apiService.requestResetPassword(email)

    override fun resetPassword(userId: Int, pass: String, otpCode: Int) = apiService.resetPassword(userId, pass, otpCode)

    override fun createUser(user: CreateUserBody) = apiService.createUser(user)

    override fun getStoreList(token: String, page: Int) = apiService.getStoreListOfUser(token, page)

}
