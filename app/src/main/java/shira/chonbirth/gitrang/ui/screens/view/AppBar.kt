package shira.chonbirth.gitrang.ui.screens.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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

@Composable
fun ContentAppBar(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
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
        Surface(modifier = Modifier.fillMaxWidth(), elevation = AppBarDefaults.TopAppBarElevation, color = VeryDarkGray) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)){
                IconButton(onClick = { navHostController.navigateUp() }, modifier = Modifier.align(
                    Alignment.CenterStart)) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null, tint = HeavyWhiteGray)
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(80.dp),
                    text = (selectedTask as RequestState.Success<MainContentData>).data.id.toString(),
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis)
                Row(modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterEnd)) {
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
                            if (bookmark){
                                Icons.Outlined.Favorite
                            }else{
                                Icons.Outlined.FavoriteBorder
                            }
                            , contentDescription = null, tint = HeavyWhiteGray)
                    }
//                    IconButton(onClick = {
//                        val sendIntent: Intent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_TEXT, "*"+(selectedTask as RequestState.Success<MainContentData>).data.title +"*\n\n"+(selectedTask as RequestState.Success<MainContentData>).data.para1 +"\n"+ (selectedTask as RequestState.Success<MainContentData>).data.para2 )
//                            type = "text/plain"
//                        }
//
//                        val shareIntent = Intent.createChooser(sendIntent, null)
//                        ContextCompat.startActivity(context,shareIntent, Bundle())
//                    }) {
//                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share Button", tint = HeavyWhiteGray)
//                    }
                }
            }
        }
    }
}