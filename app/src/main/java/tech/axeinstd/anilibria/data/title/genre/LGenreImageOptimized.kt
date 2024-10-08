package tech.axeinstd.anilibria.data.title.genre

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LGenreImageOptimized (
    val preview: String,
    val thumbnail: String
): Parcelable