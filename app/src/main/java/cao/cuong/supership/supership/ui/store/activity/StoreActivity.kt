package cao.cuong.supership.supership.ui.store.activity

import android.os.Bundle
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Drink
import cao.cuong.supership.supership.data.model.DrinkOption
import cao.cuong.supership.supership.extension.addFragment
import cao.cuong.supership.supership.extension.animRightToLeft
import cao.cuong.supership.supership.extension.replaceFragment
import cao.cuong.supership.supership.ui.store.BaseStoreInfoActivity
import cao.cuong.supership.supership.ui.store.drink.create.CreateDrinkFragment
import cao.cuong.supership.supership.ui.store.drink.info.DrinkFragment
import cao.cuong.supership.supership.ui.store.edit.EditStoreInfoFragment
import cao.cuong.supership.supership.ui.store.info.StoreInfoFragment
import cao.cuong.supership.supership.ui.store.list.StoreListFragment
import cao.cuong.supership.supership.ui.store.optional.add.AddDrinkOptionFragment
import cao.cuong.supership.supership.ui.store.optional.list.OptionalFragment
import org.jetbrains.anko.setContentView

class StoreActivity : BaseStoreInfoActivity() {

    internal var popSkip = 0
    private lateinit var ui: StoreActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = StoreActivityUI()
        ui.setContentView(this)
        replaceFragment(R.id.storeActivityContainer, StoreListFragment())
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.storeActivityContainer)
            if (shouldReload) {
                when (currentFragment) {
                    is StoreListFragment -> {
                        shouldReload = false
                        currentFragment.reloadData()
                    }
                    is StoreInfoFragment -> {
                        shouldReload = false
                        currentFragment.reloadData()
                    }
                }
            }
        }
    }

    override fun onBindViewModel() = Unit

    override fun onBackPressed() {
        super.onBackPressed()
        while (popSkip > 0) {
            popSkip--
            supportFragmentManager.popBackStackImmediate()
        }
    }

    internal fun openStoreInfoFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, StoreInfoFragment.getNewInstance(storeId), { it.animRightToLeft() }, StoreInfoFragment::class.java.simpleName)
    }

    internal fun openCreateDrinkFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, CreateDrinkFragment.getNewInstance(storeId), { it.animRightToLeft() }, StoreInfoFragment::class.java.simpleName)
    }

    internal fun openDrinkOptionFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, OptionalFragment.getNewInstance(storeId), { it.animRightToLeft() }, OptionalFragment::class.java.simpleName)
    }

    internal fun openAddDrinkOptionFragment(storeId: Long) {
        addFragment(R.id.storeActivityContainer, AddDrinkOptionFragment.getNewInstance(storeId), { it.animRightToLeft() }, AddDrinkOptionFragment::class.java.simpleName)
    }

    internal fun openDrinkFragment(drink: Drink) {
        addFragment(R.id.storeActivityContainer, DrinkFragment.getNewInstance(drink), { it.animRightToLeft() }, DrinkFragment::class.java.simpleName)
    }

    internal fun openEditDrinkFragment(drink: Drink) {
        addFragment(R.id.storeActivityContainer, CreateDrinkFragment.getNewInstance(-1L, drink), { it.animRightToLeft() }, "Edit-" + CreateDrinkFragment::class.java.simpleName)
    }

    internal fun openEditDrinkOptionFragment(drinkOption: DrinkOption) {
        addFragment(R.id.storeActivityContainer, AddDrinkOptionFragment.getNewInstance(-1, drinkOption), { it.animRightToLeft() }, "Edit-${AddDrinkOptionFragment::class.java.simpleName}")
    }

    internal fun openEditStoreInfoFragment() {
        addFragment(R.id.storeActivityContainer, EditStoreInfoFragment(), { it.animRightToLeft() }, EditStoreInfoFragment::class.java.simpleName)
    }
}
