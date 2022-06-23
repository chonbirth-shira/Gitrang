package shira.chonbirth.gitrang.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import shira.chonbirth.arok.navigation.destinations.*
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.navigation.destinations.viewComposable
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN

@Composable
fun AppNavigation(
    navController: NavHostController,
    navControllerBotNav: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController){
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ){
        homeComposable(sharedViewModel = sharedViewModel, navControllerBotNav = navControllerBotNav, navigateToViewScreen = screen.view)
        viewComposable(sharedViewModel = sharedViewModel)
    }
}