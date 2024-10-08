package tech.axeinstd.anilibria

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tech.axeinstd.anilibria.data.account.LAccount
import tech.axeinstd.anilibria.data.account.LFavoritesResult
import tech.axeinstd.anilibria.data.account.LFavoriteRelease
import tech.axeinstd.anilibria.data.title.LFavoritesList
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anilibria.data.title.LTitleList
import tech.axeinstd.anilibria.data.title.members.LMember
import tech.axeinstd.anilibria.data.title.members.LMembers
import tech.axeinstd.anilibria.data.title.schedule.LScheduleDay
import tech.axeinstd.anilibria.data.title.schedule.LScheduleTitle
import tech.axeinstd.anilibria.data.title.schedule.LScheduleToday
import tech.axeinstd.anilibria.enumerates.title.AgeRating
import tech.axeinstd.anilibria.enumerates.title.ProductionStatus
import tech.axeinstd.anilibria.enumerates.title.PublishStatuse
import tech.axeinstd.anilibria.enumerates.title.ScheduleType
import tech.axeinstd.anilibria.enumerates.title.Sorting
import tech.axeinstd.anilibria.enumerates.title.TitleSeason
import tech.axeinstd.anilibria.enumerates.title.TitleType

class AniLibria(
    val url: String = "https://anilibria.top/api/v1",
    val baseUrl: String = "https://anilibria.top"
) {
    val jsonConfig = Json {
        ignoreUnknownKeys = true
    }

    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                jsonConfig
            )
            register(ContentType.Text.Html, KotlinxSerializationConverter(Json))
        }
    }

    // AUTH SECTION
    suspend fun auth(login: String, password: String): LAccount {
        val response: LAccount = client.post("$url/accounts/users/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("login", login)
                        append("password", password)
                    }
                )
            )
        }.body()
        return response
    }

    suspend fun logout(token: String): LAccount {
        val response: LAccount = client.post("$url/accounts/users/auth/logout") {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
        return response
    }

    // TITLE SECTION
    suspend fun getTitleById(id: Int): LTitle {
        val release: LTitle = client.get("$url/anime/releases/$id").body()
        return release
    }

    suspend fun releases(
        page: Int = 1,
        limit: Int = 10,
        genres: String? = null,
        types: List<TitleType>? = null,
        seasons: List<TitleSeason>? = null,
        from_year: Int? = null,
        to_year: Int? = null,
        search: String? = null,
        sorting: Sorting? = null,
        age_ratings: List<AgeRating>? = null,
        publish_statuses: List<PublishStatuse>? = null,
        production_statuses: List<ProductionStatus>? = null
    ): LTitleList {
        types?.forEach { it.toString() }
        seasons?.forEach { it.toString() }
        age_ratings?.forEach { it.toString() }
        publish_statuses?.forEach { it.toString() }
        production_statuses?.forEach { it.toString() }
        val params: Map<String, String?> = mapOf(
            "page" to page.toString(),
            "limit" to limit.toString(),
            "f[genres]" to genres,
            "f[types]" to types?.toString()?.substring(1, types.toString().length - 1),
            "f[seasons]" to seasons?.toString()?.substring(1, seasons.toString().length - 1),
            "f[years][from_year]" to from_year?.toString(),
            "f[years][to_year]" to to_year?.toString(),
            "f[search]" to search,
            "f[sorting]" to sorting?.toString(),
            "f[age_ratings]" to age_ratings?.toString()?.substring(1, age_ratings.toString().length - 1),
            "f[publish_statuses]" to publish_statuses?.toString()?.substring(1, publish_statuses.toString().length - 1),
            "f[production_statuses]" to production_statuses?.toString()
                ?.substring(1, production_statuses.toString().length - 1)
        )
        val response: LTitleList = client.get("${url}/anime/catalog/releases") {
            url {
                for ((param, value) in params) {
                    if (value != null) {
                        parameters.append(param, value)
                    }
                }
            }
        }.body()

        return response
    }

    suspend fun schedule(type: ScheduleType): List<LScheduleTitle> {
        if (type == ScheduleType.NOW) {
            val response: LScheduleToday = client.get("$url/anime/schedule/${type}").body()
            return response.today
        }
        val response: List<LScheduleTitle> = client.get("$url/anime/schedule/${type}").body()
        return response
    }

    suspend fun getUserFavorites(token: String): LFavoritesList {
        val response: LFavoritesList = client.post("$url/accounts/users/me/favorites/releases") {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
        return response
    }

    suspend fun addUserFavorites(ids: List<Int>, authToken: String): LFavoritesResult {
        val releases: MutableList<LFavoriteRelease> = mutableListOf()
        for (id in ids) {
            releases.add(LFavoriteRelease(release_id = id))
        }

        val response = client.post("$url/accounts/users/me/favorites") {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $authToken")
            }
            setBody(
                Json.encodeToString<MutableList<LFavoriteRelease>>(releases)
            )
        }
        when (response.status) {
            HttpStatusCode.OK -> return LFavoritesResult(message = "Релизы успешно добавлены в избранное")
            HttpStatusCode.Forbidden -> return LFavoritesResult(message = "Необходимо авторизоваться")
        }
        return LFavoritesResult(message = "Непредвиденная ошибка")
    }

    suspend fun deleteUserFavorites(ids: List<Int>, authToken: String): LFavoritesResult {
        val releases: MutableList<LFavoriteRelease> = mutableListOf()
        for (id in ids) {
            releases.add(LFavoriteRelease(release_id = id))
        }

        val response = client.delete("$url/accounts/users/me/favorites") {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $authToken")
            }
            setBody(
                Json.encodeToString<MutableList<LFavoriteRelease>>(releases)
            )
        }
        when (response.status) {
            HttpStatusCode.OK -> return LFavoritesResult(message = "Релизы успешно удалены")
            HttpStatusCode.Forbidden -> return LFavoritesResult(message = "Необходимо авторизоваться")
        }
        return LFavoritesResult(message = "Непредвиденная ошибка")
    }

    object Utils {
        fun scheduleAsDaysList(schedule: List<LScheduleTitle>): List<LScheduleDay> {
            val lScheduleDayList: MutableList<LScheduleDay> = mutableListOf(
                LScheduleDay(
                    day = 0,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 1,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 2,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 3,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 4,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 5,
                    list = mutableListOf()
                ),
                LScheduleDay(
                    day = 6,
                    list = mutableListOf()
                )
            )
            for (title in schedule) {
                if (title.release.publish_day != null)
                    lScheduleDayList[title.release.publish_day.value - 1].list.add(title)
            }
            return lScheduleDayList
        }
        fun separateMembersByTheirWork(members: List<LMember>): LMembers {
            val membersList: LMembers = LMembers(
                voicing = mutableListOf(),
                timing = mutableListOf(),
                decorating = mutableListOf(),
                translating = mutableListOf()
            )
            for (member in members) {
                when (member.role.value) {
                    "voicing" -> membersList.voicing.add(member)
                    "timing" -> membersList.timing.add(member)
                    "decorating" -> membersList.decorating.add(member)
                    "translating" -> membersList.translating.add(member)
                }
            }
            return membersList
        }
    }
}