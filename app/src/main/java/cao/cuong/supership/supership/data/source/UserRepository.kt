package cao.cuong.supership.supership.data.source

import cao.cuong.supership.supership.data.model.paypal.VerifyPaymentBody
import cao.cuong.supership.supership.data.source.datasource.UserDataSource
import cao.cuong.supership.supership.data.source.remote.UserRemoteDataSource
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody

/**
 *
 * @author at-cuongcao.
 */
class UserRepository : UserDataSource {

    private val userRemoteDataSource = UserRemoteDataSource()

    override fun login(user: String, pass: String) = userRemoteDataSource.login(user, pass)

    override fun changePassword(oldPass: String, newPass: String, token: String) = userRemoteDataSource.changePassword(oldPass, newPass, token)

    override fun getUserInfo(token: String) = userRemoteDataSource.getUserInfo(token)

    override fun updateUserInfo(userId: Long, name: String, phone: String) = userRemoteDataSource.updateUserInfo(userId, name, phone)

    override fun requestResetPassword(email: String) = userRemoteDataSource.requestResetPassword(email)

    override fun resetPassword(userId: Long, pass: String, otpCode: Int) = userRemoteDataSource.resetPassword(userId, pass, otpCode)

    override fun createUser(user: CreateUserBody) = userRemoteDataSource.createUser(user)

    override fun getStoreList(token: String, page: Int) = userRemoteDataSource.getStoreList(token, page)

    override fun getOrders(token: String, page: Int) = userRemoteDataSource.getOrders(token, page)

    override fun getBillInfo(token: String, id: Long) = userRemoteDataSource.getBillInfo(token, id)

    override fun verifyPayment(verifyPaymentBody: VerifyPaymentBody) = userRemoteDataSource.verifyPayment(verifyPaymentBody)
}
