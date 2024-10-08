package tech.axeinstd.anixapp.screens.release.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowInsetsCompat
import coil.compose.SubcomposeAsyncImage
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anilibria.data.title.LTitle
import tech.axeinstd.anixapp.AniLibriaClient


@Composable
fun LargeReleaseLayout(release: MutableState<LTitle>, innerPadding: PaddingValues, modifier: Modifier) {
    Row {
        InformationColumn(innerPadding, release, modifier)
    }
}

@Composable
fun InformationColumn(innerPadding: PaddingValues, release: MutableState<LTitle>, modifier: Modifier) {
    LazyRow(
        modifier = modifier
    ) {
        item {
            LazyColumn(
            ) {
                item {
                    Spacer(
                        modifier = Modifier.height(innerPadding.calculateTopPadding())
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = AniLibriaClient.url + release.value.poster?.src,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}