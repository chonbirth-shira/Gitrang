package shira.chonbirth.gitrang.ui.screens.home.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.R
import shira.chonbirth.gitrang.components.ActionDialog
import shira.chonbirth.gitrang.data.models.MainListData
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.util.Constants.BK_SCREEN
import shira.chonbirth.gitrang.viewmodels.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit,
    navControllerMain: NavHostController
) {
//    val painter = rememberAsyncImagePainter(R.drawable.mesh)
//    AnimatedVisibility(visible = (state is ImagePainter.State.Loading)) {
//        CircularProgressIndicator()
//    }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = true){
        sharedViewModel.getAllAp()
        sharedViewModel.getList()
        sharedViewModel.getGroupList()
    }

    val gitrang by sharedViewModel.allList.collectAsState()
    var searchAppBarState by sharedViewModel.searchAppBarState

    val search by sharedViewModel.searchTasks.collectAsState()

    val listState = rememberLazyListState()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 10} }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var text by rememberSaveable { mutableStateOf("") }
    var groupPopUp by sharedViewModel.groupPopUp
    val count by sharedViewModel.getCount.collectAsState(0)
    var actionDialog by sharedViewModel.actionDialog
    val groupList by sharedViewModel.groupList.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    ActionDialog(openDialog = actionDialog, closeDialog = { sharedViewModel.actionDialog.value = false }, onYesClicked = {}, sharedViewModel = sharedViewModel, navigateToContentScreen = navigateToContentScreen, coroutineScope = coroutineScope, snackbarHostState = snackbarHostState)


    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).padding(bottom = 100.dp),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            if (searchAppBarState){

            }else{
                CenterAlignedTopAppBar(
                    title = {
                        ElevatedButton(modifier = Modifier.padding(start = SMALL_PADDING), onClick = {
                            searchAppBarState = true
                        }){
                            Row(modifier = Modifier.padding(SMALL_PADDING), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Search, contentDescription = null)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    Text(text = "Search Gitrang")
                                }
                            }
                        }
                    },
                    actions = {
                        TextButton( onClick = {
                            navControllerMain.navigate(BK_SCREEN)
                        }) {
                            BadgedBox( badge = {
                                if (count > 0){
                                    Badge {
                                        Text(text = count.toString(), modifier = Modifier.semantics { contentDescription = "$count bookmarks" })
                                    }
                                }
                            } ) {
                                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "bookmark")
                            }
                        }
                    },scrollBehavior = scrollBehavior)
            }
        },
        content = {
            if (searchAppBarState){
                LaunchedEffect(key1 = true){
                    sharedViewModel.getSearchTask(searchQuery = "")
                }
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = text,
                    onQueryChange = {
                        text = it
                        coroutineScope.launch {
                            sharedViewModel.getSearchTask(searchQuery = it)
                        } },
                    onSearch = { searchAppBarState = false },
                    active = searchAppBarState,
                    onActiveChange = { searchAppBarState = it },
                    placeholder = { Text("Search '36' or 'Chisol'") },
                    leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    trailingIcon = {
                        if (text.isNotEmpty()){
                            IconButton(onClick = {
                                text=""
                                sharedViewModel.getSearchTask(searchQuery = "")
                            }) {
                                Icon(Icons.Outlined.Clear, contentDescription = null)
                            }
                        }
                        if (text.isEmpty() && searchAppBarState == true){
                        IconButton(onClick = {searchAppBarState = false}) {
                            Icon(Icons.Outlined.Clear, contentDescription = null)
                        }}
                    }) {
                    SearchList(
                        list = search,
                        navigateToContentScreen = navigateToContentScreen,
                        sharedViewModel = sharedViewModel
                    )
                }
            } else {
                Surface(Modifier.padding(top = it.calculateTopPadding())) {
                    MainList(gitrang = gitrang, navigateToContentScreen = navigateToContentScreen, sharedViewModel = sharedViewModel, listState = listState)
                }
            }
            if (groupPopUp) {
                ModalBottomSheet(
                    onDismissRequest = {
                        sharedViewModel.groupPopUp.value = false
                    },
                    sheetState = sheetState
                ) {
                    var state = rememberLazyListState()
                    LazyColumn(state = state){
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(all = 5.dp), contentAlignment = Alignment.TopCenter){
                                Surface(modifier = Modifier.clip(RoundedCornerShape(30.dp)), color = MaterialTheme.colorScheme.tertiaryContainer) {
                                    Text(modifier = Modifier.padding(5.dp).padding(start = 10.dp, end = 10.dp), text = "Go to Group", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                }
                            }
                        }
                        items(groupList){
                            Card(modifier = Modifier.fillMaxWidth(), onClick = {
                                coroutineScope.launch { listState.scrollToItem(20) }
                                sharedViewModel.groupPopUp.value = false
                            }) {
                                ListItem(
                                    headlineContent = {
                                        Text(modifier = Modifier.padding(MEDIUM_PADDING), text = it)
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
                                                Icon(Icons.Outlined.Star, contentDescription = null)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = showButton,
                enter =  fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing), initialAlpha = 0f),
                exit = fadeOut(animationSpec = tween(500), targetAlpha = 10f)
//                , enter = fadeIn(1f, animationSpec = tween(300)), exit = fadeOut(0f, animationSpec = tween(800))
            ) {
                ExtendedFloatingActionButton(
                    onClick = { coroutineScope.launch { listState.scrollToItem(0) } },
                    icon = {
                        Icon(Icons.Outlined.KeyboardArrowUp, "Floating action button.")
                    },
                    text = {
                        Text(text = "Go to Top")
                    }
                )
            }
        }
    )
}

