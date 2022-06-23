package shira.chonbirth.gitrang.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.screens.view.ViewScreen
import shira.chonbirth.gitrang.util.Constants.GIT_ID
import shira.chonbirth.gitrang.util.Constants.VIEW_SCREEN

fun NavGraphBuilder.viewComposable(
    sharedViewModel: SharedViewModel
) {
    composable (
        route = VIEW_SCREEN,
        arguments = listOf(navArgument(GIT_ID){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
//        val gitId = navBackStackEntry.arguments!!.getInt(GIT_ID)
//        sharedViewModel.getSelectedItem(gitId)
        ViewScreen(sharedViewModel = sharedViewModel)
    }
}