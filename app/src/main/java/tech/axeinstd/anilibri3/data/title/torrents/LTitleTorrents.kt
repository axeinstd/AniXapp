package tech.axeinstd.anilibri3.data.title.torrents

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibri3.data.title.player.LTitleEpisodes

@Parcelize
@Serializable
data class LTitleTorrents (
    val episodes: LTitleEpisodes,
    val list: List<LTitleTorrent>
): Parcelable