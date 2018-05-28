package cao.cuong.supership.supership.data.source.remote.response

import cao.cuong.supership.supership.data.model.Comment
import cao.cuong.supership.supership.data.model.Rating
import com.google.gson.annotations.SerializedName

data class StoreCommentResponse(@SerializedName("next_page_flag") var nextPageFlag: Boolean,
                                @SerializedName("ratings") val ratings: MutableList<Rating>,
                                @SerializedName("comments") val comments: MutableList<Comment>) {

    internal fun starRated() = ratings.any {
        it.isSelected
    }
}
