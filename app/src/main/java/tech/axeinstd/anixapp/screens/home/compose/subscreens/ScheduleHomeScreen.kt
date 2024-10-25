package tech.axeinstd.anixapp.screens.home.compose.subscreens

import android.content.pm.ModuleInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.isSensitiveHeader
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.schedule.LScheduleTitle
import tech.axeinstd.anilibria.enumerates.title.ScheduleType
import tech.axeinstd.anixapp.constants.mapDays
import tech.axeinstd.anixapp.releaseScreenRoute
import tech.axeinstd.anixapp.view_models.PreloadTitleInfo
import tech.axeinstd.anixapp.view_models.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleHomeScreen(
    AniLibriaClient: AniLibria,
    padding: PaddingValues,
    preloadTitleInfo: PreloadTitleInfo,
    navController: NavController,
    scheduleViewModel: ScheduleViewModel
) {

    /*val refreshState = rememberPullRefreshState(
        refreshing = scheduleViewModel.isLoading.value,
        onRefresh = { scheduleViewModel.refresh() })*/
    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing && !scheduleViewModel.isRefreshing.value) {
        scheduleViewModel.refresh(refreshState)
    }

    Scaffold { innerPadding ->
        val paddingInnerPadding = innerPadding
        if (scheduleViewModel.isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding()),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier.nestedScroll(refreshState.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = padding.calculateBottomPadding())
                ) {
                    item {
                        Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                    }

                    items(AniLibria.Utils.scheduleAsDaysList(scheduleViewModel.schedule.value)) { day ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(155.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Row {
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "${mapDays[day.day]}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(day.list) { title ->
                                    SubcomposeAsyncImage(
                                        modifier = Modifier
                                            .height(110.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable {
                                                preloadTitleInfo.releaseID.intValue =
                                                    title.release.id
                                                preloadTitleInfo.preloadTitleInfo.value =
                                                    title.release
                                                navController.navigate(releaseScreenRoute) {
                                                    popUpTo(releaseScreenRoute)
                                                }
                                            },
                                        model = AniLibriaClient.baseUrl + title.release.poster?.src,
                                        loading = {
                                            Box(
                                                modifier = Modifier.background(MaterialTheme.colorScheme.background)
                                            )
                                        },
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = refreshState,
                )
            }
        }
    }
}