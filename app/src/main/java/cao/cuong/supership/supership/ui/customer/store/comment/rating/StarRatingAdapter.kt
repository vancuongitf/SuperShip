package cao.cuong.supership.supership.ui.customer.store.comment.rating

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.Rating
import cao.cuong.supership.supership.extension.enableHighLightWhenClickedForListItem
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource

class StarRatingAdapter(internal val items: List<Rating>) : RecyclerView.Adapter<StarRatingAdapter.StarRatingItemHolder>() {

    internal var onItemClicked: (Rating) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarRatingItemHolder {
        val ui = StarRatingItemUI()
        return StarRatingItemHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StarRatingItemHolder?, position: Int) {
        holder?.onBind()
    }


    inner class StarRatingItemHolder(private val ui: StarRatingItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.enableHighLightWhenClickedForListItem()
            itemView.onClick {
                if (adapterPosition > -1) {
                    onItemClicked(items[adapterPosition])
                }
            }
        }

        internal fun onBind() {
            ui.tvStarValue.text = items[adapterPosition].value.toString()
            ui.tvMessage.text = items[adapterPosition].getMessage()
            ui.tvRatingCount.text = "${items[adapterPosition].count} Đánh giá"

            if (items[adapterPosition].isSelected) {
                ui.tvStarValue.textColorResource = R.color.colorBlue
                ui.tvMessage.textColorResource = R.color.colorBlue
                ui.imgStar.setImageResource(R.drawable.ic_star_gold)
            } else {
                ui.tvStarValue.textColorResource = R.color.colorBlack
                ui.tvMessage.textColorResource = R.color.colorBlack
                ui.imgStar.setImageResource(R.drawable.ic_star_black)
            }
        }
    }
}
