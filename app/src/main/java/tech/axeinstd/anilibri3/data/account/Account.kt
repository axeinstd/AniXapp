package tech.axeinstd.anilibri3.data.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Account (
    val err: String? = null,
    val mes: String? = null,
    val key: String? = null,
    val sessionId: String? = null,
): Parcelable