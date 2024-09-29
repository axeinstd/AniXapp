package tech.axeinstd.anixapp.screens.home.compose.subscreens

import android.content.pm.ModuleInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tech.axeinstd.anilibri3.Libria
import tech.axeinstd.anilibri3.data.title.schedule.LSchedule
import tech.axeinstd.anilibri3.data.title.schedule.LScheduleDay
import tech.axeinstd.anixapp.constants.mapDays

@Composable
fun ScheduleHomeScreen(AniLibriaClient: Libria, padding: PaddingValues) {
    val isLoading = rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val schedule = rememberSaveable {
        mutableStateOf(
            LSchedule(
                list = emptyList<LScheduleDay>()
            )
        )
    }

    LaunchedEffect(true) {
        if (schedule.value.list.isEmpty()) {
            coroutineScope.launch {
                isLoading.value = true
                val res: List<LScheduleDay> = AniLibriaClient.schedule(filter = "id,posters")
                schedule.value = LSchedule(list = res)
                isLoading.value = false
            }
        }
    }

    Scaffold { innerPadding ->
        val paddingInnerPadding = innerPadding
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding()),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
            ) {
                item {
                    Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                }

                items(schedule.value.list) { day ->
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
                            Text("${mapDays[day.day]}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(day.list) { title ->
                                SubcomposeAsyncImage(
                                    modifier = Modifier
                                        .height(110.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    model = title.posters?.getPosterUrl(),
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

                if (isLoading.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}