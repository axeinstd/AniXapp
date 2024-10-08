package tech.axeinstd.anilibria.data.title.meta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LPagination (
    val total: Int,
    val count: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int,
    val links: LPaginationLinks
): Parcelable