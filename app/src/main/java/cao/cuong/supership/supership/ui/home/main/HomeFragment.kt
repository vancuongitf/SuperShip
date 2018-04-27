package cao.cuong.supership.supership.ui.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext
import cao.cuong.supership.supership.ui.home.HomeContainer

/**
 *
 * @author at-cuongcao.
 */
class HomeFragment : BaseFragment() {

    private lateinit var ui: HomeFragmentUI
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = HomeFragmentViewModel()
        ui = HomeFragmentUI(HomeFragmentAdapter(childFragmentManager))
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() = Unit

    internal fun handleSearchViewClicked() {
        (parentFragment as? HomeContainer)?.openSearchDialog()
    }
}
