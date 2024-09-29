package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LSeason (
    val string: String?,
    val code: Int,
    val year: Int,
    val week_day: Int,
): Parcelable