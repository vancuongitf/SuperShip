package cao.cuong.supership.supership.ui.customer.bill.activity

import android.content.Context
import cao.cuong.supership.supership.data.source.LocalRepository

class BillActivityViewModel(context: Context) {

    private val localRepository = LocalRepository(context)

    internal fun getModule() = localRepository.getModule()
}
