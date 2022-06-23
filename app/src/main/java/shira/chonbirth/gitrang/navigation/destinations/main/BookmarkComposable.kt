package shira.chonbirth.gitrang.navigation.destinations.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.home.bookmark.BookmarkScreen

fun NavGraphBuilder.bookmarkComposable(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit
) {
    composable (
        route = "bookmark"
    ){
        BookmarkScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToContentScreen)
    }
}