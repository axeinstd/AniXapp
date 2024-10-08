package tech.axeinstd.anixapp.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.data.title.schedule.LScheduleTitle
import tech.axeinstd.anilibria.enumerates.title.ScheduleType
import tech.axeinstd.anixapp.AniLibriaClient

class ScheduleViewModel: ViewModel() {
    private val _schedule = mutableStateOf(listOf<LScheduleTitle>())
    val schedule: State<List<LScheduleTitle>> = _schedule

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val res: List<LScheduleTitle> = AniLibriaClient.schedule(type = ScheduleType.WEEK)
            _schedule.value = res
            _isLoading.value = false
        }
    }
}