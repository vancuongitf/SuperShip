package cao.cuong.supership.supership.ui.staff.shipper.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
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
    private lateinit var ui: StaffShipperInfoFragmentUI
    private lateinit var viewModel: StaffShipperInfoFragmentViewModel
    private var shipper: Shipper? = null

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
                        it.status = 1
                        handleGetShipperInfoSuccess(it)
                    }
                }

                1 -> {
                    context.showConfirmAlert(R.string.banConfirm) {
                        it.status = 2
                        handleGetShipperInfoSuccess(it)
                    }
                }
            }
        }
    }

    internal fun eventBannedButtonClicked() {
        shipper?.let {
            context.showConfirmAlert(R.string.banConfirm) {
                it.status = 2
                handleGetShipperInfoSuccess(it)
            }
        }
    }

    private fun handleGetShipperInfoSuccess(shipper: Shipper) {
        this.shipper = shipper
        ui.edtId.editText.setText(shipper.id.toString())
        ui.edtFullName.editText.setText(shipper.fullName)
        ui.edtDeposit.editText.setText(context.getString(R.string.billPrice, shipper.deposit))
        ui.edtAddress.editText.setText(shipper.address)
        ui.edtBirthDay.editText.setText(shipper.birthDay)
        ui.edtPersonalId.editText.setText(shipper.personalId)
        ui.edtEmail.editText.setText(shipper.email)
        ui.edtPhoneNumber.editText.setText(shipper.phone)
        if (shipper.status == 0) {
            ui.rlBannedButton.visibility = View.VISIBLE
            ui.imgBanned.setImageResource(R.drawable.ic_ban)
            ui.rlBannedButton.isEnabled = true
        } else {
            ui.rlBannedButton.visibility = View.GONE
            ui.imgBanned.setImageResource(R.drawable.ic_bg_tranparent)
            ui.rlBannedButton.isEnabled = false
        }
        if (shipper.status == 1) {
            ui.rlChangeStatusButton.visibility = View.VISIBLE
            ui.imgChangeStatus.setImageResource(R.drawable.ic_ban)
        } else {
            ui.rlChangeStatusButton.visibility = View.VISIBLE
            ui.imgChangeStatus.setImageResource(R.drawable.ic_check_button)
        }
    }
}
