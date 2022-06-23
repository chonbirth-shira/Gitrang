package shira.chonbirth.gitrang.ui.screens.home.bookmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.components.DeleteAllAlertDialog
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.theme.*


@OptIn(ExperimentalMaterialApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun BookmarkScreen(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit
) {

    LaunchedEffect(key1 = true){
        sharedViewModel.getAllBookmark()
    }

    val bookmarks by sharedViewModel.allBookmark.collectAsState()
    val count by sharedViewModel.getCount.collectAsState(initial = 0)

    var openDeleteDialog by sharedViewModel.openDeleteDialog

    DeleteAllAlertDialog(title = "Remove All Bookmarks?", message = "Are you sure you want to remove all Bookmarks? There is no going back after this action.", openDialog = openDeleteDialog , closeDialog = { sharedViewModel.openDeleteDialog.value = false }, onYesClicked = {}, sharedViewModel = sharedViewModel)

    Scaffold (
        topBar = {
//            BookmarkAppBar()
        },
        content = {
            if (count == 0){
                EmptyContent()
            }else {
                Items(bookmarks = bookmarks, navigateToContentScreen = navigateToContentScreen, sharedViewModel = sharedViewModel)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun Items(bookmarks: List<BookmarkListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel){
    LazyColumn {
        items(
            items = bookmarks,
            key = { item ->
                item.id
            }
        ){
            val dismissState = rememberDismissState()
            val degrees by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)

            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart){
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)

                    sharedViewModel.deleteBookmark(it)
                    sharedViewModel.addRemoveBookmark(it.id, false)
                }
            }

            var itemAppeared by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = true){
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        Item(navigateToContentScreen = navigateToContentScreen, item = it, sharedViewModel = sharedViewModel)
                    }
                )

            }

        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
        .padding(LARGEST_PADDING), contentAlignment = Alignment.CenterEnd){
        Icon(modifier = Modifier.rotate(degrees), imageVector = Icons.Filled.Delete, contentDescription = "Delete Icon", tint = Color.White)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Item(navigateToContentScreen: (Int) -> Unit, item: BookmarkListData, sharedViewModel: SharedViewModel) {
    Surface(modifier = Modifier
        .height(80.dp),
        color = MaterialTheme.colors.background,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            sharedViewModel.getSelectedItem(item.id)
            navigateToContentScreen(item.id)
        })
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = LARGE_PADDING, end = SMALL_PADDING),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(color = MaterialTheme.colors.IdCircle, shape = CircleShape)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints = constraints)
                            val currentHeight = placeable.height
                            var heightCircle = currentHeight
                            if (placeable.width > heightCircle) {
                                heightCircle = placeable.width
                            }
                            layout(heightCircle, heightCircle) {
                                placeable.placeRelative(
                                    0,
                                    (heightCircle - currentHeight) / 2
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.id.toString(),
                        color = MaterialTheme.colors.IdColor,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.width(SMALL_PADDING))
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                Column {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(SMALLEST_PADDING))
                    Box(
                        modifier = Modifier
                            .clip(shape = Shapes.medium)
                            .background(MaterialTheme.colors.CategoryPill)
                            .padding(SMALLEST_PADDING)
                    ) {
                        Text(text = item.category, style = MaterialTheme.typography.overline)
                    }
                }
            }
        }
    }
}