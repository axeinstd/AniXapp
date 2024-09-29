package tech.axeinstd.anilibri3.data.title.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LSchedule (
    val list: List<LScheduleDay>
): Parcelable