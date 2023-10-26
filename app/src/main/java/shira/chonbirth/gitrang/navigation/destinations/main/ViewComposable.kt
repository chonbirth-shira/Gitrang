package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.view.ViewScreen
import shira.chonbirth.gitrang.util.Constants.GIT_ID
import shira.chonbirth.gitrang.util.Constants.VIEW_SCREEN

fun NavGraphBuilder.viewComposable(
    navControllerMain: NavHostController,
    sharedViewModel: SharedViewModel
) {
    composable (
        route = VIEW_SCREEN,
//        arguments = listOf(navArgument(GIT_ID){
//            type = NavType.IntType
//        },
        enterTransition = {fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) }
    ){ navBackStackEntry ->
//        val gitId = navBackStackEntry.arguments!!.getInt(GIT_ID)
//        sharedViewModel.getSelectedItem(gitId)
        ViewScreen(navControllerMain = navControllerMain, sharedViewModel = sharedViewModel)
    }
}