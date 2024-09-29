package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
class LTitlePosters (
    var baseUrl: String = "https://static-libria.weekstorm.us/",
    val small: LTitlePoster,
    val medium: LTitlePoster,
    val original: LTitlePoster
): Parcelable {
    fun getPosterUrl(size: String = "original"): String {
        when (size) {
            "small" -> return "$baseUrl${small.url}"
            "medium" -> return "$baseUrl${medium.url}"
            "original" -> return "$baseUrl${original.url}"
            else -> return return "$baseUrl${original.url}"
        }
    }
}