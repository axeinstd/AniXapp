package tech.axeinstd.anilibri3.data.title.player

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleEpisodeSkips (
    val opening: List<Int>,
    val ending: List<Int>
): Parcelable