package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.bookmark.BookmarkScreen
import shira.chonbirth.gitrang.util.Constants.BK_SCREEN

fun NavGraphBuilder.bookmarkComposable(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit,
    navControllerMain: NavHostController
) {
    composable (
        route = BK_SCREEN,
    ){
        BookmarkScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToContentScreen, navControllerMain = navControllerMain)
    }
}