@Composable
fun SearchList(list: List<MainListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel) {
    LazyColumn {
            items(
                items = list
            ) {
                ListItems(sharedViewModel = sharedViewModel, item = it, navigateToContentScreen = navigateToContentScreen)
            }
        }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(gitrang: List<MainListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel, listState: LazyListState){
    LazyColumn(state = listState){
        val grouped = gitrang.groupBy { it.category }
        grouped.forEach{(section, sectionItem) ->
            stickyHeader {
                Box(modifier = Modifier.fillMaxWidth().padding(all = 5.dp), contentAlignment = Alignment.TopCenter){
                    Surface(modifier = Modifier.clip(RoundedCornerShape(30.dp)).clickable(onClick = {sharedViewModel.groupPopUp.value = true}), color = MaterialTheme.colorScheme.tertiaryContainer) {
                        Text(modifier = Modifier.padding(5.dp).padding(start = 10.dp, end = 10.dp), text = section, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }
            }
            items(
                items = sectionItem,
                itemContent = {
                    ListItems(sharedViewModel = sharedViewModel, item = it, navigateToContentScreen = navigateToContentScreen)
                }
            )
        }
        item {
            Spacer(Modifier.size(60.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItems(sharedViewModel: SharedViewModel, item: MainListData, navigateToContentScreen: (Int) -> Unit) {
    val eng = item.english!!.length > 4
    val eng1 = item.english1!!.length > 4
    Card(Modifier.combinedClickable(
        onClick = {
            sharedViewModel.getSelectedItem(item.id)
            navigateToContentScreen(item.id)
        },
        onLongClick = {
            sharedViewModel.title.value = item.title
            sharedViewModel.id.value = item.id.toString()
            sharedViewModel.bookmark1.value = item.bookmark
            sharedViewModel.category.value = item.category
            sharedViewModel.actionDialog.value = true
        })
    ) {
        ListItem(
            headlineContent = { Text(text = item.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            supportingContent = {
                Text(text = if (eng){item.english}else{""} + if(eng && eng1){" | "}else{""} + if (eng1){item.english1}else{""}, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
            },
            trailingContent = {
                if(item.bookmark){
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }
}