package tech.axeinstd.anilibri3.data.title.player

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LTitleHLS (
    val fhd: String?,
    val hd: String?,
    val sd: String?
): Parcelable