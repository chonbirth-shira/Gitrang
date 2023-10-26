package shira.chonbirth.gitrang.ui.screens.home.bookmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.components.DeleteAllAlertDialog
import shira.chonbirth.gitrang.components.DeleteItemAlertDialog
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.viewmodels.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit,
    navControllerMain: NavHostController
) {
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllBookmark()
    }

    val bookmarks by sharedViewModel.allBookmark.collectAsState()
    val count by sharedViewModel.getCount.collectAsState(initial = 0)

    var openDeleteDialog by sharedViewModel.openDeleteDialog
    var openItemDeleteDialog by sharedViewModel.openItemDeleteDialog
    var bookmark by sharedViewModel.bookmark

    DeleteAllAlertDialog(title = "Remove All Bookmarks?", message = "Are you sure you want to remove all Bookmarks? There is no going back after this action.", openDialog = openDeleteDialog , closeDialog = { sharedViewModel.openDeleteDialog.value = false }, onYesClicked = {}, sharedViewModel = sharedViewModel, snackbarHostState = snackbarHostState, scope = scope, bookmarks = bookmarks)
    DeleteItemAlertDialog(bookmark = bookmark, openDialog = openItemDeleteDialog , closeDialog = { sharedViewModel.openItemDeleteDialog.value = false }, onYesClicked = {}, sharedViewModel = sharedViewModel, snackbarHostState = snackbarHostState, scope = scope)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            val count by sharedViewModel.getCount.collectAsState(0)
