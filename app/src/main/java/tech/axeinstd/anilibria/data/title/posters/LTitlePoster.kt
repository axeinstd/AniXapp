package tech.axeinstd.anilibria.data.title.posters

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitlePoster (
    val src: String?,
    val thumbnail: String?,
    val optimized: LTitlePosterOptimized?
): Parcelable