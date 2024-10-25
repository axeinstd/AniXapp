package tech.axeinstd.anixapp.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.data.title.LFavoritesList
import tech.axeinstd.anilibria.data.title.meta.LMeta
import tech.axeinstd.anilibria.data.title.meta.LPagination
import tech.axeinstd.anilibria.data.title.meta.LPaginationLinks
import tech.axeinstd.anixapp.AniLibriaClient

class UserFavoritesViewModel(): ViewModel() {
    private val _favorites = mutableStateOf(
        LFavoritesList(
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
    val favorites: State<LFavoritesList> = _favorites

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadFavorites(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val res: LFavoritesList = AniLibriaClient.getUserFavorites(token = token)
            _favorites.value = res
            _isLoading.value = false
        }
    }
}