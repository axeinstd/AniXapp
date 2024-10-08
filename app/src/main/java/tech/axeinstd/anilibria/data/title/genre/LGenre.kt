package tech.axeinstd.anilibria.data.title.genre

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.posters.LTitlePoster

@Parcelize
@Serializable
data class LGenre (
    val id: Int,
    val name: String,
    val image: LGenreImage? = null,
    val total_releases: Int
): Parcelable