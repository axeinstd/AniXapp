package tech.axeinstd.anixapp.view_models

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anilibria.data.title.LTitleName
import tech.axeinstd.anilibria.data.title.genre.LGenre


class PreloadTitleInfo: ViewModel() {
    val releaseID = mutableIntStateOf(0)
    val preloadTitleInfo = mutableStateOf(
        LTitle(
            id = 0,
            name = LTitleName("null"),
            is_in_production = false,
            is_blocked_by_geo = false,
            episodes_are_unknown = false,
            is_blocked_by_copyrights = false,
            added_in_users_favorites = 0,
            genres = listOf(
                LGenre(
                    id = 0,
                    name = "null",
                    total_releases = 0
                )
            )
        )
    )
}