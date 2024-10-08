package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.meta.LMeta


@Parcelize
@Serializable
data class LFavoritesList (
    val data: List<LTitle>? = null,
    val meta: LMeta? = null,
    val message: String? = null
): Parcelable