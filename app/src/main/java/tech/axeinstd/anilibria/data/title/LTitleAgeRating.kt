package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleAgeRating (
    val value: String,
    val label: String,
    val is_adult: Boolean,
    val description: String
): Parcelable