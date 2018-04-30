package cao.cuong.supership.supership.ui.store.add

import android.app.Activity.RESULT_OK
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.data.model.google.StoreAddress
import cao.cuong.supership.supership.extension.numberToString
import cao.cuong.supership.supership.ui.base.BaseFragment
import cao.cuong.supership.supership.ui.location.LocationActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.jetbrains.anko.AnkoContext

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = CreateStoreFragmentUI()
        return ui.createView(AnkoContext.Companion.create(context, this))
    }

    override fun onBindViewModel() {

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
                    val address = data?.extras?.getParcelable<StoreAddress>(LocationActivity.KEY_ADDRESS_RESULT)
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

            ui.tvOpenTime.text = "${openHour.numberToString()} : ${openMin.numberToString()}"
        }, 0, 0, true)
        timePicker.show()
    }

    internal fun chooseCloseTime() {
        val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            closeHour = hourOfDay
            closeMin = minute

            ui.tvCloseTime.text = "${closeHour.numberToString()}:${closeMin.numberToString()}"
        }, 0, 0, true)
        timePicker.show()
    }

    internal fun onChooseAddressClicked() {
        val intent = Intent(activity, LocationActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SEARCH_LOCATION)
    }

    internal fun addStoreClicked() {

    }
}
