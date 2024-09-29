package tech.axeinstd.anixapp.view_models

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import tech.axeinstd.anilibri3.data.title.LTitle

class PreloadTitleInfo: ViewModel() {
    val releaseID = mutableIntStateOf(0)
    val preloadTitleInfo = mutableStateOf(LTitle())
}