package tech.axeinstd.anilibria.data.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LFavoritesResult (
    val message: String? = null
): Parcelable