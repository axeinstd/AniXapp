package tech.axeinstd.anilibria.data.title.episode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LEpisodeImageOptimized (
    val src: String?,
    val thumbnail: String?
): Parcelable