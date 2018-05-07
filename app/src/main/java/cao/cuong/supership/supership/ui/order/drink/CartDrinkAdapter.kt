package cao.cuong.supership.supership.ui.order.drink

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cao.cuong.supership.supership.ui.order.drink.item.OrderedDrinkFragment

class CartDrinkAdapter(supportFragmentManager: FragmentManager, private val size: Int) : FragmentPagerAdapter(supportFragmentManager) {
    override fun getItem(position: Int) = OrderedDrinkFragment.getNewInstance(position)

    override fun getCount() = size
}
