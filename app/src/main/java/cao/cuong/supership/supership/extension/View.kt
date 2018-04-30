package cao.cuong.supership.supership.extension

import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import cao.cuong.supership.supership.R

/**
 *
 * @author at-cuongcao.
 */
/**
 * Enable High Light When Clicked
 */
internal fun View.enableHighLightWhenClicked() {
    val originAlphaAnimation = AnimationUtils.loadAnimation(context, R.anim.high_light_fade_in)
    val lessAlphaAnimation = AnimationUtils.loadAnimation(context, R.anim.high_light_fade_out)

    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            v.startAnimation(lessAlphaAnimation)
        } else if (event.action == MotionEvent.ACTION_UP) {
            v.startAnimation(originAlphaAnimation)
        }
        false
    }
}
