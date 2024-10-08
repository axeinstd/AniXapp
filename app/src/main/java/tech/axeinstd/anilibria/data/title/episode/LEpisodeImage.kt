package tech.axeinstd.anilibria.data.title.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.posters.LTitlePosterOptimized

@Parcelize
@Serializable
data class LEpisodeImage (
    val src: String?,
    val thumbnail: String?,
    val optimized: LEpisodeImageOptimized?
): Parcelable