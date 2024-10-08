package tech.axeinstd.anilibria.data.title.torrents

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.LTitleType

@Parcelize
@Serializable
data class LTorrent (
    val id: Int,
    val hash: String,
    val size: Long,
    val type: LTitleType,
    val label: String,
    val codec: LTorrentCodec,
    val color: LTorrentColor,
    val magnet: String,
    val seeders: Int,
    val quality: LTorrentQuality,
    val bitrate: Int?,
    val leechers: Int,
    val sort_order: Int,
    val created_at: String,
    val updated_at: String,
    val description: String,
    val completed_times: Int
): Parcelable