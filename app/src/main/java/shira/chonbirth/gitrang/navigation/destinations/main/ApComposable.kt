package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.ui.screens.aganchakrikeporaiani.AganchakgrikePoraiani
import shira.chonbirth.gitrang.util.Constants.AP_SCREEN
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

fun NavGraphBuilder.apComposable(
    sharedViewModel: SharedViewModel,
    navControllerMain: NavHostController
) {
    composable (
        route = AP_SCREEN
    ){
        AganchakgrikePoraiani(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
    }
}