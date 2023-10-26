package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shira.chonbirth.gitrang.ui.screens.aganchakrikeporaiani.AganchakgrikePoraiani
import shira.chonbirth.gitrang.ui.screens.auth.AuthScreen
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.view.ViewScreen
import shira.chonbirth.gitrang.util.Constants.AP_ID
import shira.chonbirth.gitrang.util.Constants.AP_SCREEN
import shira.chonbirth.gitrang.util.Constants.AUTH_SCREEN
import shira.chonbirth.gitrang.util.Constants.GIT_ID
import shira.chonbirth.gitrang.util.Constants.VIEW_SCREEN

fun NavGraphBuilder.authComposable(
    sharedViewModel: SharedViewModel,
    navControllerMain: NavHostController
) {
    composable (
        route = AUTH_SCREEN
    ){
        AuthScreen(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
    }
}