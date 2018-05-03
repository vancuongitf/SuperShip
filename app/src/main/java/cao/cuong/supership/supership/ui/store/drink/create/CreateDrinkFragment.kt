package cao.cuong.supership.supership.ui.store.drink.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.data.source.remote.request.CreateDrinkBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.isValidateFullName
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.extension.showOkAlert
import cao.cuong.supership.supership.extension.unAccent
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.jetbrains.anko.AnkoContext

class CreateDrinkFragment : BaseFragment() {

    companion object {

        internal fun getNewInstance(storeId: Long): CreateDrinkFragment {
            val instance = CreateDrinkFragment()
            instance.arguments = Bundle().apply {
                putLong(StoreInfoFragment.KEY_STORE_ID, storeId)
            }
            return instance
        }
    }

    internal lateinit var ui: CreateDrinkFragmentUI
    internal lateinit var viewModel: CreateDrinkFragmentViewModel
    private var storeId = -1L
    private var uri: Uri? = null
    private val options = mutableListOf<DrinkOption>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storeId = arguments.getLong(StoreInfoFragment.KEY_STORE_ID)
        (activity as? StoreActivity)?.getDrinkOptions()?.let {
            options.addAll(it)
        }
        viewModel = CreateDrinkFragmentViewModel(context)
        ui = CreateDrinkFragmentUI(options)
        return ui.createView(AnkoContext.Companion.create(context, this))
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


    internal fun onBackClicked() {
        activity.onBackPressed()
    }

    internal fun addDrinkClicked() {
        val name = ui.edtName.text.toString().trim()
        val priceString = ui.edtPrice.text.toString()
        var message = ""
        if (uri != null) {
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
                        viewModel.createDrink(uri!!, CreateDrinkBody(null, storeId, name, name.unAccent(), price, "", set))
                                .observeOnUiThread()
                                .subscribe(this::handleCreateDrinkSuccess, this::handleApiError)
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
}
