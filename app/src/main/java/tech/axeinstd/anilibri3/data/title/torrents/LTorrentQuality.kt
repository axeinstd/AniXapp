package tech.axeinstd.anilibri3.data.title.torrents
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTorrentQuality (
    val string: String,
    val type: String,
    val resolution: String,
    val encoder: String,
): Parcelable