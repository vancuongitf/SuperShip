package cao.cuong.supership.supership.ui.staff.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

abstract class StaffBaseFragment : BaseFragment() {

    internal lateinit var ui: StaffBaseFragmentUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = StaffBaseFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() = Unit

    abstract fun onRefresh()
}
