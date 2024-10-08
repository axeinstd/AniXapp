package tech.axeinstd.anilibria.data.title.meta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LMeta (
    val pagination: LPagination
): Parcelable