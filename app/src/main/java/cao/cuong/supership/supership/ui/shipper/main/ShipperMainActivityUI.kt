package cao.cuong.supership.supership.ui.shipper.main

import android.support.design.widget.TabLayout
import android.view.Gravity
import android.view.ViewManager
import android.widget.ImageView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.ui.base.widget.NonSwipeAbleViewPager
import cao.cuong.supership.supership.ui.base.widget.nonSwipeAbleViewPager
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout

class ShipperMainActivityUI(val mainTabs: List<ShipperMainTab>) : AnkoComponent<ShipperMainActivity> {
    companion object {
        internal const val OFFSCREEN_PAGE_LIMIT = 2
    }

    internal lateinit var viewPager: NonSwipeAbleViewPager
    private lateinit var tabLayout: TabLayout
    private val listIcon = mutableListOf<ImageView>()
    internal lateinit var mainTabAdapter: cao.cuong.supership.supership.ui.shipper.main.MainPagerAdapter

    override fun createView(ui: AnkoContext<ShipperMainActivity>) = with(ui) {
        verticalLayout {

            backgroundColorResource = R.color.colorWhite

            lparams(matchParent, matchParent)

            viewPager = nonSwipeAbleViewPager {
                id = R.id.mainActivityViewPager
                offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
                mainTabAdapter = MainPagerAdapter(owner.supportFragmentManager, mainTabs)
                adapter = mainTabAdapter
            }.lparams(matchParent, 0) {
                weight = 1f
            }

            view {
                backgroundResource = R.color.colorRed
                alpha = 0.7f
            }.lparams(matchParent, dip(1))

            tabLayout = tabLayout {
                id = R.id.mainActivityTabLayout
                tabMode = TabLayout.MODE_FIXED
                setupWithViewPager(viewPager)
                setSelectedTabIndicatorHeight(0)
                backgroundColorResource = R.color.colorWhite
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        tab?.let {
                            listIcon[it.position].setImageResource(mainTabs[it.position].itemType.icon)
                        }
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        tab?.let {
                            listIcon[it.position].setImageResource(mainTabs[it.position].itemType.iconRed)
                        }
                    }
                })
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            mainTabs.withIndex().forEach {
                val tab = tabLayout.getTabAt(it.index)
                tab?.customView = tabItem(it.index)
            }
        }
    }

    private fun ViewManager.tabItem(pos: Int) = relativeLayout {
        gravity = Gravity.CENTER
        lparams(matchParent, matchParent)
        backgroundDrawable = null
        if (mainTabs[pos].itemType.isSelected) {
            listIcon.add(pos, imageView(mainTabs[pos].itemType.iconRed).lparams(dimen(R.dimen.tabLayoutItemWidth), dimen(R.dimen.tabLayoutItemWidth)))
        } else {
            listIcon.add(pos, imageView(mainTabs[pos].itemType.icon).lparams(dimen(R.dimen.tabLayoutItemWidth), dimen(R.dimen.tabLayoutItemWidth)))
        }
    }
}
