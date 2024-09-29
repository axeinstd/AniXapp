package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LBlocked (
    val blocked: Boolean = false,
    val  bakanim: Boolean = false,
    val copyrights: Boolean = false,
    val geoip: Boolean = false,
    val geoip_list: List<String>?
): Parcelable