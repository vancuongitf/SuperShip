package cao.cuong.supership.supership.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.addChildFragment
import cao.cuong.supership.supership.extension.animBotToTop
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.home.main.HomeFragment
import cao.cuong.supership.supership.ui.home.search.SearchDialogFragment
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class HomeContainer : BaseFragment() {

    private lateinit var ui: HomeContainerUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = HomeContainerUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addChildFragment(R.id.homeContainer, HomeFragment(), null)
    }

    override fun onBindViewModel() = Unit

    internal fun openSearchDialog() {
        addChildFragment(R.id.homeContainer, SearchDialogFragment(), SearchDialogFragment::class.java.simpleName, {
            it.animBotToTop()
        })
    }
}
