package shira.chonbirth.arok.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.HomeScreen
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN

fun NavGraphBuilder.homeComposable(
    navControllerBotNav: NavHostController,
    sharedViewModel: SharedViewModel,
    navigateToViewScreen: (Int) -> Unit
) {
    composable (
        route = HOME_SCREEN
    ){
        HomeScreen(sharedViewModel = sharedViewModel, navControllerBotNav = navControllerBotNav, navigateToViewScreen = navigateToViewScreen)
    }
}