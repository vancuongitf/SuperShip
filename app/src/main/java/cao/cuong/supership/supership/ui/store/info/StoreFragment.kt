package cao.cuong.supership.supership.ui.store.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.ui.base.BaseFragment
import org.jetbrains.anko.AnkoContext

/**
 *
 * @author at-cuongcao.
 */
class StoreFragment : BaseFragment() {

    private lateinit var ui: StoreFragmentUI

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = StoreFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }


    override fun onBindViewModel() {

    }

    internal fun onBackClicked() {

    }
}
