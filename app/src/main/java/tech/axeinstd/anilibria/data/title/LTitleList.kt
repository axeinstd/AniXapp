package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.meta.LMeta

@Parcelize
@Serializable
data class LTitleList (
    val data: List<LTitle>,
    val meta: LMeta
): Parcelable