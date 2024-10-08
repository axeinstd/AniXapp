package tech.axeinstd.anilibria.data.title.members

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LMembers (
    val voicing: MutableList<LMember>,
    val timing: MutableList<LMember>,
    val decorating: MutableList<LMember>,
    val translating: MutableList<LMember>
): Parcelable