//            var expanded by remember { mutableStateOf(false) }
            CenterAlignedTopAppBar(
                title = {
                    Text("Bookmark", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                                 IconButton(onClick = {
                                     navControllerMain.navigateUp()
                                 }){
                                     Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                                 }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    AnimatedVisibility(visible = count >= 1){
                        PlainTooltipBox(tooltip = {}){
                            IconButton(onClick = {
                                openDeleteDialog = true
                            }) {

                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                if (count == 0){
                    EmptyContent()
                }else {
                    Items(bookmarks = bookmarks, navigateToContentScreen = navigateToContentScreen, sharedViewModel = sharedViewModel)
                }
            }
        }
    )
}

@Composable
fun Items(bookmarks: List<BookmarkListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel){
//    val state = rememberSwipeToDismissBoxState()
    LazyColumn {
        items(
            items = bookmarks,
            key = { item ->
                item.id
            }
        ){
            Itemz(navigateToContentScreen = navigateToContentScreen, item = it, sharedViewModel = sharedViewModel)
        }
    }
//            val dismissState = rememberDismissState()
//            val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)
//
//            val dismissDirection = dismissState.dismissDirection
//            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
//            if (isDismissed && dismissDirection == DismissDirection.EndToStart){
//                val scope = rememberCoroutineScope()
//                scope.launch {
//                    delay(300)
//
//                    sharedViewModel.deleteBookmark(it)
//                    sharedViewModel.addRemoveBookmark(it.id, false)
//                }
//            }
//
//            var itemAppeared by remember { mutableStateOf(false) }
//
//            LaunchedEffect(key1 = true){
//                itemAppeared = true
//            }
//
//            AnimatedVisibility(
//                visible = itemAppeared && !isDismissed,
//                enter = expandVertically(
//                    animationSpec = tween(
//                        durationMillis = 300
//                    )
//                ),
//                exit = shrinkVertically(
//                    animationSpec = tween(
//                        durationMillis = 300
//                    )
//                )
//            ) {
//
//                SwipeToDismiss(
//                    state = dismissState,
//                    directions = setOf(DismissDirection.EndToStart),
//                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
//                    background = { RedBackground(degrees = degrees) },
//                    dismissContent = {
//                        Item(navigateToContentScreen = navigateToContentScreen, item = it, sharedViewModel = sharedViewModel)
//                    }
//                )
//
//            }
//
//        }
//    }
//}
//
//@Composable
//fun RedBackground(degrees: Float) {
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .background(Color.Red)
//        .padding(LARGEST_PADDING), contentAlignment = Alignment.CenterEnd){
//        Icon(modifier = Modifier.rotate(degrees), imageVector = Icons.Filled.Delete, contentDescription = "Delete Icon", tint = Color.White)
//    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Itemz(navigateToContentScreen: (Int) -> Unit, item: BookmarkListData, sharedViewModel: SharedViewModel) {
    Card(Modifier.combinedClickable(
            onClick = {
                sharedViewModel.getSelectedItem(item.id)
                navigateToContentScreen(item.id)
            },
            onLongClick = {
                sharedViewModel.bookmark.value = item
                sharedViewModel.openItemDeleteDialog.value = true
            },
        )) {
        ListItem(
            headlineContent = { Text(text = item.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            supportingContent = {
                Text(text = item.category, maxLines = 1, overflow = TextOverflow.Ellipsis)
            },
            leadingContent = {
                Box(modifier = Modifier
                    .fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints = constraints)
                            val currentHeight = placeable.height
                            var heightCircle = currentHeight
                            if (placeable.width > heightCircle) {
                                heightCircle = placeable.width
                            }
                            layout(heightCircle, heightCircle) {
                                placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                            }
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item.id.toString(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        )
//        HorizontalDivider()
//    Card(
//        Modifier.combinedClickable(
//            onClick = {
//                sharedViewModel.getSelectedItem(item.id)
//                navigateToContentScreen(item.id)
//            },
//            onLongClick = {
//                sharedViewModel.bookmark.value = item
//                sharedViewModel.openItemDeleteDialog.value = true
//            },
//        )
//    ) {
//        Surface(
//            modifier = Modifier
//                .height(80.dp),
//            color = MaterialTheme.colorScheme.background,
////        shadowElevation = TASK_ITEM_ELEVATION,
////        onClick = {
////            sharedViewModel.getSelectedItem(item.id)
////            navigateToContentScreen(item.id)
////        },
//        )
//        {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .padding(start = 16.dp, end = 16.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(48.dp)
//                            .height(48.dp)
//                            .background(
//                                color = MaterialTheme.colorScheme.secondaryContainer,
//                                shape = CircleShape
//                            )
//                            .layout { measurable, constraints ->
//                                val placeable = measurable.measure(constraints = constraints)
//                                val currentHeight = placeable.height
//                                var heightCircle = currentHeight
//                                if (placeable.width > heightCircle) {
//                                    heightCircle = placeable.width
//                                }
//                                layout(heightCircle, heightCircle) {
//                                    placeable.placeRelative(
//                                        0,
//                                        (heightCircle - currentHeight) / 2
//                                    )
//                                }
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = item.id.toString(),
//                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.width(SMALL_PADDING))
//                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
//                    Column {
//                        Text(
//                            text = item.title,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            style = MaterialTheme.typography.titleLarge
//                        )
//                        Box(
//                            modifier = Modifier
////                            .clip(shape = Shapes.medium)
////                            .background(MaterialTheme.colorScheme.primary)
//                        ) {
//                            Text(
//                                text = item.category,
//                                color = MaterialTheme.colorScheme.primary,
//                                style = MaterialTheme.typography.titleSmall
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }
}
//@Composable
//@ExperimentalMaterial3Api
//fun SwipeToDismissListItems() {
//    val dismissState = rememberDismissState()
//    SwipeToDismiss(
//        state = dismissState,
//        background = {
//            val color by animateColorAsState(
//                when (dismissState.targetValue) {
//                    Default -> Color.LightGray
//                    DismissedToEnd -> Color.Green
//                    DismissedToStart -> Color.Red
//                }
//            )
//            Box(Modifier.fillMaxSize().background(color))
//        },
//        dismissContent = {
//            Card {
//                ListItem(
//                    headlineContent = {
//                        Text("Cupcake")
//                    },
//                    supportingContent = { Text("Swipe me left or right!") }
//                )
//                Divider()
//            }
//        }
//    )
//}