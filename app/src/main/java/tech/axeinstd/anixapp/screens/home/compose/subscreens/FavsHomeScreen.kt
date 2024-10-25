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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anixapp.view_models.UserFavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavsHomeScreen(
    AniLibriaClient: AniLibria,
    token: MutableState<String>,
    padding: PaddingValues,
    favoritesViewModel: UserFavoritesViewModel
) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val isLoading = rememberSaveable { mutableStateOf(false) }

    val descriptionColor = MaterialTheme.colorScheme.surface.copy(
        red = MaterialTheme.colorScheme.surface.red + if (isSystemInDarkTheme()) 0.5f else -0.5f,
        green = MaterialTheme.colorScheme.surface.green + if (isSystemInDarkTheme()) 0.5f else -0.5f,
        blue = MaterialTheme.colorScheme.surface.blue + if (isSystemInDarkTheme()) 0.5f else -0.5f
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") }
            )
        }
    ) { innerPadding ->
        val innerPaddingInScaffold = innerPadding
        if (token.value != "") {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
            ) {
                item {
                    Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()))
                }
                if (!favoritesViewModel.isLoading.value) {
                    items((favoritesViewModel.favorites.value.data as List<LTitle>).reversed()) { title ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {

                            Row(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                SubcomposeAsyncImage(
                                    model = AniLibriaClient.url + title.poster?.src,
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
                                    Text(
                                        title.name.main,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        title.description ?: "Описание не найдено",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 11.sp,
                                        lineHeight = 11.sp,
                                        color = descriptionColor
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
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
        } else {
            Box(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Rounded.Star, contentDescription = null, modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Авторизуйтесь для просмотра", fontWeight = FontWeight.Bold)
                    Text("избранных релизов", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}