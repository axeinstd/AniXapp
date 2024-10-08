package tech.axeinstd.anilibria.data.title.torrents

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTorrentCodec (
    val value: String,
    val description: String
): Parcelable