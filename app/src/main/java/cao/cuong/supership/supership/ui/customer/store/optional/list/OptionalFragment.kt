package cao.cuong.supership.supership.ui.customer.store.optional.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showConfirmAlert
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment.Companion.KEY_STORE_ID
import org.jetbrains.anko.AnkoContext

class OptionalFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeId: Long): OptionalFragment {
            val instance = OptionalFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    private lateinit var ui: OptionalFragmentUI
    private lateinit var viewModel: OptionalFragmentViewModel
    private var options = mutableListOf<DrinkOption>()
    private var storeId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? StoreActivity)?.let {
            options = it.getDrinkOptions()
        }
        storeId = arguments.getLong(KEY_STORE_ID)
        ui = OptionalFragmentUI(options)
        viewModel = OptionalFragmentViewModel(context)
        ui.optionAdapter.onItemAction = this::onOptionItemAction
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

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun addOptionalClicked() {
        (activity as? StoreActivity)?.openAddDrinkOptionFragment(storeId)
    }

    private fun onOptionItemAction(view: View, drinkOption: DrinkOption) {
        when (view.id) {
            R.id.imgDeleteDrinkOption -> {
                context.showConfirmAlert(R.string.confirmDeleteDrink) {
                    viewModel.deleteDrinkOption(drinkOption.id)
                            .observeOnUiThread()
                            .subscribe({
                                (activity as? StoreActivity)?.shouldReload = true
                                context.showOkAlert(R.string.notification, it.message) {
                                    options.remove(drinkOption)
                                    ui.optionAdapter.notifyDataSetChanged()
                                }
                            }, this::handleApiError)
                }
            }

            R.id.imgEditDrinkOption -> {
                (activity as? StoreActivity)?.openEditDrinkOptionFragment(drinkOption)
            }
        }
    }
}
