package cao.cuong.supership.supership.ui.staff.main

import android.graphics.Color
import android.support.design.widget.TabLayout
import android.widget.ImageView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.ui.base.widget.NonSwipeAbleViewPager
import cao.cuong.supership.supership.ui.base.widget.nonSwipeAbleViewPager
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout

/**
 *
 * @author at-cuongcao.
 */
class StaffMainActivityUI(val mainTabs: List<StaffMainTab>) : AnkoComponent<StaffMainActivity> {

    companion object {
        internal const val OFFSCREEN_PAGE_LIMIT = 2
    }

    internal lateinit var viewPager: NonSwipeAbleViewPager
    private lateinit var tabLayout: TabLayout
    private val listIcon = mutableListOf<ImageView>()
    internal lateinit var mainTabAdapter: StaffMainPagerAdapter

    override fun createView(ui: AnkoContext<StaffMainActivity>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            viewPager = nonSwipeAbleViewPager {
                id = R.id.mainActivityViewPager
                offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
                mainTabAdapter = StaffMainPagerAdapter(owner.supportFragmentManager, mainTabs)
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
                setTabTextColors(Color.BLACK, Color.RED)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            mainTabs.withIndex().forEach {
                val mainTab = it
                val tab = tabLayout.getTabAt(it.index)
                tab?.let {
                    it.text = mainTab.value.getLabel()
                }
            }
        }
    }

//    private fun ViewManager.tabItem(pos: Int) = relativeLayout {
//        gravity = Gravity.CENTER
//        lparams(matchParent, matchParent)
//        backgroundDrawable = null
//        if (mainTabs[pos].itemType.isSelected) {
//            listIcon.add(pos, imageView(mainTabs[pos].itemType.iconRed).lparams(dimen(R.dimen.tabLayoutItemWidth), dimen(R.dimen.tabLayoutItemWidth)))
//        } else {
//            listIcon.add(pos, imageView(mainTabs[pos].itemType.icon).lparams(dimen(R.dimen.tabLayoutItemWidth), dimen(R.dimen.tabLayoutItemWidth)))
//        }
//    }
}
