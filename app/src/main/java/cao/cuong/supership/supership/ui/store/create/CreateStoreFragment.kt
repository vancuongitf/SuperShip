package cao.cuong.supership.supership.ui.store.create

import android.app.Activity.RESULT_OK
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OpenHour
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.data.source.remote.request.CreateStoreBody
import cao.cuong.supership.supership.data.source.remote.response.MessageResponse
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import cao.cuong.supership.supership.ui.store.activity.StoreActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert

class CreateStoreFragment : BaseFragment() {

    companion object {
        private const val REQUEST_CODE_SEARCH_LOCATION = 22
    }

    private lateinit var ui: CreateStoreFragmentUI
    private var uri: Uri? = null
    private var openHour = -1
    private var openMin = -1
    private var closeHour = -1
    private var closeMin = -1
    private var openTime = -1
    private var closeTime = -1
    private var address: StoreAddress? = null
    private lateinit var viewModel: CreateStoreFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = CreateStoreFragmentViewModel(context)
        ui = CreateStoreFragmentUI()
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
        if (resultCode == RESULT_OK) {
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

                REQUEST_CODE_SEARCH_LOCATION -> {
                    address = data?.extras?.getParcelable<StoreAddress>(LocationActivity.KEY_ADDRESS_RESULT)
                    address?.let {
                        ui.tvAddress.text = it.address
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

    internal fun chooseAvatar() {
        CropImage.activity(null)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(context, this);
    }

    internal fun chooseOpenTime() {
        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            openHour = hourOfDay
            openMin = minute
            openTime = openHour * 60 + openMin
            ui.tvOpenTime.text = "${openHour.numberToString()} : ${openMin.numberToString()}"
        }, 0, 0, true)
        timePicker.show()
    }

    internal fun chooseCloseTime() {
        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            closeHour = hourOfDay
            closeMin = minute
            closeTime = closeHour * 60 + closeMin
            ui.tvCloseTime.text = "${closeHour.numberToString()} : ${closeMin.numberToString()}"
        }, 0, 0, true)
        timePicker.show()
    }

    internal fun onChooseAddressClicked() {
        val intent = Intent(activity, LocationActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SEARCH_LOCATION)
    }

    internal fun addStoreClicked() {
        var message = ""
        val name = ui.edtName.text.toString().trim()
        val unAccentName = name.unAccent()
        val phone = ui.edtPhone.text.toString()
        val email = ui.edtEmail.text.toString()
        val openDays = mutableSetOf<Int>()
        ui.checkBoxlist.forEachIndexed { index, checkBox ->
            if (checkBox.isChecked) {
                openDays.add(index)
            }
        }

        if (uri != null) {
            if (name.isValidateFullName()) {
                if (address != null) {
                    if (phone.isValidatePhoneNumber()) {
                        if (email.isValidateEmail()) {
                            if (openDays.isNotEmpty()) {
                                if (openTime < 0 || closeTime < 0 || openTime == closeTime || openTime > closeTime) {
                                    message = "Vui lòng chọn giờ mở/đóng cửa hợp lệ."
                                } else {
                                    val createStoreBody = CreateStoreBody("", name, unAccentName, address!!.address, address!!.latLng, phone, email, "", OpenHour(openDays.toMutableList(), openTime, closeTime))
                                    createStore(createStoreBody)
                                }
                            } else {
                                message = "Vui lòng chọn ngày mở cửa."
                            }
                        } else {
                            message = "Email không hợp lệ."
                        }
                    } else {
                        message = "Số điện thoại không hợp lệ."
                    }
                } else {
                    message = "Vui lòng chọn địa chỉ cho cửa hàng."
                }
            } else {
                message = "Tên cửa hàng không hợp lệ."
            }
        } else {
            message = "Vui lòng chọn ảnh đại diện cho cửa hàng."
        }
        if (message.isNotEmpty()) {
            val error = Throwable(message)
            handleApiError(error)
        }
    }

    private fun createStore(createStoreBody: CreateStoreBody) {
        uri?.let {
            viewModel.createStore(it, createStoreBody)
                    .observeOnUiThread()
                    .subscribe(this::handleCreateStoreSuccess, this::handleApiError)
        }
    }

    private fun handleCreateStoreSuccess(messageResponse: MessageResponse) {
        alert {
            title = context.getString(R.string.notification)
            message = messageResponse.message
            isCancelable = false

            okButton {
                it.dismiss()
                (activity as? StoreActivity)?.let {
                    it.shouldReload = true
                    it.onBackPressed()
                }
            }
        }.show()
    }
}
