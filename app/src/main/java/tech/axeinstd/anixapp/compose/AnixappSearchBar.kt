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
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.LTitleList
import tech.axeinstd.anilibria.data.title.meta.LMeta
import tech.axeinstd.anilibria.data.title.meta.LPagination
import tech.axeinstd.anilibria.data.title.meta.LPaginationLinks
import tech.axeinstd.anixapp.AniLibriaClient
import tech.axeinstd.anixapp.releaseScreenRoute
import tech.axeinstd.anixapp.view_models.PreloadTitleInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    AniLibriaClient: AniLibria,
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
                data = emptyList(),
                meta = LMeta(
                    pagination = LPagination(
                        total = 0,
                        count = 0,
                        per_page = 0,
                        current_page = 0,
                        total_pages = 0,
                        links = LPaginationLinks()
                    )
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
                    searchedTitleList.value = AniLibriaClient.releases(
                        search = searchText.value,
                        limit = with(density) { screenHeight.toPx().toInt() / 70.dp.toPx().toInt() }
                    )
                }
            }
        },
        onSearch = {
            // isActiveSearch.value = false
            coroutineScope.launch {
                searchedTitleList.value = AniLibriaClient.releases(
                    search = searchText.value,
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
                                data = emptyList(),
                                meta = LMeta(
                                    pagination = LPagination(
                                        total = 0,
                                        count = 0,
                                        per_page = 0,
                                        current_page = 0,
                                        total_pages = 0,
                                        links = LPaginationLinks()
                                    )
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
        items(searchedTitleList.value.data) { title ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable {
                        preloadTitleInfo.releaseID.intValue = title.id
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
                        model = AniLibriaClient.baseUrl + title.poster?.src,
                        contentDescription = null,
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            title.name.main,
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