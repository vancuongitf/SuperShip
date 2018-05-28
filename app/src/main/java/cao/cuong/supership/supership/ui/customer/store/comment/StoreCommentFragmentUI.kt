package cao.cuong.supership.supership.ui.customer.store.comment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputFilter
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Comment
import cao.cuong.supership.supership.extension.enableHighLightWhenClicked
import cao.cuong.supership.supership.extension.findLastVisibleItemPosition
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class StoreCommentFragmentUI(comments: MutableList<Comment>) : AnkoComponent<StoreCommentFragment> {

    internal val commentAdapter = CommentAdapter(comments)
    internal lateinit var edtComment: EditText
    internal lateinit var btnComment: TextView
    internal lateinit var imgStarRate: ImageView
    internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    internal lateinit var rlStarButton: RelativeLayout

    override fun createView(ui: AnkoContext<StoreCommentFragment>) = with(ui) {

        verticalLayout {

            backgroundColorResource = R.color.colorWhite
            lparams(matchParent, matchParent)

            toolbar {
                setContentInsetsAbsolute(0, 0)
                backgroundColorResource = R.color.colorGrayLight

                linearLayout {

                    gravity = Gravity.CENTER_VERTICAL

                    relativeLayout {

                        gravity = Gravity.CENTER_VERTICAL
                        enableHighLightWhenClicked()
                        onClick {
                            owner.onBackClicked()
                        }

                        imageView(R.drawable.ic_back_button) {
                        }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                            horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                        }
                    }.lparams(wrapContent, matchParent)

                    textView(R.string.comment) {
                        textSizeDimen = R.dimen.storeTitleTextSize
                        textColorResource = R.color.colorBlue
                    }.lparams(0, wrapContent) {
                        weight = 1f
                    }

                    rlStarButton = relativeLayout {
                        visibility = View.GONE
                        relativeLayout {
                            relativeLayout {

                                gravity = Gravity.CENTER_VERTICAL
                                enableHighLightWhenClicked()
                                onClick {
                                    owner.onStarRateClicked()
                                }

                                imgStarRate = imageView(R.drawable.ic_star_gray) {
                                }.lparams(dimen(R.dimen.backButtonSize), dimen(R.dimen.backButtonSize)) {
                                    horizontalMargin = dimen(R.dimen.accountFragmentLoginPadding)
                                }
                            }.lparams(wrapContent, matchParent)
                        }.lparams(wrapContent, matchParent)
                    }


                }.lparams(matchParent, dimen(R.dimen.toolBarHeight))

            }.lparams(matchParent, wrapContent)

            edtComment = editText {
                backgroundColorResource = R.color.colorGrayLight
                gravity = Gravity.TOP
                horizontalPadding = dip(10)
                filters = arrayOf(InputFilter.LengthFilter(300))
            }.lparams(matchParent, dip(100)) {
                margin = dip(10)
            }

            relativeLayout {
                enableHighLightWhenClicked()

                onClick {
                    owner.onCommentButtonClicked()
                }

                btnComment = textView(R.string.comment) {
                    textColorResource = R.color.colorBlack
                    backgroundColorResource = R.color.colorBlue
                    gravity = Gravity.END
                    padding = dip(10)
                }.lparams(wrapContent, wrapContent) {
                    horizontalMargin = dip(10)
                    bottomMargin = dip(10)
                }
            }

            swipeRefreshLayout = swipeRefreshLayout {

                onRefresh {
                    owner.onRefresh()
                }

                recyclerView {
                    lparams(matchParent, matchParent)
                    layoutManager = LinearLayoutManager(context)
                    adapter = commentAdapter
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recycglerView: RecyclerView?, dx: Int, dy: Int) {
                            owner.loadMore(this@recyclerView.findLastVisibleItemPosition())
                        }
                    })
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
