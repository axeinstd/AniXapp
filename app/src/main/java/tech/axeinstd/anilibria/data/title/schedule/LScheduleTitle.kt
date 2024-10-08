package tech.axeinstd.anilibria.data.title.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anilibria.data.title.episode.LEpisode

@Parcelize
@Serializable
data class LScheduleTitle (
    val release: LTitle,
    val new_release_episode: LEpisode?,
    val new_release_episode_ordinal: Int
): Parcelable