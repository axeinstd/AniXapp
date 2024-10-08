package tech.axeinstd.anilibria.data.title.members

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LMember (
    val id: String,
    val role: LMemberRole,
    val nickname: String,
    // val user: LUser
): Parcelable