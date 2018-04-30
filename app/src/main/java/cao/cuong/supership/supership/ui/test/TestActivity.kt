package cao.cuong.supership.supership.ui.test

import android.app.Activity
import cao.cuong.supership.supership.ui.base.BaseActivity
import android.content.Intent
import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import android.R.attr.data
import android.os.Bundle
import android.util.Log
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StoreRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.jetbrains.anko.setContentView
import java.io.File

class TestActivity : BaseActivity() {

    private var uri: Uri? = null

    private lateinit var ui: TestActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = TestActivityUI()
        ui.setContentView(this)
    }

    override fun onBindViewModel() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK){
//            when(requestCode){
//                22 -> {
//                    uri = data?.data
//                }
//
//                2 -> {
//
//                }
//            }
//        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                val x = StoreRepository()
                x.uploadImage(File(resultUri.path))
                        .observeOnUiThread()
                        .subscribe({
                            Glide.with(this)
                                    .load("http://vnshipperman.000webhostapp.com/uploads/${it.message}" )
                                    .into(ui.img)
                            Log.i("tag11", it.message)
                        }, {
                            Log.i("tag11", Gson().toJson(it))
                        })
                Log.i("tag11", "ok")
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.i("tag11", Gson().toJson(error))
            }
        }
    }

    fun chon() {
        CropImage.activity(null)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, 22)
    }

    fun crop() {

    }


}