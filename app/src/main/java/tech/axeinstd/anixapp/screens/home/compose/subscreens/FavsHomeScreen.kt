package tech.axeinstd.anixapp.screens.home.compose.subscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import tech.axeinstd.anilibri3.Libria
import tech.axeinstd.anilibri3.data.title.LPagination
import tech.axeinstd.anilibri3.data.title.LTitleList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavsHomeScreen(AniLibriaClient: Libria, sessionID: MutableState<String>, padding: PaddingValues) {
    val configuration = LocalConfiguration.current

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val currentPage = rememberSaveable { mutableIntStateOf(1) }

    val isLoading = rememberSaveable { mutableStateOf(false) }

    val titlesList = rememberSaveable {
        mutableStateOf(
            LTitleList(
                list = emptyList(),
                pagination = LPagination(
                    pages = 1,
                    current_page = 0,
                    items_per_page = 0,
                    total_items = 0
                )
            )
        )
    }

    val needLoadTitles by remember {
        derivedStateOf {
            (scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: 0) >= scrollState.layoutInfo.totalItemsCount - 2 && titlesList.value.pagination.pages > titlesList.value.pagination.current_page && !isLoading.value && sessionID.value != ""
        }
    }

    LaunchedEffect(needLoadTitles) {
        if (needLoadTitles || titlesList.value.list.isEmpty()) {
            coroutineScope.launch {
                isLoading.value = true
                val res: LTitleList = AniLibriaClient.getUserFavorites(
                    sessionID.value,
                    page = currentPage.intValue,
                    filter = "id,names,description,posters",
                    items_per_page = 50,
                )
                currentPage.intValue++
                titlesList.value = titlesList.value.copy(
                    list = titlesList.value.list + res.list,
                    pagination = res.pagination
                )
                isLoading.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") }
            )
        }
    ) { innerPadding ->
        val innerPaddingInScaffold = innerPadding
        if (sessionID.value != "") {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
            ) {
                item {
                    Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                }
                items(titlesList.value.list) { title ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        val descriptionColor = MaterialTheme.colorScheme.surface.copy(
                            red = MaterialTheme.colorScheme.surface.red + if (isSystemInDarkTheme()) 0.5f else -0.5f,
                            green = MaterialTheme.colorScheme.surface.green + if (isSystemInDarkTheme()) 0.5f else -0.5f,
                            blue = MaterialTheme.colorScheme.surface.blue + if (isSystemInDarkTheme()) 0.5f else -0.5f
                        )
                        Row (
                            modifier = Modifier.fillMaxSize()
                        ) {
                            SubcomposeAsyncImage(
                                model = title.posters?.getPosterUrl(),
                                loading = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.1.toFloat())
                                            .background(MaterialTheme.colorScheme.background)
                                    )
                                },
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("${title.names?.ru}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(title.description ?: "Описание не найдено", fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 11.sp, color = descriptionColor)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if (isLoading.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Rounded.Star, contentDescription = null, modifier = Modifier
                        .height(48.dp)
                        .width(48.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Авторизуйтесь для просмотра", fontWeight = FontWeight.Bold)
                    Text("избранных релизов", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}