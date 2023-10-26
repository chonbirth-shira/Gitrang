package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.HomeScreen
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN

fun NavGraphBuilder.homeComposable(
    navControllerMain: NavHostController,
    navControllerBottom: NavHostController,
    sharedViewModel: SharedViewModel,
    navigateToViewScreen: (Int) -> Unit
) {
    composable (
        route = HOME_SCREEN,
        enterTransition = {fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) }
    ){
        HomeScreen(sharedViewModel = sharedViewModel, navControllerBottom = navControllerBottom, navigateToViewScreen = navigateToViewScreen, navControllerMain = navControllerMain)
    }
}