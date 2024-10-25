package tech.axeinstd.anilibria.data.title.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.LTitleSlice
import tech.axeinstd.anilibria.data.title.posters.LTitlePoster

@Parcelize
@Serializable
data class LEpisode (
    val id: String,
    val name: String?,
    val ordinal: Double,
    val opening: LTitleSlice,
    val preview: LEpisodeImage,
    val hls_480: String?,
    val hls_720: String?,
    val hls_1080: String?,
    val duration: Int,
    val rutube_id: String?,
    val youtube_id: String?,
    val updated_at: String,
    val sort_order: Int,
    val name_english: String?
): Parcelable