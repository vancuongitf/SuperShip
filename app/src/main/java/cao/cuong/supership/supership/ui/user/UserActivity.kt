package cao.cuong.supership.supership.ui.user

import android.os.Bundle
import cao.cuong.supership.supership.ui.base.BaseActivity
import org.jetbrains.anko.setContentView

class UserActivity : BaseActivity() {

    private lateinit var ui: UserActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = UserActivityUI()
        ui.setContentView(this)
    }

    override fun onBindViewModel() {

    }
}
