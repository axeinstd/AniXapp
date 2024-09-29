package tech.axeinstd.anilibri3.data.title.player

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class LTitleEpisodes (
    val first: Int?,
    val last: Int?,
    val string: String?
): Parcelable