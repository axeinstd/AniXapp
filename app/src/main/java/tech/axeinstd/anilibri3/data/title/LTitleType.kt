package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class LTitleType (
    val full_string: String,
    val code: Int,
    val string: String,
    val episodes: Int?,
    val length: Int?
): Parcelable