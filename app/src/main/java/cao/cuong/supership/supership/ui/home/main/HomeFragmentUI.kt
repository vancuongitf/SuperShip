package cao.cuong.supership.supership.ui.home.main

import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Gravity
import cao.cuong.supership.supership.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager
import cao.cuong.supership.supership.ui.main.MainActivityUI

/**
 *
 * @author at-cuongcao.
 */
class HomeFragmentUI(private val fragmentAdapter: HomeFragmentAdapter) : AnkoComponent<HomeFragment> {

    internal lateinit var viewPager: ViewPager
    internal lateinit var tabLayout: TabLayout

    override fun createView(ui: AnkoContext<HomeFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)

            toolbar {
                backgroundColorResource = R.color.colorGrayVeryLight
                padding = dimen(R.dimen.toolBarPadding)
                setContentInsetsAbsolute(0, 0)
                onClick {
                    owner.handleSearchViewClicked()
                }

                linearLayout {
                    backgroundResource = R.drawable.tool_bar_bg
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dimen(R.dimen.toolBarLeftPadding)

                    imageView(R.drawable.ic_search_black_36dp)
                            .lparams(dimen(R.dimen.toolBarSearchIconSize), dimen(R.dimen.toolBarSearchIconSize))

                    textView(R.string.searchTitle)
                            .lparams {
                                leftMargin = dimen(R.dimen.toolBarLeftPadding)
                            }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            tabLayout = tabLayout {
                id = R.id.mainActivityTabLayout
                tabMode = TabLayout.MODE_FIXED
                setSelectedTabIndicatorHeight(0)
                backgroundColorResource = R.color.colorWhite
            }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            viewPager = viewPager {
                id = R.id.homeFragmentViewPager
                offscreenPageLimit = MainActivityUI.OFFSCREEN_PAGE_LIMIT
                adapter = fragmentAdapter
            }.lparams(matchParent, matchParent) {
                topMargin = dip(2)
            }

            tabLayout.setupWithViewPager(viewPager)
            tabLayout.setTabTextColors(ContextCompat.getColor(ctx, R.color.colorGray), ContextCompat.getColor(ctx, R.color.colorRed))
        }
    }
}
