package shira.chonbirth.gitrang.ui.screens.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.data.models.MainContentData
import shira.chonbirth.gitrang.ui.theme.HeavyWhiteGray
import shira.chonbirth.gitrang.ui.theme.VeryDarkGray
import shira.chonbirth.gitrang.util.RequestState
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentAppBar(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val scrollBehavior1 = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val selectedTask by sharedViewModel.selectedTask.collectAsState()
    var bookmark by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true){
        sharedViewModel.getBookmarkIds()
        bookmark = sharedViewModel.bookmarkIds.value.contains((selectedTask as RequestState.Success<MainContentData>).data.id)
    }

    if (selectedTask is RequestState.Success){
        CenterAlignedTopAppBar(
            title = {
                Text((selectedTask as RequestState.Success<MainContentData>).data.id.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis)
            },
            navigationIcon = {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = {
                    if (bookmark){
                        sharedViewModel.addRemoveBookmark(gitId = (selectedTask as RequestState.Success<MainContentData>).data.id, bookmark = false)
                        sharedViewModel.deleteBookmark(BookmarkListData(id = (selectedTask as RequestState.Success<MainContentData>).data.id, title = (selectedTask as RequestState.Success<MainContentData>).data.title, category = (selectedTask as RequestState.Success<MainContentData>).data.category))
                        bookmark = false
                    }else{
                        sharedViewModel.addRemoveBookmark(gitId = (selectedTask as RequestState.Success<MainContentData>).data.id, bookmark = true)
                        sharedViewModel.addBookmark(BookmarkListData(id = (selectedTask as RequestState.Success<MainContentData>).data.id, title = (selectedTask as RequestState.Success<MainContentData>).data.title, category = (selectedTask as RequestState.Success<MainContentData>).data.category))
                        bookmark = true
                    }
                }) {
                    Icon(
                        imageVector =
                        if (bookmark){
                            Icons.Outlined.Favorite
                        }else{
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            scrollBehavior = scrollBehavior1
        )
    }
}