package tech.axeinstd.anilibri3.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibri3.data.franchises.LFranchises
import tech.axeinstd.anilibri3.data.title.player.LTitlePlayer
import tech.axeinstd.anilibri3.data.title.torrents.LTitleTorrents

@Parcelize
@Serializable
data class LTitle(
    val id: Int? = null,
    val code: String? = null,
    val names: LTitleNames? = null,
    val franchises: List<LFranchises>? = null,
    val announce: String? = null,
    val status: LTitleStatus? = null,
    val posters: LTitlePosters? = null,
    val updated: Int? = null,
    val last_change: Int? = null,
    val type: LTitleType? = null,
    val genres: List<String>? = null,
    val team: LTeam? = null,
    val season: LSeason? = null,
    val description: String? = null,
    val in_favorites: Int? = null,
    val blocked: LBlocked? = null,
    val player: LTitlePlayer? = null,
    val torrents: LTitleTorrents? = null
): Parcelable