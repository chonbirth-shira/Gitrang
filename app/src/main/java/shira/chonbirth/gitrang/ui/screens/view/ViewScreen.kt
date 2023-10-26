package shira.chonbirth.gitrang.ui.screens.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.data.models.MainContentData
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.util.RequestState
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewScreen(navControllerMain: NavHostController, sharedViewModel: SharedViewModel){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior1 = TopAppBarDefaults.pinnedScrollBehavior()
    val selectedTask by sharedViewModel.selectedTask.collectAsState()
    var sharePopUp by sharedViewModel.sharePopUp
    val sheetState = rememberModalBottomSheetState()
    var bookmark by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true){
        sharedViewModel.getBookmarkIds()
        bookmark = sharedViewModel.bookmarkIds.value.contains((selectedTask as RequestState.Success<MainContentData>).data.id)
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior1.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            if (selectedTask is RequestState.Success){
                CenterAlignedTopAppBar(
                    title = {
                        Text((selectedTask as RequestState.Success<MainContentData>).data.id.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navControllerMain.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            sharedViewModel.sharePopUp.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = {
                            if (bookmark){
                                scope.launch {
                                    sharedViewModel.addRemoveBookmark(gitId = (selectedTask as RequestState.Success<MainContentData>).data.id, bookmark = false)
                                    sharedViewModel.deleteBookmark(BookmarkListData(id = (selectedTask as RequestState.Success<MainContentData>).data.id, title = (selectedTask as RequestState.Success<MainContentData>).data.title, category = (selectedTask as RequestState.Success<MainContentData>).data.category))
                                    bookmark = false
                                    val result = snackbarHostState
                                        .showSnackbar(
                                            message = "Removed from the Bookmark",
//                                     Defaults to SnackbarDuration.Short
                                            duration = SnackbarDuration.Short
                                        )
                                }
                            }else{
                                scope.launch {
                                    sharedViewModel.addRemoveBookmark(gitId = (selectedTask as RequestState.Success<MainContentData>).data.id, bookmark = true)
                                    sharedViewModel.addBookmark(BookmarkListData(id = (selectedTask as RequestState.Success<MainContentData>).data.id, title = (selectedTask as RequestState.Success<MainContentData>).data.title, category = (selectedTask as RequestState.Success<MainContentData>).data.category))
                                    bookmark = true
                                    val result = snackbarHostState
                                        .showSnackbar(
                                            message = "Added to the Bookmark",
//                                     Defaults to SnackbarDuration.Short
                                            duration = SnackbarDuration.Short
                                        )
                                }
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
        },
        content = {
            it.calculateTopPadding()
            MainContentView(sharedViewModel = sharedViewModel)
            if (sharePopUp) {
                ModalBottomSheet(
                    onDismissRequest = {
                        sharedViewModel.sharePopUp.value = false
                    },
                    sheetState = sheetState
                ) {
                    LazyColumn {
                        item {
                            Card(modifier = Modifier.padding(12.dp)) {
                                Column(modifier = Modifier.padding(MEDIUM_PADDING)) {
                                    Text(text = "Do you want to share this?", modifier = Modifier.padding(
                                        MEDIUM_PADDING) , style = MaterialTheme.typography.titleLarge)
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                        Button(onClick = {}){
                                            Text(text = "No")
                                        }
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Button(onClick = {}){
                                            Text(text = "Share")
                                        }
                                        Spacer(modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }
                        item {
                            Text(text = "${(selectedTask as RequestState.Success<MainContentData>).data.id} "
                                    +" ${(selectedTask as RequestState.Success<MainContentData>).data.title}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.english}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.english1}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.git_key}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para1}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.chorus}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para1}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.chorus}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para2}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para3}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para4}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para5}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para6}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para7}"
                                    +"\n"+"${(selectedTask as RequestState.Success<MainContentData>).data.para8}")
                        }
                        item {
                            Spacer(Modifier.size(60.dp))
                        }
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContentView(sharedViewModel: SharedViewModel) {

    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    if (selectedTask is RequestState.Success){
        val scrollState = rememberScrollState()
        Scaffold(
            content = {
                Surface(modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())) {
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        Spacer(modifier = Modifier.size(63.dp))
                        Text(
                            text = (selectedTask as RequestState.Success<MainContentData>).data.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = LARGEST_PADDING, start = LARGE_PADDING, end = LARGE_PADDING)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        val english = (selectedTask as RequestState.Success<MainContentData>).data.english ?: ""
                        if (english.length > 4){
                            Text(text = english, style = MaterialTheme.typography.titleLarge, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                        val english1 = (selectedTask as RequestState.Success<MainContentData>).data.english1 ?: ""
                        if (english1.length > 4){
                            Text(text = english1, style = MaterialTheme.typography.titleMedium, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING, bottom = LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center)
                        }

                        val verse = (selectedTask as RequestState.Success<MainContentData>).data.verse
                        if (verse.length > 4){
                            Text(text = verse, style = MaterialTheme.typography.titleSmall, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING, bottom = LARGEST_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)
                        }

                        val key = (selectedTask as RequestState.Success<MainContentData>).data.git_key ?: ""
                        if (key.length > 5){
                            Text(text = key, style = MaterialTheme.typography.titleMedium, modifier = Modifier
                                .padding(LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Start)
                        }

                        val para1 = (selectedTask as RequestState.Success<MainContentData>).data.para1?.replace(oldValue = "\\n", newValue = "\n")
                        if (para1 != null){
                            Paragraph(SlNo = "Pod 1", text = para1)
                        }

                        val chorus = (selectedTask as RequestState.Success<MainContentData>).data.chorus?.replace(oldValue = "\\n", newValue = "\n")
                        if (chorus!!.length > 5){
                            Chorus(text = chorus)
                        }

                        val para2 = (selectedTask as RequestState.Success<MainContentData>).data.para2?.replace(oldValue = "\\n", newValue = "\n")
                        if (para2!!.length > 5){
                            Paragraph(SlNo = "Pod 2", text = para2)
                        }

                        val para3 = (selectedTask as RequestState.Success<MainContentData>).data.para3?.replace(oldValue = "\\n", newValue = "\n")
                        if (para3!!.length > 5){
                            Paragraph(SlNo = "Pod 3", text = para3)
                        }

                        val para4 = (selectedTask as RequestState.Success<MainContentData>).data.para4?.replace(oldValue = "\\n", newValue = "\n")
                        if (para4!!.length > 5){
                            Paragraph(SlNo = "Pod 4", text = para4)
                        }

                        val para5 = (selectedTask as RequestState.Success<MainContentData>).data.para5?.replace(oldValue = "\\n", newValue = "\n")
                        if (para5!!.length > 5){
                            Paragraph(SlNo = "Pod 5", text = para5)
                        }

                        val para6 = (selectedTask as RequestState.Success<MainContentData>).data.para6?.replace(oldValue = "\\n", newValue = "\n")
                        if (para6!!.length > 5){
                            Paragraph(SlNo = "Pod 6", text = para6)
                        }

                        val para7 = (selectedTask as RequestState.Success<MainContentData>).data.para7?.replace(oldValue = "\\n", newValue = "\n")
                        if (para7!!.length > 5){
                            Paragraph(SlNo = "Pod 7", text = para7)
                        }

                        val para8 = (selectedTask as RequestState.Success<MainContentData>).data.para8?.replace(oldValue = "\\n", newValue = "\n")
                        if (para8!!.length > 5){
                            Paragraph(SlNo = "Pod 8", text = para8)
                        }

                        Spacer(modifier = Modifier.height(100.dp))

                    }
                }
            }
        )
    }
}

@Composable
fun Paragraph(SlNo: String, text: String){
    Surface(modifier = Modifier
        .padding(SMALL_PADDING)
        .fillMaxWidth()
        .wrapContentHeight(), shape = RoundedCornerShape(5.dp), color = MaterialTheme.colorScheme.primaryContainer) {
        Column {
            Box(modifier = Modifier
                .padding(LARGE_PADDING), contentAlignment = Alignment.Center) {
                Text(text = SlNo, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Text(text = text, style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp), modifier = Modifier
                .padding(
                    start = LARGE_PADDING,
                    bottom = LARGE_PADDING,
                    end = LARGE_PADDING
                )
                .fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun Chorus(text: String){
    Surface(modifier = Modifier
        .padding(SMALL_PADDING)
        .fillMaxWidth()
        .wrapContentHeight(), shadowElevation = 1.dp, shape = RoundedCornerShape(5.dp), color = MaterialTheme.colorScheme.tertiaryContainer) {
        Column {
            Box(modifier = Modifier
                .padding(LARGE_PADDING), contentAlignment = Alignment.Center) {
                Text(text = "Ring·taitaiani", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onTertiaryContainer)
            }
            Text(text = text, style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp), modifier = Modifier
                .padding(
                    start = LARGE_PADDING,
                    bottom = LARGE_PADDING,
                    end = LARGE_PADDING
                )
                .fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}