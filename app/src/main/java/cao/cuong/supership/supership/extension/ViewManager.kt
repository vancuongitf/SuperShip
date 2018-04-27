package cao.cuong.supership.supership.extension

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.ViewManager
import cao.cuong.supership.supership.ui.base.widget.CommonButton
import cao.cuong.supership.supership.ui.base.widget.CommonEditText
import cao.cuong.supership.supership.ui.base.widget.CommonEditTextWithEditButton
import org.jetbrains.anko.custom.ankoView

/**
 *
 * @author at-cuongcao.
 */
internal fun ViewManager.commonEditText(@StringRes title: Int, passwordType: Boolean = false, init: CommonEditText.() -> Unit): CommonEditText =
        ankoView({ CommonEditText(it, title, passwordType) }, 0, init)

internal fun ViewManager.commonButton(@StringRes title: Int, @ColorRes backGroundColor: Int, @DimenRes verticalPadding: Int, init: CommonButton.() -> Unit): CommonButton =
        ankoView({ CommonButton(it, title, backGroundColor, verticalPadding) }, 0, init)

internal fun ViewManager.commonEditTextWithEditButton(@DrawableRes title: Int, onClicked: () -> Unit = {}, init: CommonEditTextWithEditButton.() -> Unit): CommonEditTextWithEditButton =
        ankoView({ CommonEditTextWithEditButton(it, title, onClicked) }, 0, init)
