package cao.cuong.supership.supership.ui.staff.shipper.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

class StaffShipperInfoFragment : BaseFragment() {

    companion object {

        internal const val KEY_SHIPPER_ID = "shipper_id"

        internal fun getNewInstance(shipperId: Long): StaffShipperInfoFragment {
            val instance = StaffShipperInfoFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_SHIPPER_ID, shipperId)
            }
            return instance
        }
    }

    private var shipperId = -1L
    private var shipper: Shipper? = null
    private var targetStatus = -1
    private lateinit var ui: StaffShipperInfoFragmentUI
    private lateinit var viewModel: StaffShipperInfoFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = StaffShipperInfoFragmentViewModel(context)
        ui = StaffShipperInfoFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateProgressStatusObservable
                .observeOnUiThread()
                .subscribe(this::handleUpdateProgressDialogStatus)
        shipperId = arguments.getLong(KEY_SHIPPER_ID)
        viewModel.getShipperInfo(shipperId)
                .observeOnUiThread()
                .subscribe(this::handleGetShipperInfoSuccess, this::handleApiError)
    }

    override fun onBindViewModel() {

    }

    internal fun onBackButtonClicked() {
        activity.onBackPressed()
    }

    internal fun eventChangeStatusClicked() {
        shipper?.let {
            when (it.status) {
                0, 2 -> {
                    context.showConfirmAlert(R.string.activeConfirm) {
                        changeShipperStatus(1)
                    }
                }

                1 -> {
                    context.showConfirmAlert(R.string.banConfirm) {
                        changeShipperStatus(2)
                    }
                }
            }
        }
    }

    internal fun eventBannedButtonClicked() {
        shipper?.let {
            context.showConfirmAlert(R.string.banConfirm) {
                changeShipperStatus(2)
            }
        }
    }

    private fun handleGetShipperInfoSuccess(newShipper: Shipper) {
        this.shipper = newShipper
        shipper?.let {
            ui.edtId.editText.setText(it.id.toString())
            ui.edtFullName.editText.setText(it.fullName)
            ui.edtDeposit.editText.setText(context.getString(R.string.billPrice, it.deposit))
            ui.edtAddress.editText.setText(it.address)
            ui.edtBirthDay.editText.setText(it.birthDay)
            ui.edtPersonalId.editText.setText(it.personalId)
            ui.edtEmail.editText.setText(it.email)
            ui.edtPhoneNumber.editText.setText(it.phone)
            if (it.status == 0) {
                ui.rlBannedButton.visibility = View.VISIBLE
            } else {
                ui.rlBannedButton.visibility = View.GONE
            }
            if (it.status == 1) {
                ui.rlChangeStatusButton.visibility = View.VISIBLE
                ui.imgChangeStatus.setImageResource(R.drawable.ic_ban)
            } else {
                ui.rlChangeStatusButton.visibility = View.VISIBLE
                ui.imgChangeStatus.setImageResource(R.drawable.ic_check_button)
            }
        }
    }

    private fun changeShipperStatus(status: Int) {
        targetStatus = status
        viewModel.changeShipperStatus(shipperId, status)
                .observeOnUiThread()
                .subscribe(this::handleChangeStatusSuccess, this::handleApiError)
    }

    private fun handleChangeStatusSuccess(messageResponse: MessageResponse) {
        shipper?.status = targetStatus
        context.showOkAlert(R.string.notification, messageResponse.message) {
            shipper?.let {
                handleGetShipperInfoSuccess(it)
            }
        }
    }
}
