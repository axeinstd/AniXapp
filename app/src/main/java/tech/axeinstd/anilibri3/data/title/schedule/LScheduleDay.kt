package tech.axeinstd.anilibri3.data.title.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibri3.data.title.LTitle

@Parcelize
@Serializable
data class LScheduleDay (
    val day: Int,
    val list: List<LTitle>
): Parcelable