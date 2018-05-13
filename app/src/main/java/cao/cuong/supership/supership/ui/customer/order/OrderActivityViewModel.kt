package cao.cuong.supership.supership.ui.customer.order

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class OrderActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    fun getUserInfo() = localRepository.getUserInfo()
}
