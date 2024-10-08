package tech.axeinstd.anixapp.view_models

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class SplashScreen: ViewModel() {
    private val _isDarkMode: MutableState<Boolean> = mutableStateOf(true)
    val isDarkMode: State<Boolean> = _isDarkMode

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    fun setDark(isDark: Boolean) {
        _isDarkMode.value = isDark
    }

    init {
        viewModelScope.launch {
            _isLoading.value = false
        }
    }
}