package cao.cuong.supership.supership.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import cao.cuong.supership.supership.data.model.RxEvent.UnAuthorizeException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author at-cuongcao.
 */
abstract class BaseActivity : AppCompatActivity() {
    private val subscription: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: BaseActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewModel = BaseActivityViewModel(this)
        RxBus.listen(UnAuthorizeException::class.java)
                .observeOnUiThread()
                .subscribe({
                    viewModel.clearAccessToken()
                }, {})
    }
    override fun onPause() {
        super.onPause()
        subscription.clear()
    }

    override fun onResume() {
        super.onResume()
        onBindViewModel()
    }

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    /**
     * This function is used to define subscription
     */
    abstract fun onBindViewModel()
}