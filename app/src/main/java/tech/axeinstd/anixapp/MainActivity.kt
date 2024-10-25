package tech.axeinstd.anixapp

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.axeinstd.anixapp.ui.theme.AnixappTheme
import tech.axeinstd.anilibria.AniLibria
import tech.axeinstd.anixapp.screens.LoadingScreen
import tech.axeinstd.anixapp.screens.LoginScreen
import tech.axeinstd.anixapp.screens.home.compose.navigation.BottomNavBarItem
import tech.axeinstd.anixapp.screens.home.compose.subscreens.FavsHomeScreen
import tech.axeinstd.anixapp.screens.home.compose.subscreens.MainHomeScreen
import tech.axeinstd.anixapp.compose.Search
import tech.axeinstd.anixapp.data.storage.UserStorage
import tech.axeinstd.anixapp.screens.HomeScreen
import tech.axeinstd.anixapp.view_models.HomeTitleListViewModel
import tech.axeinstd.anixapp.view_models.ScheduleViewModel
import tech.axeinstd.anixapp.view_models.SplashScreen
import tech.axeinstd.anixapp.view_models.UserFavoritesViewModel

const val loadingScreenRoute: String = "loadingScreen"
const val loginScreenRoute: String = "loginScreen"
const val releaseScreenRoute: String = "releaseScreen"

val AniLibriaClient: AniLibria = AniLibria()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val context: Context = this
        val isDark = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES // check if sys in dark mode
        val splashScreenViewModel = SplashScreen().apply {
            setDark(isDark)
        }
        val splashScreen = installSplashScreen().setKeepOnScreenCondition {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)  {
                if (splashScreenViewModel.isDarkMode.value) {
                    splashScreen.setSplashScreenTheme(R.style.SplashScreenDark)
                } else {
                    splashScreen.setSplashScreenTheme(R.style.SplashScreenLight)
                }
            }
            splashScreenViewModel.isLoading.value
        }
        // MODELS & STORES INITIALIZATION
        val userDataStore = UserStorage(context)
        val homeTitleListViewModel = HomeTitleListViewModel()
        val favoritesViewModel = UserFavoritesViewModel()
        val scheduleViewModel = ScheduleViewModel()
        setContent {
            val navController = rememberNavController()
            val token = rememberSaveable { mutableStateOf("") }
            enableEdgeToEdge(
                navigationBarStyle = if (isSystemInDarkTheme()) SystemBarStyle.dark(
                    android.graphics.Color.TRANSPARENT
                ) else SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT
                )
            )
            AnixappTheme {
                HomeScreen(
                    navController = navController,
                    context = context,
                    userDataStore = userDataStore,
                    homeTitleListViewModel = homeTitleListViewModel,
                    favoritesViewModel = favoritesViewModel,
                    scheduleViewModel = scheduleViewModel,
                    token = token
                )
            }
        }
    }
}

