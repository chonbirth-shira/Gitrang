package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen

fun NavGraphBuilder.listComposable(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit
) {
    composable (
        route = "home"
    ){
        ListScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToContentScreen)
    }
}