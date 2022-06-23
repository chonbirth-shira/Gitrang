package shira.chonbirth.gitrang.navigation

import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN

class Screens (navController: NavHostController) {

    val main: () -> Unit = {
        navController.navigate(route = HOME_SCREEN) {
        }
    }

    val view: (Int) -> Unit = { gitId ->
        navController.navigate(route = "view/$gitId")
    }
}