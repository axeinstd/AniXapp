package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LPagination (
    val pages: Int,
    val current_page: Int,
    val items_per_page: Int,
    val total_items: Int
): Parcelable