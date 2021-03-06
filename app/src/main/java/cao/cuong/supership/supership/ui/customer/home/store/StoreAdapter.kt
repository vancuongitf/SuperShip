package cao.cuong.supership.supership.ui.customer.home.store

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cao.cuong.supership.supership.BuildConfig
import cao.cuong.supership.supership.R
import cao.cuong.supership.supership.data.model.StoreInfoExpress
import cao.cuong.supership.supership.extension.getDistanceString
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textResource

/**
 *
 * @author at-cuongcao.
 */
class StoreAdapter(private val storeInfoExpresses: MutableList<StoreInfoExpress>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var nextPageFlag = false
    internal var onItemClicked: (item: StoreInfoExpress) -> Unit = {}

    override fun getItemCount() = storeInfoExpresses.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val ui = StoreItemUI()
        return StoreHolder(ui, ui.createView(AnkoContext.Companion.create(parent.context, parent)))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? StoreHolder)?.onBind()
    }

    inner class StoreHolder(val ui: StoreItemUI, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val option = RequestOptions()
                .placeholder(R.drawable.glide_place_holder)

        init {
            itemView.onClick {
                onItemClicked(storeInfoExpresses[adapterPosition])
            }
        }

        internal fun onBind() {
            with(storeInfoExpresses[adapterPosition]) {
                Glide.with(itemView.context)
                        .applyDefaultRequestOptions(option)
                        .asBitmap()
                        .load(BuildConfig.BASE_IMAGE_URL + storeImage)
                        .into(ui.imgStoreIcon)
                ui.tvStoreName.text = storeName
                ui.tvStoreAddress.text = storeAddress
                if (storeDistance != null) {
                    ui.tvStoreDistance.text = storeDistance.getDistanceString()
                } else {
                    ui.tvStoreDistance.text = ""
                }
                if (storeRate.rateCount == 0) {
                    ui.tvStoreStarRate.textResource = R.string.not_rate_yet
                } else {
                    ui.tvStoreStarRate.text = itemView.context.getString(R.string.store_rate, storeRate.rate.toString(), storeRate.rateCount.toString())
                }
            }
        }
    }
}
