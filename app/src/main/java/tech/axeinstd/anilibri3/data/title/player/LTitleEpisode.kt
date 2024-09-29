package tech.axeinstd.anilibri3.data.title.player

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleEpisode (
    val episode: Double,
    val name: String?,
    val uuid: String?,
    val created_timestamp: Long,
    val preview: String?,
    val skips: LTitleEpisodeSkips,
    val hls: LTitleHLS,
): Parcelable