package cao.cuong.supership.supership

import android.app.Application
import cao.cuong.supership.supership.ui.base.BaseActivity

class App : Application() {
    internal var currentActivity: BaseActivity? = null
}
