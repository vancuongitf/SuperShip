package cao.cuong.supership.supership.ui.splash.module

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.main.MainActivity
import cao.cuong.supership.supership.ui.shipper.main.ShipperMainActivity
import cao.cuong.supership.supership.ui.splash.splash.SplashFragment
import org.jetbrains.anko.AnkoContext

class ChooseModuleFragment : BaseFragment() {

    private lateinit var ui: ChooseModuleFragmentUI
    private lateinit var viewModel: ChooseModuleFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ChooseModuleFragmentViewModel(context)
        ui = ChooseModuleFragmentUI()
        return ui.createView(AnkoContext.create(context, this))
    }

    override fun onBindViewModel() {

    }

    internal fun eventCustomerClick() {
        viewModel.chooseModule(SplashFragment.CUSTOMER_MODULE)
        startActivity(Intent(context, MainActivity::class.java))
        activity.finish()
    }

    internal fun eventShipperClick() {
        viewModel.chooseModule(SplashFragment.SHIPPER_MODULE)
        startActivity(Intent(context, ShipperMainActivity::class.java))
        activity.finish()
    }

    internal fun eventStaffClick() {
    }
}
