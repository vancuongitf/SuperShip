package cao.cuong.supership.supership.data.source.datasource

import cao.cuong.supership.supership.data.model.AccessToken
import cao.cuong.supership.supership.data.source.remote.request.CreateUserBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.data.source.remote.response.RequestResetPassResponse
import io.reactivex.Single

/**
 *
 * @author at-cuongcao.
 */
interface UserDataSource {

    fun createUser(user: CreateUserBody): Single<MessageResponse>

    fun login(user: String, pass: String): Single<AccessToken>

    fun requestResetPassword(email: String): Single<RequestResetPassResponse>

    fun resetPassword(userId: Int, pass: String, otpCode: Int): Single<AccessToken>
}
