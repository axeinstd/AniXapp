package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleNames(
    val ru: String,
    val en: String,
    val alternative: String?
): Parcelable