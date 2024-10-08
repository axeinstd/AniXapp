package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleName (
    val main: String,
    val english: String? = null,
    val alternative: String? = null
): Parcelable