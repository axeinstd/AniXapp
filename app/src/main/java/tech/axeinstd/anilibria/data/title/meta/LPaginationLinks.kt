package tech.axeinstd.anilibria.data.title.meta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LPaginationLinks (
    val previous: String? = null,
    val next: String? = null
): Parcelable