package tech.axeinstd.anilibria.data.title.schedule

import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.LTitle


@Serializable
data class LScheduleDay (
    val day: Int,
    val list: MutableList<LScheduleTitle>
)