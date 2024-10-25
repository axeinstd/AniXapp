package tech.axeinstd.anixapp.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.data.title.LFavoritesList
import tech.axeinstd.anilibria.data.title.LTitleList
import tech.axeinstd.anilibria.data.title.meta.LMeta
import tech.axeinstd.anilibria.data.title.meta.LPagination
import tech.axeinstd.anilibria.data.title.meta.LPaginationLinks
import tech.axeinstd.anilibria.enumerates.title.Sorting
import tech.axeinstd.anixapp.AniLibriaClient

class HomeTitleListViewModel: ViewModel() {
    private val _list = mutableStateOf(
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
    val list: State<LTitleList> = _list

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentPage = mutableIntStateOf(1)
    val currentPage: State<Int> = _currentPage

    init {
        viewModelScope.launch {
            println("STARTING")
            _isLoading.value = true
            val res: LTitleList = AniLibriaClient.releases(
                page = _currentPage.intValue,
                limit = 10,
                sorting = Sorting.RATING_DESC
            )
            _currentPage.intValue++
            _list.value = _list.value.copy(
                data = _list.value.data + res.data,
                meta = res.meta
            )
            println("COMPLETED")
            _isLoading.value = false
        }
    }

    suspend fun load() {
        _isLoading.value = true
        val res: LTitleList = AniLibriaClient.releases(
            page = _currentPage.intValue,
            limit = 10,
            sorting = Sorting.RATING_DESC
        )
        _currentPage.intValue++
        _list.value = _list.value.copy(
            data = _list.value.data + res.data,
            meta = res.meta
        )
        _isLoading.value = false
    }

}