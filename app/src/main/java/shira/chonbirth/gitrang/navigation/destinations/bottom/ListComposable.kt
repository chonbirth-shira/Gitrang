package shira.chonbirth.gitrang.navigation.destinations.bottom

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen

fun NavGraphBuilder.listComposable(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit,
    navControllerMain: NavHostController
) {
    composable (
        route = "home"
    ){
        ListScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToContentScreen, navControllerMain = navControllerMain)
    }
}