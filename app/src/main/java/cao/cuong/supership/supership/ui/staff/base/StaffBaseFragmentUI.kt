package cao.cuong.supership.supership.ui.staff.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.util.concurrent.TimeUnit

class StaffBaseFragmentUI : AnkoComponent<StaffBaseFragment> {

    internal var onTextChange: (String) -> Unit = {}
    internal var onChangeListStatusClicked: (Int) -> Unit = {}
    internal lateinit var edtSearch: EditText
    internal lateinit var recyclerViewBills: RecyclerView
    internal lateinit var tvTitle: TextView
    internal lateinit var llSearchView: LinearLayout
    internal lateinit var llBillStatusChange: LinearLayout
    internal lateinit var imgWaiting: ImageView
    internal lateinit var imgChecked: ImageView
    internal lateinit var imgTransit: ImageView
    internal lateinit var imgDone: ImageView
    internal lateinit var llUserStatusChange: LinearLayout
    internal lateinit var imgUserWaiting: ImageView
    internal lateinit var imgUserChecked: ImageView
    internal lateinit var imgUserBanned: ImageView
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val searchObservable = PublishSubject.create<String>()

    init {
        searchObservable
                .observeOn(Schedulers.computation())
                .debounce(400, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe({
                    it?.let {
                        if (it.isNotEmpty()) {
                            onTextChange(it)
                        }
                    }
                })
    }

    override fun createView(ui: AnkoContext<StaffBaseFragment>) = with(ui) {
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColorResource = R.color.colorWhite

            toolbar {
                backgroundColorResource = R.color.colorGrayVeryLight
                setContentInsetsAbsolute(0, 0)

                verticalLayout {

                    linearLayout {
                        tvTitle = textView {
                            gravity = Gravity.CENTER_VERTICAL
                            textSizeDimen = R.dimen.storeTitleTextSize
                            textColorResource = R.color.colorBlack
                        }.lparams(0, dimen(R.dimen.toolBarHeight)) {
                            leftMargin = dip(20)
                            weight = 1f
                        }

                        llUserStatusChange = linearLayout {

                            visibility = View.GONE

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(0)
                                }

                                imgUserWaiting = imageView(R.drawable.ic_user_waiting_red) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(1)
                                }

                                imgUserChecked = imageView(R.drawable.ic_user_checked_black) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(2)
                                }

                                imgUserBanned = imageView(R.drawable.ic_banned_user_black) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)
                        }.lparams(wrapContent, matchParent)

                        llBillStatusChange = linearLayout {

                            visibility = View.GONE

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(0)
                                }

                                imgWaiting = imageView(R.drawable.ic_waiting_red) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(1)
                                }

                                imgChecked = imageView(R.drawable.ic_check_list) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(2)
                                }

                                imgTransit = imageView(R.drawable.ic_transit) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                enableHighLightWhenClicked()
                                onClick {
                                    onChangeListStatusClicked(3)
                                }

                                imgDone = imageView(R.drawable.ic_done) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)
                        }.lparams(wrapContent, matchParent)
                    }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {

                    }

                    llSearchView = linearLayout {
                        backgroundResource = R.drawable.tool_bar_bg
                        gravity = Gravity.CENTER_VERTICAL
                        leftPadding = dimen(R.dimen.toolBarLeftPadding)

                        imageView(R.drawable.ic_search_black_36dp)
                                .lparams(dimen(R.dimen.toolBarSearchIconSize), dimen(R.dimen.toolBarSearchIconSize))

                        edtSearch = editText {
                            backgroundColorResource = R.color.colorWhite
                            hint = "Tìm kiếm..."
                            addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                    s?.let {
                                        val text = it.toString().trim()
                                        if (text.isNotEmpty()) {
                                            searchObservable.onNext(text)
                                        }
                                    }
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
                            })
                        }.lparams(matchParent, matchParent) {
                            leftMargin = dimen(R.dimen.toolBarLeftPadding)
                        }
                    }.lparams(matchParent, dimen(R.dimen.toolBarHeight)) {
                        margin = dip(5)
                    }
                }.lparams(matchParent, wrapContent)

            }.lparams(matchParent, wrapContent)
            swipeRefreshLayout = swipeRefreshLayout {
                recyclerViewBills = recyclerView {
                    id = R.id.recyclerStaff
                    lparams(matchParent, matchParent)
                    layoutManager = LinearLayoutManager(ctx)
                }

                onRefresh {
                    owner.onRefresh()
                }
            }.lparams(matchParent, matchParent)
        }
    }
}

