package tech.axeinstd.anilibria.data.title

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import tech.axeinstd.anilibria.data.title.episode.LEpisode
import tech.axeinstd.anilibria.data.title.genre.LGenre
import tech.axeinstd.anilibria.data.title.members.LMember
import tech.axeinstd.anilibria.data.title.posters.LTitlePoster
import tech.axeinstd.anilibria.data.title.torrents.LTorrent

@Parcelize
@Serializable
data class LTitle (
    val id: Int,
    val type: LTitleType? = null,
    val year: Int? = null,
    val name: LTitleName,
    val alias: String? = null,
    val season: LTitleSeason? = null,
    val poster: LTitlePoster? = null,
    val fresh_at: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val is_onging: Boolean? = null,
    val age_rating: LTitleAgeRating? = null,
    val publish_day: LTitlePublishDay? = null,
    val description: String? = null,
    val notification: String? = null,
    val episodes_total: Int? = null,
    val external_player: String? = null,
    val is_in_production: Boolean,
    val is_blocked_by_geo: Boolean,
    val episodes_are_unknown: Boolean,
    val is_blocked_by_copyrights: Boolean,
    val added_in_users_favorites: Int,
    val average_duration_of_episode: Double? = null,
    val genres: List<LGenre>,
    val members: List<LMember>? = null,
    val sponsor: LSponsor? = null,
    val episodes: List<LEpisode>? = null,
    val torrents: List<LTorrent>? = null
): Parcelable