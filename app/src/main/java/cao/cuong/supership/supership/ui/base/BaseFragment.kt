package cao.cuong.supership.supership.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.showOkAlert
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 *
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var progressDialog: ProgressDialog
    private val subscription: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(context.getString(R.string.processing))
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

    protected fun handleApiError(throwable: Throwable?) {
        context.showOkAlert(throwable ?: Throwable("Xãy ra lỗi! Vui lòng thử lại sau."))
    }

    protected fun handleUpdateProgressDialogStatus(status: Boolean) {
        if (status) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    /**
     * This function is used to define subscription
     */
    abstract fun onBindViewModel()
}
