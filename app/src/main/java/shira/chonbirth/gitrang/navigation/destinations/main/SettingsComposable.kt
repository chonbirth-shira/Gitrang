package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.ui.screens.SettingScreen
import shira.chonbirth.gitrang.ui.screens.home.list.ApListScreen
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen

fun NavGraphBuilder.settingScreenComposable(
    sharedViewModel: SharedViewModel,
    navControllerMain: NavHostController
) {
    composable (
        route = "settings"
    ){
        SettingScreen(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
    }
}