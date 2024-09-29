package tech.axeinstd.anilibri3.data.title.player

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class LTitlePlayer (
    val alternative_player: String? = null,
    val host: String? = null,
    val episodes: LTitleEpisodes? = null,
    val list: Map<String, LTitleEpisode>? = null
): Parcelable