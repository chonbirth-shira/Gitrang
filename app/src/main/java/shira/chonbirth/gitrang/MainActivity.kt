package shira.chonbirth.gitrang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import shira.chonbirth.gitrang.navigation.AppNavigation
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.bookmark.BookmarkAppBar
import shira.chonbirth.gitrang.ui.screens.home.list.DefaultAppBar
import shira.chonbirth.gitrang.ui.screens.home.list.SearchAppBar
import shira.chonbirth.gitrang.ui.screens.view.ContentAppBar
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN
import shira.chonbirth.gitrang.util.Constants.VIEW_SCREEN
import shira.chonbirth.gitrang.ui.theme.GitrangTheme
import shira.chonbirth.gitrang.ui.theme.VeryDarkGray

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navControllerMain: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var navControllerBotNav: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitrangTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(color = VeryDarkGray, darkIcons = false)
                }

                navControllerMain = rememberAnimatedNavController()
                val navBackStackEntryMain by navControllerMain.currentBackStackEntryAsState()
                val currentRouteMain = navBackStackEntryMain?.destination?.route

                navControllerBotNav = rememberAnimatedNavController()
                val navBackStackEntryBotNav by navControllerBotNav.currentBackStackEntryAsState()
                val currentRouteBotNav = navBackStackEntryBotNav?.destination?.route

                val searchAppBarState by sharedViewModel.searchAppBarState
                val searchTextState: String by sharedViewModel.searchTextState

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold (
                        topBar = {
                            when (currentRouteMain) {
                                HOME_SCREEN -> {
                                    if (currentRouteBotNav == "home") {
                                        if (searchAppBarState) {
                                            SearchAppBar(
                                                sharedViewModel = sharedViewModel,
                                                text = searchTextState,
                                                onTextChange = {
                                                    sharedViewModel.searchTextState.value = it
                                                },
                                                onSearchClicked = {
                                                    sharedViewModel.getSearchTask(searchQuery = it)
                                                })
                                        } else {
                                            DefaultAppBar(sharedViewModel = sharedViewModel)
                                        }
                                    } else if (currentRouteBotNav == "bookmark") {
                                        BookmarkAppBar(sharedViewModel = sharedViewModel)
                                    }
                                }
                                VIEW_SCREEN -> {
                                    ContentAppBar(
                                        navHostController = navControllerMain,
                                        sharedViewModel = sharedViewModel
                                    )
                                }
                            }
                        },
                        content = {
                            AppNavigation(
                                navController = navControllerMain,
                                navControllerBotNav = navControllerBotNav,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    )
                }
            }
        }
    }
}