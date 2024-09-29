package tech.axeinstd.anilibri3

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json
import tech.axeinstd.anilibri3.data.account.Account
import tech.axeinstd.anilibri3.data.title.LTitle
import tech.axeinstd.anilibri3.data.title.LTitleList
import tech.axeinstd.anilibri3.data.title.schedule.LScheduleDay
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

class Libria(
    var url: String = "https://vk.anilib.moe/api/v3",
    var loginUrl: String = "https://vk.anilib.moe/public",
) {
    var jsonConfig = Json {
        ignoreUnknownKeys = true
    }

    suspend fun getTitleById(id: Int): LTitle {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
            }
        }
        val release: LTitle = client.get("$url/title?id=$id").body()
        return release
    }

    suspend fun search(
        search: String,
        year: String? = null,
        type: String? = null,
        session_code: String? = null,
        genres: String? = null,
        team: String? = null,
        voice: String? = null,
        translator: String? = null,
        editing: String? = null,
        decor: String? = null,
        timing: String? = null,
        filter: String? = null,
        remove: String? = null,
        include: String? = null,
        description_type: String = "plain",
        playlist_type: String = "object",
        limit: Int? = null,
        after: Int? = null,
        order_by: String? = null,
        sort_direction: Int = 0,
        page: Int? = null,
        items_per_page: Int? = null
    ): LTitleList {
        val params: Map<String, String?> = mapOf(
            "search" to search,
            "year" to year,
            "type" to type,
            "session_code" to session_code,
            "genres" to genres,
            "team" to team,
            "voice" to voice,
            "translator" to translator,
            "editing" to editing,
            "decor" to decor,
            "timing" to timing,
            "filter" to filter,
            "remove" to remove,
            "include" to include,
            "description_type" to description_type,
            "playlist_type" to playlist_type,
            "limit" to limit?.toString(),
            "after" to after?.toString(),
            "order_by" to order_by,
            "sort_direction" to sort_direction.toString(),
            "page" to page?.toString(),
            "items_per_page" to items_per_page?.toString()
        )

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
            }
        }

        val response: LTitleList = client.get("${url}/title/search") {
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

    suspend fun login(login: String, passwd: String): Account {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
                register(ContentType.Text.Html, KotlinxSerializationConverter(Json))
            }
        }

        val response: Account = client.post("${loginUrl}/login.php") {
            contentType(ContentType.Application.Json)
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("mail", login)
                        append("passwd", passwd)
                    }
                )
            )
        }.body()
        return response
    }

    suspend fun advancedSearch(
        query: String,
        simple_query: String? = null,
        filter: String? = null,
        remove: String? = null,
        include: String? = null,
        description_type: String = "plain",
        playlist_type: String = "object",
        limit: Int? = null,
        after: Int? = null,
        order_by: String? = null,
        sort_direction: Int = 0,
        page: Int? = null,
        items_per_page: Int? = null
    ): LTitleList {
        val params: Map<String, String?> = mapOf(
            "query" to query,
            "simple_query" to simple_query,
            "filter" to filter,
            "remove" to remove,
            "include" to include,
            "description_type" to description_type,
            "playlist_type" to playlist_type,
            "limit" to limit?.toString(),
            "after" to after?.toString(),
            "order_by" to order_by,
            "sort_direction" to sort_direction.toString(),
            "page" to page?.toString(),
            "items_per_page" to items_per_page?.toString()
        )


        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
            }
        }

        val response: LTitleList = client.get("${url}/title/search/advanced") {
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

    suspend fun getUserFavorites(
        session: String,
        filter: String? = null,
        remove: String? = null,
        include: String? = null,
        description_type: String = "plain",
        playlist_type: String = "object",
        limit: Int? = null,
        after: Int? = null,
        page: Int? = null,
        items_per_page: Int? = null
    ): LTitleList {
        val params: Map<String, String?> = mapOf(
            "session" to session,
            "filter" to filter,
            "remove" to remove,
            "include" to include,
            "description_type" to description_type,
            "playlist_type" to playlist_type,
            "limit" to limit?.toString(),
            "after" to after?.toString(),
            "page" to page?.toString(),
            "items_per_page" to items_per_page?.toString()
        )


        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
            }
        }

        val response: LTitleList = client.get("${url}/user/favorites") {
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

    suspend fun schedule(
        filter: String? = null,
        remove: String? = null,
        include: String? = null,
        days: String? = null,
        description_type: String = "plain",
        playlist_type: String = "object",
    ): List<LScheduleDay> {
        val params: Map<String, String?> = mapOf(
            "filter" to filter,
            "remove" to remove,
            "include" to include,
            "days" to days,
            "description_type" to description_type,
            "playlist_type" to playlist_type,
        )


        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    jsonConfig
                )
            }
        }

        val response: List<LScheduleDay> = client.get("${url}/title/schedule") {
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
}