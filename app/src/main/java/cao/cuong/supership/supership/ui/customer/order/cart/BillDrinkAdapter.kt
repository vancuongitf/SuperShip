package cao.cuong.supership.supership.ui.customer.order.cart

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.OrderedDrink
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick

class BillDrinkAdapter(private val drinks: MutableList<OrderedDrink>, private val adapterType: AdapterType = AdapterType.CART_INFO) : RecyclerView.Adapter<BillDrinkAdapter.BillDrinkViewHolder>() {

    internal val option = RequestOptions()
            .placeholder(R.drawable.glide_place_holder)

    internal var onDrinkCountChange: () -> Unit = {}
    internal var onItemClicked: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillDrinkViewHolder {
        val ui = BillDrinkItemUI()
        return BillDrinkViewHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent, false)))
    }

    override fun getItemCount() = drinks.size

    override fun onBindViewHolder(holder: BillDrinkViewHolder?, position: Int) {
        holder?.onBind()
    }


    inner class BillDrinkViewHolder(private val ui: BillDrinkItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.onClick {
                if (adapterPosition > -1) {
                    onItemClicked(adapterPosition)
                }
            }

            if (adapterType == AdapterType.BILL_INFO) {
                ui.imgMinus.visibility = View.GONE
                ui.imgAdd.visibility = View.GONE
            } else {
                ui.imgMinus.onClick {
                    if (adapterPosition > -1) {
                        with(drinks[adapterPosition]) {
                            if (count > 1) {
                                count--
                            }
                            onDrinkCountChange()
                        }
                        notifyDataSetChanged()
                    }
                }

                ui.imgAdd.onClick {
                    if (adapterPosition > -1) {
                        with(drinks[adapterPosition]) {
                            count++
                            onDrinkCountChange()
                        }
                        notifyDataSetChanged()
                    }
                }
            }
        }

        internal fun onBind() {
            with(drinks[adapterPosition]) {
                Glide.with(itemView.context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load("https://vnshipperman.000webhostapp.com/uploads/$image")
                        .into(ui.imgDrinkImage)
                ui.tvDrinkName.text = name
                ui.tvDrinkPrice.text = itemView.context.getString(R.string.drinkPrice, price)
                ui.tvDrinkCount.text = count.toString()
                ui.tvTotalPrice.text = itemView.context.getString(R.string.drinkTotalPrice, price * count)
            }
        }
    }

    enum class AdapterType {
        CART_INFO,
        BILL_INFO
    }
}
