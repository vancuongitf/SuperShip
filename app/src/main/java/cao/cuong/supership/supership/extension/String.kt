package cao.cuong.supership.supership.extension

import android.util.Patterns
import java.security.MessageDigest
import java.text.Normalizer
import java.util.regex.Pattern


/**
 *
 * @author at-cuongcao.
 */

private const val REGEX_USER_NAME = "[a-zA-Z][a-zA-Z0-9]{7,15}"
private const val REGEX_PASS_WORD = "[a-zA-Z0-9]{8,16}"
private const val REGEX_PHONE_NUMBER_MOBILE = "('+'84|84|0)(1[2689]|9)[0-9]{8,8}"
private const val REGEX_PHONE_NUMBER_STATIC = "('+'84|84|0)2[0-9]{2}"
private const val REGEX_EMAIL_ADDRESS = "((([a-zA-Z0-9]{0,1})+([_|.]{0,1}([a-zA-Z0-9]){0,1})+))+@(([a-zA-Z0-9]{1,63}[-|.]{1}[a-zA-Z0-9]{1,63})(([-|.]{1}[a-zA-Z0-9]{1,63}){0,1})+){1,255}"
private const val REGEX_OTP_CODE = "[1-9][0-9]{5}"
private const val REGEX_FULL_NAME = "[a-zA-Z_ \\-ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+"
private const val REGEX_STORE_NAME = "[a-zA-Z0-9_ \\-ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ*%@]+"

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

internal fun String.isValidateFullName() = Regex(REGEX_FULL_NAME).matches(this)

internal fun String.isValidateUserName() = Regex(REGEX_USER_NAME).matches(this)

internal fun String.isValidatePassWord() = Regex(REGEX_PASS_WORD).matches(this)

internal fun String.isValidateEmail() = Patterns.EMAIL_ADDRESS.pattern().toRegex().matches(this)

internal fun String.isValidateOTPCode() = Regex(REGEX_OTP_CODE).matches(this)

internal fun String.isValidateStoreName() = Regex(REGEX_STORE_NAME).matches(this)

internal fun String.isValidatePhoneNumber() = Regex(REGEX_PHONE_NUMBER_MOBILE).matches(this) || Regex(REGEX_PHONE_NUMBER_STATIC).matches(this)
//internal fun String.validatePassword(): Boolean {
//    val regex = ""
//    val pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.pattern())
//    return pattern.toRegex().matches(this)
//}

internal fun String.sha1(): String {
    val hexChars = "0123456789ABCDEF"
    val bytes = MessageDigest
            .getInstance("SHA-1")
            .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(hexChars[i shr 4 and 0x0f])
        result.append(hexChars[i and 0x0f])
    }
    return result.toString().toLowerCase()
}
