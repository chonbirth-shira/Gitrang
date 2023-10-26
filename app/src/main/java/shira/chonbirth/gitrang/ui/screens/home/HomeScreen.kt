package shira.chonbirth.gitrang.ui.screens.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import shira.chonbirth.gitrang.ui.screens.SettingScreen
import shira.chonbirth.gitrang.ui.screens.aganchakrikeporaiani.AganchakgrikePoraiani
import shira.chonbirth.gitrang.ui.screens.home.bookmark.BookmarkScreen
import shira.chonbirth.gitrang.ui.screens.home.list.ApListScreen
import shira.chonbirth.gitrang.ui.screens.home.list.ListScreen
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun HomeScreen(sharedViewModel : SharedViewModel, navControllerBottom : NavHostController, navControllerMain : NavHostController, navigateToViewScreen : (Int) -> Unit){
    Scaffold (
        contentWindowInsets = WindowInsets(0.dp),
        content = {
            it.calculateTopPadding()
            NavHostContainer(navControllerBottom = navControllerBottom, navControllerMain = navControllerMain, sharedViewModel = sharedViewModel, navigateToViewScreen = navigateToViewScreen)
        },
        bottomBar = {
            BottomNavigationBar(navController = navControllerBottom, sharedViewModel = sharedViewModel)
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController, sharedViewModel: SharedViewModel) {

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
//        val count by sharedViewModel.getCount.collectAsState(0)

        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Gitrang") },
            label = { Text("Gitrang") },
            selected = currentRoute == "home",
            onClick = { if (currentRoute != "home"){
                navController.navigate("home"){
                    popUpTo("aplist") { inclusive = true }
                    popUpTo("setting") { inclusive = true }
                }}}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Star, contentDescription = "Gitrang") },
            label = { Text("Aganchakgrike Poraiani", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "aplist",
            onClick = { if (currentRoute != "aplist"){
                navController.navigate("aplist"){
                    popUpTo("home") { inclusive = true }
                    popUpTo("setting") { inclusive = true }
                }}}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Gitrang") },
            label = { Text("Settings", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "setting",
            onClick = { if (currentRoute != "setting"){
                navController.navigate("setting"){
                    popUpTo("aplist") { inclusive = true }
                    popUpTo("home") { inclusive = true }
                }}}
        )
//        NavigationBarItem(
//            icon = { BadgedBox( badge = {
//                if (count > 0){
//                    Badge {
//                        Text(text = count.toString(), modifier = Modifier.semantics { contentDescription = "$count bookmarks" })
//                    }
//                }
//            } ) {
//                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
//            }
//                   },
//            label = { Text("Bookmark") },
//            selected = currentRoute == "bookmark",
//            onClick = { if (currentRoute != "bookmark") {
//                navController.navigate("bookmark"){
//                    popUpTo("home") { inclusive = true }
//                }
//            }}
//        )
    }
//    BottomNavigation(
//        elevation = BottomNavigationDefaults.Elevation,
//        backgroundColor = MaterialTheme.colors.background
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        val count by sharedViewModel.getCount.collectAsState(0)
//
//        BottomNavigationItem(
//            selected = currentRoute == "home",
//            onClick = { if (currentRoute != "home"){
//                navController.navigate("home"){
//                    popUpTo("bookmark") { inclusive = true }
//                }
//            }
//            },
//            icon = {
//                Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
//            },
//            label = {
//                Text(text = "Home")
//            },
//            alwaysShowLabel = true
//        )
//
//        BottomNavigationItem(
//            selected = currentRoute == "bookmark",
//            onClick = { if (currentRoute != "bookmark") {
//                navController.navigate("bookmark"){
//                    popUpTo("home") { inclusive = true }
//                }
//            }
//            },
//            icon = {
//                if ( count != 0){
//                    BadgeBox( badgeContent = { Text(text = count.toString()) }) {
//                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
//                    }
//                }else{
//                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
//                }
//            },
//            label = {
//                Text(text = "Bookmark")
//            },
//            alwaysShowLabel = true
//        )
//    }
}

@Composable
fun NavHostContainer(
    navControllerMain: NavHostController,
    navControllerBottom: NavHostController,
    sharedViewModel: SharedViewModel,
    navigateToViewScreen: (Int) -> Unit
) {
    NavHost(
        navController = navControllerBottom,
        startDestination = "home",
        builder = {
            composable(route = "home") {
                ListScreen(sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToViewScreen, navControllerMain = navControllerMain)
            }
            composable(route = "aplist") {
                ApListScreen(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
            }
            composable(route = "setting") {
                SettingScreen(sharedViewModel = sharedViewModel, navControllerMain = navControllerMain)
            }
        })
}