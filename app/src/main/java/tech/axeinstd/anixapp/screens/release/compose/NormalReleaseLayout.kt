package tech.axeinstd.anixapp.screens.release.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import tech.axeinstd.anilibri3.data.title.LTitle
import tech.axeinstd.anilibri3.data.title.player.LTitleEpisode
import tech.axeinstd.anilibri3.data.title.player.LTitleEpisodes
import tech.axeinstd.anixapp.R
import tech.axeinstd.anixapp.releaseScreenRoute
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun NormalReleaseLayout(innerPadding: PaddingValues, release: MutableState<LTitle>, navController: NavController) {
    val defaultCardModifier = Modifier
        .fillMaxWidth(0.97f)
    println(release.value.torrents?.list?.size)
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
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).onGloballyPositioned { layoutCoordinates ->
                            previewHeight.intValue = layoutCoordinates.size.height
                        },
                        model = release.value.posters?.getPosterUrl(),
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
                        release.value.names?.ru ?: "Имя отсутствует",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(release.value.names?.en ?: "Английское имя отсутствует", fontSize = 10.sp)
                    Row {
                        Text("Сезон: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.season?.year} ${release.value.season?.string}")
                    }
                    Row {
                        Text("Тип: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.type?.string}")
                    }
                    Row {
                        Text("Жанры: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.genres}".slice(1.."${release.value.genres}".length - 2))
                    }
                    Row {
                        Text("Озвучка: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.team?.voice}".slice(1.."${release.value.team?.voice}".length - 2))
                    }
                    Row {
                        Text("Тайминг: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.team?.timing}".slice(1.."${release.value.team?.timing}".length - 2))
                    }
                    Row {
                        Text("Работа над субтитрами: ", fontWeight = FontWeight.Bold)
                        Text("${release.value.team?.translator}".slice(1.."${release.value.team?.translator}".length - 2))
                    }
                    Row {
                        Text("Состояние релиза: ", fontWeight = FontWeight.Bold)
                        Text(release.value.status?.string ?: "?")
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
                        .height(((release.value.torrents?.list?.size ?: 0) * 60).dp)
                        .wrapContentHeight()
                        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                    userScrollEnabled = false,
                ) {
                    items(release.value.torrents?.list ?: emptyList()) { torrent ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row {
                                    Text(
                                        "Серия ${torrent.episodes.string} ",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(torrent.quality.string, color = descriptionColor)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(torrent.size_string ?: "")
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
                                    Text(longToDate(torrent.uploaded_timestamp))
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
                    (release.value.player?.list?.values ?: emptyList()).reversed().forEachIndexed { index: Int, episode: LTitleEpisode ->
                        val currentCardColor = if (index % 2 == 1) {
                            episodeCardColor
                        } else {
                            MaterialTheme.colorScheme.surfaceContainer
                        }
                        Card (
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
                                    Text("Эпизод ${if ((episode.episode ?: 0.0) % 1.0 == 0.0) episode.episode.toInt() else episode.episode} • ${episode.name ?: " "}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                                    Text("Обновлена ${longToDate(episode.created_timestamp)}", color = descriptionColor)
                                }
                                TextButton(
                                    onClick = {},
                                    modifier = Modifier.width(90.dp)
                                ) {
                                    Text("Смотреть")
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

fun longToDate(time: Long): String {
    println(time)
    val date = Date(time * 1000)
    val format = SimpleDateFormat("dd.MM.yyyy, HH:mm", java.util.Locale.ENGLISH)
    return format.format(date)
}
