package shira.chonbirth.gitrang.navigation.destinations.bottom

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.ui.screens.home.list.ApListScreen
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen

fun NavGraphBuilder.apListComposable(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit,
    navControllerMain: NavHostController
) {
    composable (
        route = "home"
    ){
        ApListScreen(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
    }
}