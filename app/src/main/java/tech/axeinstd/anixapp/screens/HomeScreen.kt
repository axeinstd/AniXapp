package tech.axeinstd.anixapp.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import tech.axeinstd.anixapp.AniLibriaClient
import tech.axeinstd.anixapp.R
import tech.axeinstd.anixapp.compose.Search
import tech.axeinstd.anixapp.data.storage.UserStorage
import tech.axeinstd.anixapp.loadingScreenRoute
import tech.axeinstd.anixapp.loginScreenRoute
import tech.axeinstd.anixapp.releaseScreenRoute
import tech.axeinstd.anixapp.screens.home.compose.navigation.BottomNavBarItem
import tech.axeinstd.anixapp.screens.home.compose.subscreens.FavsHomeScreen
import tech.axeinstd.anixapp.screens.home.compose.subscreens.MainHomeScreen
import tech.axeinstd.anixapp.screens.home.compose.subscreens.ProfileHomeScreen
import tech.axeinstd.anixapp.screens.home.compose.subscreens.ScheduleHomeScreen
import tech.axeinstd.anixapp.screens.release.ReleaseScreen
import tech.axeinstd.anixapp.view_models.PreloadTitleInfo

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    val navBarItems = listOf(
        BottomNavBarItem(
            title = "Главная",
            selectedIcon = Icons.Rounded.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "mainHomeScreen"
        ),
        BottomNavBarItem(
            title = "Избранное",
            selectedIcon = Icons.Rounded.Star,
            unselectedIcon = ImageVector.Companion.vectorResource(R.drawable.round_star_outline_24),
            route = "favsHomeScreen"
        ),
        BottomNavBarItem(
            title = "Расписание",
            selectedIcon = Icons.Rounded.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
            route = "scheduleHomeScreen"
        ),
        BottomNavBarItem(
            title = "Профиль",
            selectedIcon = Icons.Rounded.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "profileHomeScreen"
        )
    )
    val currentItemIndex = rememberSaveable { mutableIntStateOf(0) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val token = rememberSaveable { mutableStateOf("") } // "UjXG6Vteqjh4EBsGXDNtFaEKGHfqBrUk"

    val preloadTitleInfoModel: PreloadTitleInfo = viewModel()

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val isTopLevelScreen = when (navBackStackEntry.value?.destination?.route) {
        releaseScreenRoute -> true
        loginScreenRoute -> true
        else -> false
    }


    val needToHideNavBar = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val isActiveSearch = rememberSaveable { mutableStateOf(false) }
    val searchBar = @Composable {
        Search(
            AniLibriaClient = AniLibriaClient,
            screenHeight = screenHeight,
            modifier = Modifier.fillMaxWidth(),
            isActiveSearch = isActiveSearch,
            navController = navController,
            preloadTitleInfo = preloadTitleInfoModel
        )
    }

    val userDataStore = UserStorage(context)

    LaunchedEffect(key1 = true) {
        userDataStore.getUserId().collect { sessionid ->
            if (sessionid == null || sessionid == "") {
                userDataStore.getIfNeedToShowLoginScreen().collect { needToShow ->
                    if (needToShow == null || needToShow) {
                        navController.navigate(loginScreenRoute) {
                            popUpTo(loginScreenRoute) {
                                saveState = true
                            }
                        }
                    }
                }
            } else {
                token.value = sessionid
            }
        }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = currentItemIndex.intValue == 0 && !isTopLevelScreen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                searchBar()
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isActiveSearch.value && !isTopLevelScreen,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                NavigationBar(
                    modifier = Modifier,
                ) {
                    navBarItems.fastForEachIndexed { index, bottomNavBarItem ->
                        NavigationBarItem(
                            selected = currentItemIndex.intValue == index,
                            label = { Text(bottomNavBarItem.title) },
                            icon = {
                                if (index == currentItemIndex.intValue) {
                                    Icon(
                                        bottomNavBarItem.selectedIcon,
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        bottomNavBarItem.unselectedIcon,
                                        contentDescription = null
                                    )
                                }
                            },
                            onClick = {
                                currentItemIndex.intValue = index
                                navController.navigate(bottomNavBarItem.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val padding = innerPadding

        NavHost(
            navController = navController,
            startDestination =  navBarItems[0].route,
            enterTransition = {
                fadeIn(tween(250))
            },
            exitTransition = {
                fadeOut(tween(250))
            },
            popEnterTransition = {
                fadeIn(tween(250))
            },
            popExitTransition = {
                fadeOut(tween(250))
            }
        ) {
            composable(loadingScreenRoute) {
                LoadingScreen() {
                    navController.navigate("")
                }
            }
            composable(loginScreenRoute) {
                LoginScreen(
                    AniLibriaClient = AniLibriaClient,
                    onComplete = { sessionid ->
                        coroutineScope.launch {
                            userDataStore.saveUserId(sessionid)
                            token.value = sessionid
                            navController.navigate(navBarItems[0].route) {
                                popUpToRoute
                            }
                        }
                    },
                    onDismiss = {   needToShowLoginScreen ->
                        coroutineScope.launch {
                            userDataStore.setShowLoginScreen(needToShowLoginScreen)
                            navController.navigate(navBarItems[0].route) {
                                popUpToRoute
                            }
                        }
                    }
                )
            }

            composable(navBarItems[0].route) {
                MainHomeScreen(
                    AniLibriaClient = AniLibriaClient,
                    token = token,
                    padding = innerPadding,
                    navController = navController,
                    preloadTitleInfoModel
                )
            }
            composable(navBarItems[1].route) {
                FavsHomeScreen(
                    AniLibriaClient = AniLibriaClient,
                    token = token,
                    padding = innerPadding
                )
            }

            composable(navBarItems[2].route) {
                ScheduleHomeScreen(
                    AniLibriaClient = AniLibriaClient,
                    padding = innerPadding,
                    preloadTitleInfoModel,
                    navController
                )
            }
            composable(navBarItems[3].route) {
                ProfileHomeScreen(
                    padding = innerPadding
                )
            }
            composable(releaseScreenRoute) {
                ReleaseScreen(
                    AniLibriaClient = AniLibriaClient,
                    preloadTitleInfoModel,
                    navController
                )
            }
        }
    }
}
