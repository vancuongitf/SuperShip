package cao.cuong.supership.supership.extension

import java.text.Normalizer
import java.util.regex.Pattern


/**
 *
 * @author at-cuongcao.
 */
internal fun String.unAccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    var result = pattern.matcher(temp).replaceAll("")
    while (result.contains("Đ")) {
        result = result.replace("Đ", "d")
    }
    while (result.contains("đ")) {
        result = result.replace("đ", "d")
    }
    return result
}
