package shira.chonbirth.gitrang.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import shira.chonbirth.gitrang.ui.screens.home.bookmark.BookmarkScreen
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun HomeScreen(sharedViewModel : SharedViewModel, navControllerBotNav : NavHostController, navigateToViewScreen : (Int) -> Unit){
    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navControllerBotNav, sharedViewModel = sharedViewModel)
        },
        content = { padding ->
            NavHostContainer(navController = navControllerBotNav, padding = padding, sharedViewModel = sharedViewModel, navigateToViewScreen = navigateToViewScreen)
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavigationBar(navController: NavHostController, sharedViewModel: SharedViewModel) {

    BottomNavigation(
        elevation = BottomNavigationDefaults.Elevation,
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val count by sharedViewModel.getCount.collectAsState(0)

        BottomNavigationItem(
            selected = currentRoute == "home",
            onClick = { if (currentRoute != "home"){
                navController.navigate("home"){
                    popUpTo("bookmark") { inclusive = true }
                }
            }
            },
            icon = {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
            },
            label = {
                Text(text = "Home")
            },
            alwaysShowLabel = true
        )

        BottomNavigationItem(
            selected = currentRoute == "bookmark",
            onClick = { if (currentRoute != "bookmark") {
                navController.navigate("bookmark"){
                    popUpTo("home") { inclusive = true }
                }
            }
            },
            icon = {
                if ( count != 0){
                    BadgeBox( badgeContent = { Text(text = count.toString()) }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
                    }
                }else{
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
                }
            },
            label = {
                Text(text = "Bookmark")
            },
            alwaysShowLabel = true
        )
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    sharedViewModel: SharedViewModel,
    navigateToViewScreen: (Int) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            composable(route = "home") {
                ListScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToViewScreen)
            }
            composable(route = "bookmark") {
                BookmarkScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToViewScreen)
            }
        })
}