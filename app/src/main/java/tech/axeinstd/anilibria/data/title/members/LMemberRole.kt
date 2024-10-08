package tech.axeinstd.anilibria.data.title.members

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LMemberRole (
    val value: String,
    val description: String
): Parcelable