package cao.cuong.supership.supership.ui.order

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.OrderDrink
import cao.cuong.supership.supership.data.model.OrderedDrink
import cao.cuong.supership.supership.data.model.RxEvent.UpdateCartStatus
import cao.cuong.supership.supership.data.source.remote.network.RxBus
import cao.cuong.supership.supership.data.source.remote.request.BillBody
import cao.cuong.supership.supership.extension.*
import cao.cuong.supership.supership.ui.order.cart.CartFragment
import cao.cuong.supership.supership.ui.store.BaseStoreInfoActivity
import cao.cuong.supership.supership.ui.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import org.jetbrains.anko.setContentView
import kotlin.concurrent.thread

class OrderActivity : BaseStoreInfoActivity() {

    internal lateinit var billBody: BillBody
    internal val cart = mutableListOf<OrderDrink>()
    internal val orderedDrinks = mutableListOf<OrderedDrink>()

    private lateinit var ui: OrderActivityUI
    private lateinit var viewModel: OrderActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = OrderActivityUI()
        viewModel = OrderActivityViewModel(this)
        ui.setContentView(this)
        val storeId = intent.extras.getLong(StoreInfoFragment.KEY_STORE_ID)
        replaceFragment(R.id.orderActivityContainer, StoreInfoFragment.getNewInstance(storeId))
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.orderActivityContainer)
            if (currentFragment is StoreInfoFragment) {
                updateCartInfo()
            }
        }
    }

    override fun onBindViewModel() = Unit

    internal fun openDrinkOrderFragment(drink: Drink) {
        addFragment(R.id.orderActivityContainer, DrinkFragment.getNewInstance(drink), { it.animRightToLeft() }, DrinkFragment::class.java.simpleName)
    }

    internal fun openCartFragment() {
        if (cart.isNotEmpty()) {
            val userInfo = viewModel.getUserInfo()
            billBody = BillBody(store.id, "", userInfo?.fullName ?: "", userInfo?.phoneNumber
                    ?: "", null, 0, cart)
            addFragment(R.id.orderActivityContainer, CartFragment(), { it.animRightToLeft() }, CartFragment::class.java.simpleName)
        }
    }

    internal fun openCartDrinkFragment() {

    }

    internal fun drinkOrder(orderDrink: OrderDrink) {
        val old = cart.filter {
            it.isSameWithOther(orderDrink)
        }
        if (old.isNotEmpty()) {
            old.first().count += orderDrink.count
        } else {
            cart.add(orderDrink)
        }
        RxBus.publish(UpdateCartStatus(cart.isNotEmpty()))
    }

    internal fun getOrderedDrink(index: Int): OrderedDrink? {
        if (index in 0 until orderedDrinks.size) {
            return orderedDrinks[index]
        }
        return null
    }

    private fun updateCartInfo() {
        thread {
            orderedDrinks.clear()
            cart.forEach {
                val orderDrink = it
                val drinks = store.menu.filter {
                    it.id == orderDrink.drinkId
                }
                if (drinks.isNotEmpty()) {
                    val drink = drinks.first()
                    var price = drink.price
                    val drinkOptionItemIds = orderDrink.optionals.getDrinkOptionItemIds()
                    val storeOptionItems = store.options.getDrinkOptionItems()
                    drinkOptionItemIds.forEach {
                        val id = it
                        storeOptionItems.find { it.id == id }?.let {
                            price += it.price
                        }
                    }
                    orderDrink.optionals
                    val orderedDrink = OrderedDrink(drink.id, drink.name, price, drink.image, orderDrink.count, orderDrink.note, store.options.getOrderedOptions(orderDrink.optionals))
                    orderedDrinks.add(orderedDrink)
                }
            }
        }
    }

    internal fun getCartPrice() = orderedDrinks.sumBy { it.price * it.count }
}
