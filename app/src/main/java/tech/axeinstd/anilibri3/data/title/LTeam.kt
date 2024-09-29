package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class LTeam (
    val voice: List<String>,
    val translator: List<String>,
    val editing: List<String>,
    val decor: List<String>,
    val timing: List<String>
): Parcelable