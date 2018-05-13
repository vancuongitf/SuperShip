package cao.cuong.supership.supership.ui.customer.store.drink.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.model.rxevent.BackPressEvent
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkBody
import cao.cuong.supership.supership.data.source.remote.request.EditDrinkBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseActivity
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.customer.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.customer.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.customer.store.info.StoreInfoFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.jetbrains.anko.AnkoContext

class CreateDrinkFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeId: Long, drink: Drink? = null): CreateDrinkFragment {
            val instance = CreateDrinkFragment()
            instance.arguments = Bundle().apply {
                putLong(StoreInfoFragment.KEY_STORE_ID, storeId)
                if (drink != null) {
                    putSerializable(DrinkFragment.KEY_DRINK, drink)
                }
            }
            return instance
        }
    }

    internal lateinit var ui: CreateDrinkFragmentUI
    internal lateinit var viewModel: CreateDrinkFragmentViewModel
    private var storeId = -1L
    private var uri: Uri? = null
    private val options = mutableListOf<DrinkOption>()
    private var action: Action = Action.CREATE
    private var drink: Drink? = null
    private val oldOptions = mutableSetOf<Long>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(StoreInfoFragment.KEY_STORE_ID)
        (activity as? StoreActivity)?.getDrinkOptions()?.let {
            options.addAll(it)
        }
        viewModel = CreateDrinkFragmentViewModel(context)
        ui = CreateDrinkFragmentUI(options)
        ui.optionsAdapter.onItemAction = { view, drinkOption ->
            when (view.id) {
                R.id.imgCancelOption -> {
                    drinkOption.isSelected = false
                    ui.optionsAdapter.notifyDataSetChanged()
                }

                R.id.imgApplyDrinkOption -> {
                    drinkOption.isSelected = true
                    ui.optionsAdapter.notifyDataSetChanged()
                }
            }
        }
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments.containsKey(DrinkFragment.KEY_DRINK)) {
            action = Action.EDIT
            drink = arguments.getSerializable(DrinkFragment.KEY_DRINK) as? Drink
            drink?.let {
                oldOptions.addAll(it.options)
                val drink = it
                options.forEach {
                    if (drink.options.contains(it.id)) {
                        it.isSelected = true
                    }
                }
                ui.optionsAdapter.notifyDataSetChanged()
                val option = RequestOptions()
                        .placeholder(R.drawable.glide_place_holder)
                ui.rlAvatar.visibility = View.VISIBLE
                Glide.with(context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load("https://vnshipperman.000webhostapp.com/uploads/${drink.image}")
                        .into(ui.imgAvatar)
                ui.edtName.setText(drink.name)
                ui.edtPrice.setText(drink.price.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    val resultUri = result.uri
                    resultUri?.let {
                        uri = it
                        ui.imgAvatar.setImageURI(uri)
                        ui.rlAvatar.visibility = View.VISIBLE
                    }
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            val error = result.error
            handleApiError(Throwable(error.cause))
        }
    }


    internal fun onBackClicked(rxEvent: BackPressEvent? = null) {
        activity.onBackPressed()
    }

    internal fun addDrinkClicked() {
        (activity as? BaseActivity)?.hideKeyBoard()
        val name = ui.edtName.text.toString().trim()
        val priceString = ui.edtPrice.text.toString()
        var message = ""
        if (uri != null || action == Action.EDIT) {
            if (name.isValidateFullName()) {
                try {
                    val price = priceString.toInt()
                    if (price > 0) {
                        val set = mutableSetOf<Long>()
                        options.forEach {
                            if (it.isSelected) {
                                set.add(it.id)
                            }
                        }
                        if (action == Action.CREATE) {
                            viewModel.createDrink(uri!!, CreateDrinkBody(null, storeId, name, name.unAccent(), price, "", set))
                                    .observeOnUiThread()
                                    .subscribe(this::handleCreateDrinkSuccess, this::handleApiError)
                        } else {
                            val addOptions = set.filter {
                                !(drink?.options?.contains(it) ?: true)
                            }.toSet()
                            val deleteOptions = mutableSetOf<Long>()
                            drink?.let {
                                deleteOptions.addAll(it.options.filter {
                                    !set.contains(it)
                                }.toSet())
                            }

                            if (isSameBefore(name, price, addOptions, deleteOptions)) {
                                context.showConfirmAlert(R.string.nothingChange) {
                                    activity.onBackPressed()
                                }
                            } else {
                                drink?.let {
                                    val editDrinkBody = EditDrinkBody("", it.id, name, name.unAccent(), price, it.image, addOptions, deleteOptions)
                                    viewModel.editDrink(uri, editDrinkBody)
                                            .subscribe(this::handleEditDrinkSuccess, this::handleApiError)
                                }

                            }
                        }
                    } else {
                        message = "Giá phải là một số nguyên dương."
                    }
                } catch (e: NumberFormatException) {
                    message = "Vui lòng nhập giá đúng định dạng."
                }
            } else {
                message = "Vui lòng nhập tên đồ uống đúng định dạng."
            }
        } else {
            message = "Vui lòng chọn ảnh cho đồ uống."
        }
        if (message.isNotEmpty()) {
            handleApiError(Throwable(message))
        }
    }

    internal fun chooseAvatar() {
        CropImage.activity(null)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(context, this);
    }

    private fun handleCreateDrinkSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            (activity as? StoreActivity)?.shouldReload = true
            activity.onBackPressed()
        }
    }

    private fun handleEditDrinkSuccess(messageResponse: MessageResponse) {
        context.showOkAlert(R.string.notification, messageResponse.message) {
            (activity as? StoreActivity)?.let {
                it.shouldReload = true
                it.popSkip = 1
                it.onBackPressed()
            }
        }
    }

    private fun isSameBefore(newName: String, newPrice: Int, addOptions: Set<Long>, deleteOptions: Set<Long>) = uri == null
            && newName == drink?.name
            && newPrice == drink?.price
            && addOptions.isEmpty()
            && deleteOptions.isEmpty()

    enum class Action {
        CREATE,
        EDIT
    }
}
