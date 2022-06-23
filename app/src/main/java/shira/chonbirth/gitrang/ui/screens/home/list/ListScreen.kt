package shira.chonbirth.gitrang.ui.screens.home.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.MainListData
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ListScreen(
    sharedViewModel: SharedViewModel,
    navigateToContentScreen: (Int) -> Unit
) {

    LaunchedEffect(key1 = true){
        sharedViewModel.getList()
    }

    val gitrang by sharedViewModel.allList.collectAsState()
    val searchAppBarState by sharedViewModel.searchAppBarState

    val search by sharedViewModel.searchTasks.collectAsState()

    val listState = rememberLazyListState()
    val showButton = listState.firstVisibleItemIndex > 8
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        content = {
            if (searchAppBarState){
                SearchList(list = search, navigateToContentScreen = navigateToContentScreen, sharedViewModel = sharedViewModel)
            }else {
                MainList(gitrang = gitrang, navigateToContentScreen = navigateToContentScreen, sharedViewModel = sharedViewModel, listState = listState)
            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = showButton, enter = fadeIn(0f, animationSpec = tween(300)), exit = fadeOut(0f, animationSpec = tween(800))) {
                OutlinedButton(onClick = {
                    coroutineScope.launch { listState.scrollToItem(0) } },
                    modifier= Modifier.size(32.dp),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.Alpha50, backgroundColor = MaterialTheme.colors.GoTop),
                ) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Go to Top")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchList(list: List<MainListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel) {

    if (list.isEmpty() && sharedViewModel.searchTextState.value.isNotEmpty()){
       Box(modifier = Modifier
           .fillMaxWidth()
           .padding(top = 75.dp), contentAlignment = Alignment.Center) {
           Text(modifier = Modifier.clip(shape = Shapes.medium).background(MaterialTheme.colors.CategoryPill).padding(top = SMALL_PADDING, bottom = SMALL_PADDING, start = LARGEST_PADDING, end = LARGEST_PADDING),text = "No match found...", style = MaterialTheme.typography.subtitle1)
       } 
    }else {
        LazyColumn {
            items(
                items = list
            ) {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                    color = MaterialTheme.colors.background,
                    shape = RectangleShape,
                    elevation = TASK_ITEM_ELEVATION,
                    onClick = {
                        sharedViewModel.getSelectedItem(it.id)
                        navigateToContentScreen(it.id)
                    })
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .padding(start = LARGE_PADDING, end = SMALL_PADDING),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(48.dp)
                                    .background(
                                        color = MaterialTheme.colors.IdCircle,
                                        shape = CircleShape
                                    )
                                    .layout { measurable, constraints ->
                                        val placeable =
                                            measurable.measure(constraints = constraints)
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
                                    text = it.id.toString(),
                                    color = MaterialTheme.colors.IdColor,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(SMALL_PADDING))
                        Column(
                            modifier = Modifier.padding(
                                top = LARGE_PADDING,
                                end = LARGE_PADDING
                            )
                        ) {
                            Row(modifier = Modifier.padding(top = 6.dp)) {
                                Text(
                                    modifier = Modifier.weight(8f),
                                    text = it.title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.subtitle1
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f), contentAlignment = Alignment.CenterEnd
                                ) {

                                }
                                if (it.bookmark) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = LovelyPink
                                    )
                                } else {
                                    Canvas(
                                        modifier = Modifier
                                            .size(SMALL_PADDING)
                                            .weight(0.5f)
                                    ) {
                                        drawCircle(color = LovelyPink)
                                    }
                                }
                            }
                            val eng = it.english!!.length > 4
                            val eng1 = it.english1!!.length > 4
                            Text(
                                text = if (eng) {
                                    it.english
                                } else {
                                    ""
                                } + if (eng && eng1) {
                                    ", "
                                } else {
                                    ""
                                } + if (eng1) {
                                    it.english1
                                } else {
                                    ""
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.BlackWhite,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.ExtraLight
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainList(gitrang: List<MainListData>, navigateToContentScreen: (Int) -> Unit, sharedViewModel: SharedViewModel, listState: LazyListState){

    LazyColumn(state = listState){
        val grouped = gitrang.groupBy { it.category }
        grouped.forEach{(section, sectionItem) ->
            stickyHeader {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.StickyHeaderBackground, elevation = 4.dp) {
                    Text(modifier = Modifier.padding(10.dp), text = section, style = MaterialTheme.typography.caption, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.StickyHeaderContent)
                }
            }
            items(
                items = sectionItem,
                itemContent = {
                    ListItems(sharedViewModel = sharedViewModel, item = it, navigateToContentScreen = navigateToContentScreen)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItems(sharedViewModel: SharedViewModel, item: MainListData, navigateToContentScreen: (Int) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp),
        color = MaterialTheme.colors.background,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            sharedViewModel.getSelectedItem(item.id)
            navigateToContentScreen(item.id)
        }
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .padding(start = LARGE_PADDING, end = SMALL_PADDING), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
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
                            placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                        }
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.id.toString(), color = MaterialTheme.colors.IdColor, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(SMALL_PADDING))
            Column(modifier = Modifier.padding(top = LARGE_PADDING, end = LARGE_PADDING)) {
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Text(modifier = Modifier.weight(8f), text = item.title, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.subtitle1)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.CenterEnd){

                    }
                    if(item.bookmark){
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(18.dp), tint = LovelyPink)
                    }else{
                        Canvas(modifier = Modifier
                            .size(SMALL_PADDING)
                            .weight(0.5f)){
                            drawCircle(color = LovelyPink)
                        }
//                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(18.dp), tint = LovelyPink)
                    }
                }
                val eng = item.english!!.length > 4
                val eng1 = item.english1!!.length > 4
                Text(text = if (eng){item.english}else{""} + if(eng && eng1){", "}else{""} + if (eng1){item.english1}else{""}, maxLines = 1, overflow = TextOverflow.Ellipsis, color = MaterialTheme.colors.BlackWhite, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.ExtraLight)
            }
        }
    }
}