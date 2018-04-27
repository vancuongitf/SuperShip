package cao.cuong.supership.supership.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import android.widget.FrameLayout
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.showOkAlert
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 *
 * @author at-cuongcao.
 */
abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var behavior: BottomSheetBehavior<View>
    private val subscription: CompositeDisposable = CompositeDisposable()
    internal lateinit var progressDialog: ProgressDialog
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED;
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view!!.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(android.support.design.R.id.design_bottom_sheet) as FrameLayout?
            behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 0
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        initDialog()
    }

    override fun onResume() {
        super.onResume()
        onBindViewModel()
    }

    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    abstract fun onBindViewModel()

    protected fun handleUpdateProgressStatus(status: Boolean) {
        if (status) {
            progressDialog.show()
        } else {
            progressDialog.cancel()
        }
    }

    protected fun handleApiError(throwable: Throwable?) {
        context.showOkAlert(throwable ?: Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
    }

    private fun initDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(context.getString(R.string.processing))
    }
}
