package tech.axeinstd.anilibri3.data.title.torrents

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibri3.data.title.player.LTitleEpisodes

@Parcelize
@Serializable
data class LTitleTorrent (
    val torrent_id: Int,
    val episodes: LTitleEpisodes,
    val quality: LTorrentQuality,
    val leechers: Int,
    val seeders: Int,
    val downloads: Int,
    val total_size: Long,
    val size_string: String,
    val url: String,
    val magnet: String,
    val uploaded_timestamp: Long,
    val hash: String,
    val metadata: String?,
    val raw_base64_file: String?
): Parcelable