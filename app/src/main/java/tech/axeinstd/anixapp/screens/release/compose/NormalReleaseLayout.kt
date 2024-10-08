package tech.axeinstd.anixapp.screens.release.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.datastore.preferences.protobuf.Timestamp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anilibria.data.title.episode.LEpisode
import tech.axeinstd.anilibria.data.title.members.LMember
import tech.axeinstd.anilibria.data.title.members.LMembers
import tech.axeinstd.anixapp.AniLibriaClient
import tech.axeinstd.anixapp.R
import tech.axeinstd.anixapp.releaseScreenRoute
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NormalReleaseLayout(
    innerPadding: PaddingValues,
    release: MutableState<LTitle>,
    navController: NavController
) {
    val defaultCardModifier = Modifier
        .fillMaxWidth(0.97f)
    val scrollState = rememberScrollState()
    val descriptionColor = MaterialTheme.colorScheme.surface.copy(
        red = MaterialTheme.colorScheme.surface.red + if (isSystemInDarkTheme()) 0.5f else -0.5f,
        green = MaterialTheme.colorScheme.surface.green + if (isSystemInDarkTheme()) 0.5f else -0.5f,
        blue = MaterialTheme.colorScheme.surface.blue + if (isSystemInDarkTheme()) 0.5f else -0.5f
    )
    val episodeCardColor = MaterialTheme.colorScheme.secondaryContainer.copy(
        alpha = 0.1f
    )
    val previewHeight = rememberSaveable { mutableIntStateOf(0) }
    val team = rememberSaveable {
        mutableStateOf(
            LMembers(
                voicing = mutableListOf(),
                timing = mutableListOf(),
                decorating = mutableListOf(),
                translating = mutableListOf()
            )
        )
    }

    if (release.value.members != null) {
        team.value =
            AniLibria.Utils.separateMembersByTheirWork(release.value.members as List<LMember>)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(innerPadding.calculateTopPadding())
            )
            Card(
                modifier = defaultCardModifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .onGloballyPositioned { layoutCoordinates ->
                                previewHeight.intValue = layoutCoordinates.size.height
                            },
                        model = AniLibriaClient.baseUrl + release.value.poster?.src,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = defaultCardModifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        release.value.name.main,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        release.value.name.english ?: "Английское имя отсутствует",
                        fontSize = 10.sp
                    )
                    Row {
                        Text("Сезон: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.year} ${release.value.season?.description}")
                    }
                    Row {
                        Text("Тип: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.type?.description}")
                    }
                    FlowRow {
                        Text("Жанры: ", fontWeight = FontWeight.Bold)
                        release.value.genres.forEachIndexed { index: Int, genre ->
                            Text(genre.name)
                            if (index != release.value.genres.size - 1) {
                                Text(",")
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                    FlowRow {
                        Text("Озвучка: ", fontWeight = FontWeight.Bold)
                        team.value.voicing.forEachIndexed { index: Int, member ->
                            Text(member.nickname)
                            if (index != team.value.voicing.size - 1) {
                                Text(",")
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                    FlowRow {
                        Text("Тайминг: ", fontWeight = FontWeight.Bold)
                        team.value.timing.forEachIndexed { index: Int, member ->
                            Text(member.nickname)
                            if (index != team.value.timing.size - 1) {
                                Text(",")
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                    FlowRow {
                        Text("Работа над субтитрами: ", fontWeight = FontWeight.Bold)
                        team.value.translating.forEachIndexed { index: Int, member ->
                            Text(member.nickname)
                            if (index != team.value.translating.size - 1) {
                                Text(",")
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                    Row {
                        Text("Состояние релиза: ", fontWeight = FontWeight.Bold)
                        Text(if (release.value.is_onging == true) "В работе" else "Завершен")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = defaultCardModifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Text(
                    "Torrent раздачи",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(5.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .height(((release.value.torrents?.size ?: 0) * 60).dp)
                        .wrapContentHeight()
                        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                    userScrollEnabled = false,
                ) {
                    items(release.value.torrents ?: emptyList()) { torrent ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row {
                                    Text(
                                        "Серия ${torrent.description} ",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text("${torrent.type.description} ${torrent.quality.description}${if (torrent.codec.value == "x265/HEVC") "HEVC" else ""}", color = descriptionColor)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("${Math.round((torrent.size / 1073741824.0) * 10) / 10.0} GB")
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Icon(
                                        imageVector = ImageVector.Companion.vectorResource(R.drawable.round_upload_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    Text(torrent.seeders.toString())
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Icon(
                                        imageVector = ImageVector.Companion.vectorResource(R.drawable.round_download_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    Text(torrent.leechers.toString())
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Icon(
                                        imageVector = ImageVector.Companion.vectorResource(R.drawable.round_calendar_month_24),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    Text(parseDate(torrent.updated_at))
                                }
                            }
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = ImageVector.Companion.vectorResource(R.drawable.round_download_24),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = defaultCardModifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    (release.value.episodes ?: emptyList()).reversed()
                        .forEachIndexed { index: Int, episode: LEpisode ->
                            val currentCardColor = if (index % 2 == 1) {
                                episodeCardColor
                            } else {
                                MaterialTheme.colorScheme.surfaceContainer
                            }
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = currentCardColor
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            "Эпизод ${if (episode.ordinal % 1.0 == 0.0) episode.ordinal.toInt() else episode.ordinal} ${if (episode.name != null) "• ${episode.name}" else ""}",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                        Text(
                                            "Обновлена ${parseDate(episode.updated_at)}",
                                            color = descriptionColor
                                        )
                                    }
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(Icons.Rounded.PlayArrow, contentDescription = null)
                                    }
                                }
                            }
                        }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
        AnimatedVisibility(
            visible = scrollState.value <= previewHeight.intValue,
            modifier = Modifier.align(Alignment.TopStart),
        ) {
            Column {
                Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                IconButton(
                    onClick = {
                        navController.popBackStack(route = releaseScreenRoute, inclusive = true)
                    }
                ) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                }
            }
        }
    }
}


@Deprecated("Deprecated")
fun longToDate(time: Long): String {
    val date = Date(time * 1000)
    val format = SimpleDateFormat("dd.MM.yyyy, HH:mm", java.util.Locale.ENGLISH)
    return format.format(date)
}

fun parseDate(dateString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.ENGLISH)
    val date = dateFormat.parse(dateString) ?: 0
    val format = SimpleDateFormat("dd.MM.yyyy, HH:mm", java.util.Locale.ENGLISH)
    return format.format(date)
}