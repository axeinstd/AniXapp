package tech.axeinstd.anilibri3.data.franchises

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibri3.data.title.LTitleNames

@Parcelize
@Serializable
data class LFranchiseRelease (
    val id: Int,
    val code: String,
    val ordinal: Int,
    val names: LTitleNames
): Parcelable