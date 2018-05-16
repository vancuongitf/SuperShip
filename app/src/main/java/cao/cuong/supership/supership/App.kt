package cao.cuong.supership.supership

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import cao.cuong.supership.supership.ui.base.BaseActivity

class App : Application() {
    internal var currentActivity: BaseActivity? = null
    private lateinit var viewModel: AppViewModel

    private val mReceive = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.submitCurrentLocation()
        }

    }

    override fun onCreate() {
        super.onCreate()
        viewModel = AppViewModel(this)
        registerReceiver(mReceive, IntentFilter(Intent.ACTION_TIME_TICK))
    }
}
