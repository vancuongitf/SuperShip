package cao.cuong.supership.supership.ui.shipper.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Shipper
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import cao.cuong.supership.supership.ui.shipper.cash.CashActivity
import org.jetbrains.anko.AnkoContext

class ShipperInfoFragment : BaseFragment() {

    private lateinit var ui: ShipperInfoFragmentUI
    private lateinit var viewModel: ShipperInfoFragmentViewModel
    private var shipperId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = ShipperInfoFragmentUI()
        viewModel = ShipperInfoFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi(UpdateAccountUI(), true)
        RxBus.listen(UpdateAccountUI::class.java)
                .observeOnUiThread()
                .subscribe({ updateUi(UpdateAccountUI(), false) })
    }

    override fun onBindViewModel() {
    }

    internal fun eventEditInfoClicked() {

    }

    internal fun eventReloadClicked() {
    }

    internal fun eventLoginButtonClick() {
        val intent = Intent(context, UserActivity::class.java)
        startActivityForResult(intent, UserActivity.USER_ACTIVITY_REQUEST_CODE)
    }

    internal fun eventCashClicked() {
        val intent = Intent(context, CashActivity::class.java)
        intent.putExtras(Bundle().apply {
            putLong(CashActivity.KEY_SHIPPER_ID, shipperId)
        })
        startActivity(intent)
    }

    internal fun eventChangePasswordButtonClicked() {
        val intent = Intent(context, UserActivity::class.java)
        startActivity(intent)
    }

    internal fun logOutClick() {
        viewModel.logOut()
        ui.llNonLogin.visibility = View.VISIBLE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.GONE
    }

    private fun updateUi(event: UpdateAccountUI, getNewData: Boolean = false) {
        if (viewModel.isLogin()) {
            ui.llNonLogin.visibility = View.GONE
            ui.llLogin.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
            val localShipper = viewModel.getLocalShipperInfo()
            if (localShipper == null || getNewData) {
                viewModel.getShipperInfo()
                        .observeOnUiThread()
                        .subscribe(this::handleGetShipperInfoSuccess, {})
            } else {
                handleGetShipperInfoSuccess(localShipper)
            }
        } else {
            ui.tvReload.visibility = View.GONE
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
        }
    }

    private fun handleGetShipperInfoSuccess(shipper: Shipper) {
        shipperId = shipper.id
        ui.edtFullName.editText.setText(shipper.fullName)
        ui.edtBirthDay.editText.setText(shipper.birthDay)
        ui.edtAddress.editText.setText(shipper.address)
        ui.edtEmail.editText.setText(shipper.email)
        ui.edtPhoneNumber.editText.setText(shipper.phone)
        ui.edtDeposit.editText.setText(context.getString(R.string.billPrice, shipper.deposit))
        ui.llLogin.visibility = View.VISIBLE
        viewModel.saveShipperInfo(shipper)
    }
}
