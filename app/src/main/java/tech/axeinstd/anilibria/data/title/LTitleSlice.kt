package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleSlice (
    val stop: Int?,
    val start: Int?
): Parcelable