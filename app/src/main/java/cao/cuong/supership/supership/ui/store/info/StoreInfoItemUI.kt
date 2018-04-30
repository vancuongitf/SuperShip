package cao.cuong.supership.supership.ui.store.info

import android.media.Image
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.extension.getWidthScreen
import org.jetbrains.anko.*

class StoreInfoItemUI : AnkoComponent<ViewGroup> {

    internal lateinit var imgAvatar: ImageView
    internal lateinit var tvStoreAddress: TextView
    internal lateinit var tvStorePhone: TextView
    internal lateinit var tvStarRate: TextView
    internal lateinit var tvOpenTime: TextView
    internal lateinit var imgOpenStatus: ImageView
    internal lateinit var checkBox0: CheckBox
    internal lateinit var checkBox1: CheckBox
    internal lateinit var checkBox2: CheckBox
    internal lateinit var checkBox3: CheckBox
    internal lateinit var checkBox4: CheckBox
    internal lateinit var checkBox5: CheckBox
    internal lateinit var checkBox6: CheckBox

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            val screenWith = ctx.getWidthScreen()
            lparams(matchParent, wrapContent)

            imgAvatar = imageView {
                backgroundResource = R.mipmap.ic_launcher_round
            }.lparams(screenWith, screenWith)

            tvStoreAddress = textView {
                textColorResource = R.color.colorBlack
            }.lparams {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            tvStorePhone = textView {
                textColorResource = R.color.colorBlack
            }.lparams {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            tvStarRate = textView {
                textColorResource = R.color.colorBlue
            }.lparams {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            linearLayout {

                gravity = Gravity.CENTER_VERTICAL

                tvOpenTime = textView {
                }

                imgOpenStatus = imageView { }.lparams(dip(5), dip(5)) {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }
            }.lparams {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }

            linearLayout {

                checkBox0 = checkBox("T2")

                checkBox1 = checkBox("T3").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                checkBox2 = checkBox("T4").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                checkBox3 = checkBox("T5").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                checkBox4 = checkBox("T6").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                checkBox5 = checkBox("T7").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }

                checkBox6 = checkBox("CN").lparams {
                    leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
                }
            }.lparams {
                topMargin = dimen(R.dimen.accountFragmentLoginPadding)
                leftMargin = dimen(R.dimen.accountFragmentLoginPadding)
            }
        }
    }
}
