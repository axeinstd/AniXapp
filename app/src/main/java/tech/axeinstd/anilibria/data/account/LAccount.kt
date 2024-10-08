package tech.axeinstd.anilibria.data.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LAccount (
    val token: String?,
    val error: String? = null
): Parcelable