package shira.chonbirth.gitrang.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import shira.chonbirth.gitrang.navigation.destinations.main.apComposable
import shira.chonbirth.gitrang.navigation.destinations.main.authComposable
import shira.chonbirth.gitrang.navigation.destinations.main.bookmarkComposable
import shira.chonbirth.gitrang.navigation.destinations.main.homeComposable
import shira.chonbirth.gitrang.navigation.destinations.main.viewComposable
import shira.chonbirth.gitrang.util.Constants.AUTH_SCREEN
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun AppNavigation(
    navControllerMain: NavHostController,
    navControllerBottom: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navControllerMain){
        Screens(navControllerMain = navControllerMain)
    }
    NavHost( navController = navControllerMain, startDestination = AUTH_SCREEN){
        homeComposable(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain, navControllerBottom = navControllerBottom, navigateToViewScreen = screen.view)
        viewComposable(navControllerMain = navControllerMain, sharedViewModel = sharedViewModel)
        apComposable(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
        authComposable(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
        bookmarkComposable(sharedViewModel = sharedViewModel, navigateToContentScreen = screen.view, navControllerMain = navControllerMain)
    }
}