package tech.axeinstd.anixapp.screens.release

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anilibria.data.title.LTitleName
import tech.axeinstd.anilibria.data.title.genre.LGenre
import tech.axeinstd.anixapp.screens.release.compose.LargeReleaseLayout
import tech.axeinstd.anixapp.screens.release.compose.NormalReleaseLayout
import tech.axeinstd.anixapp.view_models.PreloadTitleInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseScreen(AniLibriaClient: AniLibria, preloadTitleInfo: PreloadTitleInfo, navController: NavController) {
    val releleaseId = preloadTitleInfo.releaseID.intValue
    val release = rememberSaveable { mutableStateOf(
        LTitle(
            id = -1,
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
    ) }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val insets = WindowInsetsCompat.toWindowInsetsCompat(LocalView.current.rootWindowInsets)

    val leftInset = with(LocalDensity.current) { insets.getInsets(WindowInsetsCompat.Type.systemBars()).left.toDp() }
    val rightInset = with(LocalDensity.current) { insets.getInsets(WindowInsetsCompat.Type.systemBars()).right.toDp() }

    LaunchedEffect(release) {
        if (release.value.id == -1 && !isLoading.value) {
            coroutineScope.launch {
                isLoading.value = true
                release.value = AniLibriaClient.getTitleById(releleaseId)
                isLoading.value = false
            }
        }
    }

    Scaffold(
    ) { innerPadding ->
        val innerScaffoldPadding = innerPadding
        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                        model = AniLibriaClient.baseUrl + preloadTitleInfo.preloadTitleInfo.value.poster?.src,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LinearProgressIndicator()
                }
            }
        } else {
            if (screenWidth.value >= 700) {
                LargeReleaseLayout(
                    innerPadding = innerPadding,
                    release = release,
                    modifier = Modifier.padding(start = leftInset, end = rightInset)
                )
            } else {
                NormalReleaseLayout(
                    innerPadding = innerPadding,
                    release = release,
                    navController = navController
                )
            }
        }
    }
}
