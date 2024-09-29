package tech.axeinstd.anixapp.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import tech.axeinstd.anilibri3.Libria
import tech.axeinstd.anilibri3.data.title.LPagination
import tech.axeinstd.anilibri3.data.title.LTitleList
import tech.axeinstd.anixapp.releaseScreenRoute
import tech.axeinstd.anixapp.view_models.PreloadTitleInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    AniLibriaClient: Libria,
    modifier: Modifier = Modifier,
    screenHeight: Dp,
    isActiveSearch: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    preloadTitleInfo: PreloadTitleInfo,
    navController: NavController
) {
    val searchText = rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    val searchedTitleList = rememberSaveable {
        mutableStateOf(
            LTitleList(
                list = emptyList(),
                pagination = LPagination(
                    pages = 0,
                    current_page = 0,
                    items_per_page = 0,
                    total_items = 0
                )
            )
        )
    }

    SearchBar(
        modifier = modifier,
        query = searchText.value,
        onQueryChange = {
            searchText.value = it
            if (searchText.value != "") {
                coroutineScope.launch {
                    searchedTitleList.value = AniLibriaClient.search(
                        searchText.value,
                        filter = "id,names,posters,description",
                        limit = with(density) { screenHeight.toPx().toInt() / 70.dp.toPx().toInt() }
                    )
                }
            }
        },
        onSearch = {
            // isActiveSearch.value = false
            coroutineScope.launch {
                searchedTitleList.value = AniLibriaClient.search(
                    searchText.value,
                    filter = "id,names,posters,description",
                    limit = with(density) { screenHeight.toPx().toInt() / 70.dp.toPx().toInt() }
                )
            }
        },
        active = isActiveSearch.value,
        onActiveChange = {
            isActiveSearch.value = it
        },
        placeholder = { Text("Искать на АниЛибрии") },
        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Искать") },
        trailingIcon = {
            when {
                !isActiveSearch.value -> {
                    Icon(Icons.Rounded.Menu, contentDescription = "Больше")
                }

                isActiveSearch.value -> {
                    IconButton(
                        onClick = {
                            searchText.value = ""
                            searchedTitleList.value = LTitleList(
                                list = emptyList(),
                                pagination = LPagination(
                                    pages = 0,
                                    current_page = 0,
                                    items_per_page = 0,
                                    total_items = 0
                                )
                            )
                            isActiveSearch.value = false
                        }
                    ) {
                        Icon(Icons.Rounded.Close, contentDescription = "Закрыть")
                    }
                }
            }
        }
    ) {
        SearchTitleView(searchedTitleList, preloadTitleInfo, navController, isActiveSearch)
    }
}

@Composable
fun SearchTitleView(
    searchedTitleList: MutableState<LTitleList>,
    preloadTitleInfo: PreloadTitleInfo,
    navController: NavController,
    isActiveSearch: MutableState<Boolean>
) {

    val scrollState = rememberLazyListState()

    LazyColumn(
        state = scrollState
    ) {
        items(searchedTitleList.value.list) { title ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable {
                        preloadTitleInfo.releaseID.intValue = title.id ?: 9000
                        preloadTitleInfo.preloadTitleInfo.value = title
                        isActiveSearch.value = false
                        navController.navigate(releaseScreenRoute) {
                            popUpTo(releaseScreenRoute)
                        }
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SubcomposeAsyncImage(
                        model = title.posters?.getPosterUrl("original"),
                        contentDescription = null,
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            title.names?.ru ?: "Имя не найдено",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Text(
                            title.description ?: "Описание отсутствует",
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}