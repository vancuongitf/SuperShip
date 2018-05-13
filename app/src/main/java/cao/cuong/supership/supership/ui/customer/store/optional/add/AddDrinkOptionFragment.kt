package cao.cuong.supership.supership.ui.customer.store.optional.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.source.remote.request.AddDrinkOptionItemBody
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkOptionBody
import cao.cuong.supership.supership.data.source.remote.request.DrinkOptionItemBody
import cao.cuong.supership.supership.data.source.remote.request.EditDrinkOptionBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.hideKeyBoard
import cao.cuong.supership.supership.extension.isValidateStoreName
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment.Companion.KEY_STORE_ID
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext

class AddDrinkOptionFragment : BaseFragment() {

    companion object {

        private const val KEY_DRINK_OPTION = "key_drink_option"

        internal fun getNewInstance(storeId: Long, drinkOption: DrinkOption? = null): AddDrinkOptionFragment {
            val instance = AddDrinkOptionFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY_STORE_ID, storeId)
                if (drinkOption != null) {
                    putString(KEY_DRINK_OPTION, Gson().toJson(drinkOption).toString())
                }
            }
            return instance
        }
    }

    private val drinkOption = AddDrinkOptionItemBody("", mutableListOf())
    private lateinit var viewModel: AddDrinkOptionFragmentViewModel
    private lateinit var ui: AddDrinkOptionFragmentUI
    private lateinit var oldOption: DrinkOption
    private var storeId = -1L
    private var action = Action.CREATE

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(KEY_STORE_ID)
        if (arguments.containsKey(KEY_DRINK_OPTION)) {
            try {
                oldOption = Gson().fromJson(arguments.getString(KEY_DRINK_OPTION), DrinkOption::class.java)
                action = Action.EDIT
            } catch (e: Exception) {
                activity.onBackPressed()
            }
        }
        viewModel = AddDrinkOptionFragmentViewModel(context)
        ui = AddDrinkOptionFragmentUI(drinkOption.items)
        ui.itemAdapter.onDeleteItem = this::onDeleteItem
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (action == Action.EDIT) {
            ui.edtOptionName.setText(oldOption.name)
            ui.checkBoxMultiChoose.isChecked = oldOption.multiChoose == 1
            oldOption.items.forEach {
                drinkOption.items.add(DrinkOptionItemBody(it.drinkOptionId, it.name, it.price, it.id))
            }
        }
    }

    override fun onBindViewModel() {
        addDisposables(
                viewModel.progressDialogStatusObservable
                        .observeOnUiThread()
                        .subscribe(this::handleUpdateProgressDialogStatus)
        )
    }

    internal fun onBackClicked() {
        (activity as? BaseActivity)?.let {
            it.hideKeyBoard()
            it.onBackPressed()
        }
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
                if (action == Action.CREATE) {
                    val createDrinkOptionBody = CreateDrinkOptionBody("", storeId, multiChoose, name)
                    viewModel.createDrinkOption(createDrinkOptionBody, drinkOption)
                            .observeOnUiThread()
                            .subscribe(this::handleCreateDrinkOptionSuccess, this::handleApiError)
                } else {
                    val deleteItems = mutableSetOf<Long>()

                    oldOption.items.forEach {
                        val oldItem = it
                        if (drinkOption.items.filter { it.id == oldItem.id }.isEmpty()) {
                            deleteItems.add(oldItem.id)
                        }
                    }
                    val adds = drinkOption.items.filter {
                        it.id == -1L
                    }
                    val editDrinkOptionBody = EditDrinkOptionBody("", oldOption.id, name, multiChoose, deleteItems, adds.toMutableList())
                    viewModel.editDrinkOption(editDrinkOptionBody)
                            .observeOnUiThread()
                            .subscribe(this::handleCreateDrinkOptionSuccess, this::handleApiError)
                }

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
        hideKeyboard()
        val name = ui.edtDrinkOptionItemName.text.toString().trim()
        val priceString = ui.edtDrinkOptionItemPrice.text.toString().trim()
        var message = ""
        if (name.isValidateStoreName() && name.length < 21) {
            try {
                val price = priceString.toInt()
                if (price >= 0) {
                    if (drinkOption.items.size < 10) {
                        val optionId = if (action == Action.EDIT) {
                            oldOption.id
                        } else {
                            -1L
                        }
                        drinkOption.items.add(DrinkOptionItemBody(optionId, name, price))
                        ui.itemAdapter.notifyDataSetChanged()
                        ui.edtDrinkOptionItemName.setText("")
                        ui.edtDrinkOptionItemPrice.setText("")
                    } else {
                        message = "Số lượng item tối đa là 10."
                    }
                } else {
                    message = "Giá phải là một số nguyên không."
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
                it.popSkip = 1
                it.onBackPressed()
            }
        }
    }

    private fun onDeleteItem(drinkOptionItemBody: DrinkOptionItemBody) {
        drinkOption.items.remove(drinkOptionItemBody)
        ui.itemAdapter.notifyDataSetChanged()
    }

    enum class Action {
        CREATE,
        EDIT
    }
}
