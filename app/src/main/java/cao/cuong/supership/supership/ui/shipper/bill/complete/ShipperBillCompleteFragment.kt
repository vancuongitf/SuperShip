package cao.cuong.supership.supership.ui.shipper.bill.complete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.rxevent.UpdateBillStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.CompleteBillBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

class ShipperBillCompleteFragment : BaseFragment() {

    companion object {
        private const val KEY_BILL_ID = "key_bill_id"

        internal fun getNewInstance(billId: Long): ShipperBillCompleteFragment {
            val instance = ShipperBillCompleteFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_BILL_ID, billId)
            }
            return instance
        }
    }

    private lateinit var ui: ShipperBillCompleteFragmentUI
    private lateinit var viewModel: ShipperBillCompleteFragmentViewModel
    private var billId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        billId = arguments.getLong(KEY_BILL_ID)
        viewModel = ShipperBillCompleteFragmentViewModel(context)
        ui = ShipperBillCompleteFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateProgressDialogStatus
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
    }

    override fun onBindViewModel() {
    }

    internal fun eventCompleteClick() {
        hideKeyboard()
        viewModel.completeBill(CompleteBillBody(viewModel.getAccessToken(), billId, ui.edtConfirmCode.text.toString().trim()))
                .observeOnUiThread()
                .subscribe(this::handleCompleteBillSuccess, this::handleApiError)
    }

    private fun handleCompleteBillSuccess(messageResponse: MessageResponse) {
        RxBus.publish(UpdateBillStatus())
        context.showOkAlert(R.string.notification, messageResponse.message) {
            activity.onBackPressed()
        }
    }
}
