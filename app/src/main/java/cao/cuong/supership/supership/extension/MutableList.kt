package cao.cuong.supership.supership.extension

import cao.cuong.supership.supership.data.model.StoreInfoExpress

/**
 *
 * @author at-cuongcao.
 */
internal fun MutableList<StoreInfoExpress>.addAllDistinct(list: List<StoreInfoExpress>) {
    for (store in list) {
        if (this.find { it.storeId == store.storeId } == null) {
            this.add(store)
        }
    }
}
