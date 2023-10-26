package shira.chonbirth.gitrang.navigation

import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.util.Constants.AP_SCREEN
import shira.chonbirth.gitrang.util.Constants.AUTH_SCREEN
import shira.chonbirth.gitrang.util.Constants.BK_SCREEN
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN
import shira.chonbirth.gitrang.util.Constants.ST_SCREEN

class Screens (navControllerMain: NavHostController) {

    val main: () -> Unit = {
        navControllerMain.navigate(route = HOME_SCREEN) {
        }
    }

    val view: (Int) -> Unit = { gitId ->
        navControllerMain.navigate(route = "view/$gitId")
    }

    val ap: () -> Unit = {
        navControllerMain.navigate(route = AP_SCREEN){

        }
    }

//    val auth: () -> Unit = {
//        navControllerMain.navigate(route = AUTH_SCREEN){
//
//        }
//    }
    val bookmark: () -> Unit = {
        navControllerMain.navigate(route = BK_SCREEN){

        }
    }
    val setting: () -> Unit = {
        navControllerMain.navigate(route = ST_SCREEN){

        }
    }
}