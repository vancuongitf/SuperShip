package cao.cuong.supership.supership.ui.staff.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.model.rxevent.OpenUserActivityAlert
import cao.cuong.supership.supership.data.model.rxevent.UpdateAccountUI
import cao.cuong.supership.supership.data.source.remote.network.ApiException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.user.UserActivity
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext

class StaffInfoFragment : BaseFragment() {

    private val ui = StaffInfoFragmentUI()
    private lateinit var viewModel: StaffInfoFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = StaffInfoFragmentViewModel(context)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(true)
        RxBus.listen(UpdateAccountUI::class.java)
                .observeOnUiThread()
                .subscribe {
                    updateUI()
                }
    }


    override fun onBindViewModel() {

    }

    internal fun eventReloadClicked() {
        updateUI()
    }

    internal fun eventChangePasswordButtonClicked() {
        (activity as? BaseActivity)?.startUserActivity()
    }

    internal fun logOutClick() {
        viewModel.logOut()
        ui.llNonLogin.visibility = View.VISIBLE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.GONE
    }

    internal fun eventLoginButtonClick() {
        val intent = Intent(context, UserActivity::class.java)
        startActivityForResult(intent, UserActivity.USER_ACTIVITY_REQUEST_CODE)
    }

    private fun updateUI(getNewData: Boolean = false) {
        if (viewModel.isLogin()) {
            val localStaffInfo = viewModel.getLocalStaffInfo()
            if (localStaffInfo == null || getNewData) {
                ui.llNonLogin.visibility = View.GONE
                ui.llLogin.visibility = View.GONE
                ui.tvReload.visibility = View.GONE
                viewModel.getStaffInfo()
                        .observeOnUiThread()
                        .subscribe(this::handleGetStaffInfoSuccess, this::handleGetStaffInfoFail)
            } else {
                Log.i("tag11", Gson().toJson(localStaffInfo))
                handleGetStaffInfoSuccess(localStaffInfo)
            }
        } else {
            ui.llNonLogin.visibility = View.VISIBLE
            ui.llLogin.visibility = View.GONE
            ui.tvReload.visibility = View.GONE
        }
    }

    private fun handleGetStaffInfoSuccess(staff: Staff) {
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.VISIBLE
        ui.tvReload.visibility = View.GONE
        ui.edtId.editText.setText(staff.id.toString())
        ui.edtFullName.editText.setText(staff.fullName)
        ui.edtPersonalId.editText.setText(staff.personalId)
        ui.edtEmail.editText.setText(staff.email)
        ui.edtPhoneNumber.editText.setText(staff.phoneNumber)
        ui.edtBirthDay.editText.setText(staff.birthDay)
        ui.edtAddress.editText.setText(staff.address)
        viewModel.saveStaffInfo(staff)
    }

    private fun handleGetStaffInfoFail(throwable: Throwable) {
        ui.llNonLogin.visibility = View.GONE
        ui.llLogin.visibility = View.GONE
        ui.tvReload.visibility = View.VISIBLE
        if (throwable is ApiException) {
            if (throwable.code == 401) {
                RxBus.publish(OpenUserActivityAlert())
            }
        }
    }
}
