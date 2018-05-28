package cao.cuong.supership.supership.data.source.remote

import cao.cuong.supership.supership.data.model.paypal.VerifyPaymentBody
import cao.cuong.supership.supership.data.source.datasource.UserDataSource
import cao.cuong.supership.supership.data.source.remote.network.ApiClient
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody

/**
 *
 * @author at-cuongcao.
 */
class UserRemoteDataSource : UserDataSource {

    private val apiService = ApiClient.getInstance(null).service

    override fun login(user: String, pass: String) = apiService.login(user, pass)

    override fun changePassword(oldPass: String, newPass: String, token: String) = apiService.changePassword(oldPass, newPass, token)

    override fun getUserInfo(token: String) = apiService.getUserInfo(token)

    override fun updateUserInfo(userId: Long, name: String, phone: String) = apiService.updateUserInfo(userId, name, phone)

    override fun requestResetPassword(email: String) = apiService.requestResetPassword(email)

    override fun resetPassword(userId: Long, pass: String, otpCode: Int) = apiService.resetPassword(userId, pass, otpCode)

    override fun createUser(user: CreateUserBody) = apiService.createUser(user)

    override fun getStoreList(token: String, page: Int) = apiService.getStoreListOfUser(token, page)

    override fun getOrders(token: String, page: Int) = apiService.getOrdersOfUser(token, page)

    override fun getBillInfo(token: String, id: Long) = apiService.getOrderInfo(token, id)

    override fun verifyPayment(verifyPaymentBody: VerifyPaymentBody) = apiService.verifyPayment(verifyPaymentBody)
}
