package cao.cuong.supership.supership.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cao.cuong.supership.supership.App
import cao.cuong.supership.supership.data.model.rxevent.UnAuthorizeException
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.extension.observeOnUiThread
import cao.cuong.supership.supership.ui.user.UserActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author at-cuongcao.
 */
abstract class BaseActivity : AppCompatActivity() {
    private val subscription: CompositeDisposable = CompositeDisposable()
    private lateinit var viewModel: BaseActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        (applicationContext as? App)?.currentActivity = this
        onBindViewModel()
    }

    internal fun startUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivityForResult(intent, UserActivity.USER_ACTIVITY_REQUEST_CODE)
    }

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    private fun isCurrentActivity() = this == (applicationContext as? App)?.currentActivity

    /**
     * This function is used to define subscription
     */
    abstract fun onBindViewModel()
}
