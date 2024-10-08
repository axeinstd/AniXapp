package tech.axeinstd.anilibria.data.title.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class LScheduleToday (
    val today: List<LScheduleTitle>
): Parcelable