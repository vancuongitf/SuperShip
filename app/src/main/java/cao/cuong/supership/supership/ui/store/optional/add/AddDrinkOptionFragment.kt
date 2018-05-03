package cao.cuong.supership.supership.ui.store.optional.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.source.remote.request.AddDrinkOptionItemBody
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkOptionBody
import cao.cuong.supership.supership.data.source.remote.request.DrinkOptionItemBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.isValidateStoreName
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment.Companion.KEY_STORE_ID
import org.jetbrains.anko.AnkoContext

class AddDrinkOptionFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeId: Long): AddDrinkOptionFragment {
            val instance = AddDrinkOptionFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    private val drinkOption = AddDrinkOptionItemBody("", mutableListOf())
    private lateinit var viewModel: AddDrinkOptionFragmentViewModel
    private lateinit var ui: AddDrinkOptionFragmentUI
    private var storeId = -1L

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(KEY_STORE_ID)
        viewModel = AddDrinkOptionFragmentViewModel(context)
        ui = AddDrinkOptionFragmentUI(drinkOption.items)
        return ui.createView(AnkoContext.Companion.create(context, this))
    }


    override fun onBindViewModel() {
        addDisposables(
                viewModel.progressDialogStatusObservable
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateProgressDialogStatus)
        )
    }

    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun addOptionalClicked() {
        val name = ui.edtOptionName.text.toString().trim()
        var message = ""
        if (name.isValidateStoreName() && name.length < 21) {
            if (drinkOption.items.isNotEmpty()) {
                val multiChoose = if (ui.checkBoxMultiChoose.isChecked) {
                    1
                } else {
                    0
                }
                val createDrinkOptionBody = CreateDrinkOptionBody("", storeId, multiChoose, name)
                viewModel.createDrinkOption(createDrinkOptionBody, drinkOption)
                        .observeOnUiThread()
                        .subscribe(this::handleCreateDrinkOptionSuccess, this::handleApiError)
            } else {
                message = "Vui lòng thêm lựa chọn."
            }
        } else {
            message = "Vui lòng điền tên tùy chọnlà đúng định dạng. Độ dài tối da là 20 ký tự."
        }
        if (message.isNotEmpty()) {
            handleApiError(Throwable(message))
        }
    }

    internal fun addItemOptionClick() {
        val name = ui.edtDrinkOptionItemName.text.toString().trim()
        val priceString = ui.edtDrinkOptionItemPrice.text.toString().trim()
        var message = ""
        if (name.isValidateStoreName() && name.length < 21) {
            try {
                val price = priceString.toInt()
                if (price >= 0) {
                    drinkOption.items.add(DrinkOptionItemBody(-1L, name, price))
                    ui.itemAdapter.notifyDataSetChanged()
                } else {
                    message = "Giá phải là một số nguyên không ."
                }
            } catch (e: NumberFormatException) {
                message = "Giá phải là một số nguyên dương."
            }
        } else {
            message = "Tên lựa chọn chưa đúng. Độ dài tối đa  20 ký tự."
        }
        if (message.isNotEmpty()) {
            handleApiError(Throwable(message))
        }
    }

    private fun handleCreateDrinkOptionSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            (activity as? StoreActivity)?.let {
                it.shouldReload = true
                it.onBackPressed()
            }
        }
    }
}
