package tech.axeinstd.anilibria.data.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LFavoriteRelease (
    val release_id: Int
): Parcelable