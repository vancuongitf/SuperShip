package cao.cuong.supership.supership.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.ui.base.BaseFragment

/**
 *
 * @author at-cuongcao.
 */
internal fun Fragment.addChildFragment(@IdRes containerId: Int, fragment: BaseFragment, backStack: String? = null,
                                       t: (transaction: FragmentTransaction) -> Unit = {}) {
    if (childFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = childFragmentManager.beginTransaction()
        t.invoke(transaction)
        transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        transaction.commit()
        if (backStack != null) {
            Log.i("tag1122", "back stack")
            transaction.addToBackStack(backStack)
        }
        childFragmentManager.executePendingTransactions()
        Log.i("tag11223344", childFragmentManager.backStackEntryCount.toString())
    }
}

internal fun Fragment.addChildFragment(@IdRes containerId: Int, fragment: BaseFragment, backStack: String? = null) {
    if (childFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(containerId, fragment, fragment.javaClass.simpleName)
        transaction.commit()
        if (backStack != null) {
            transaction.addToBackStack(backStack)
        }
        childFragmentManager.executePendingTransactions()
    }
}

internal fun Fragment.replaceChildFragment(@IdRes containerId: Int, fragment: BaseFragment, backStack: String? = null,
                                           t: (transaction: FragmentTransaction) -> Unit = {}) {
    val transaction = childFragmentManager.beginTransaction()
    t.invoke(transaction)
    transaction.replace(containerId, fragment, backStack)
    if (backStack != null) {
        transaction.addToBackStack(backStack)
    }
    transaction.commit()
}

internal fun FragmentTransaction.animBotToTop() {
    setCustomAnimations(R.anim.slide_bot_to_top, 0, 0, R.anim.slide_top_to_bot)
}

internal fun FragmentTransaction.animRightToLeft() {
    setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right)
}
