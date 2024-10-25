package tech.axeinstd.anixapp.view_models

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.axeinstd.anilibria.data.title.schedule.LScheduleTitle
import tech.axeinstd.anilibria.enumerates.title.ScheduleType
import tech.axeinstd.anixapp.AniLibriaClient

@OptIn(ExperimentalMaterial3Api::class)
class ScheduleViewModel: ViewModel() {
    private val _schedule = mutableStateOf(listOf<LScheduleTitle>())
    val schedule: State<List<LScheduleTitle>> = _schedule

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isRefreshing: MutableState<Boolean> = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val res: List<LScheduleTitle> = AniLibriaClient.schedule(type = ScheduleType.WEEK)
            _schedule.value = res
            println("CCompletedSCH")
            _isLoading.value = false
        }
    }
    fun refresh(refreshState: PullToRefreshState) {
        if (!_isRefreshing.value) {
            viewModelScope.launch {
                _isRefreshing.value = true
                val res: List<LScheduleTitle> = AniLibriaClient.schedule(type = ScheduleType.WEEK)
                _schedule.value = res
                println("CCompletedSCH")
                _isRefreshing.value = false
                refreshState.endRefresh()
            }
        }
    }
}