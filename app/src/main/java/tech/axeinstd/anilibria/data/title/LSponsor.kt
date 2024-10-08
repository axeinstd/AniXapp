package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LSponsor (
    val id: String,
    val title: String,
    val description: String,
    val url_title: String,
    val url: String,
): Parcelable