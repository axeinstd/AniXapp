package tech.axeinstd.anilibri3.data.franchises

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LFranchise (
    val id: String,
    val name: String
): Parcelable