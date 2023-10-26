package shira.chonbirth.gitrang.ui.screens.home.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import shira.chonbirth.gitrang.R
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.util.Constants
import shira.chonbirth.gitrang.viewmodels.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApListScreen(
    sharedViewModel: SharedViewModel,
    navControllerMain: NavHostController
) {
//    val painter = rememberAsyncImagePainter(R.drawable.mesh)
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllAp()
    }
    val aplist by sharedViewModel.allAp.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).padding(bottom = 100.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Aganchakgrike Poraiani")
                },scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())){
                items(aplist){
                    Card(onClick = {
                        sharedViewModel.getSelectedAP(it.id)
                        navControllerMain.navigate(Constants.AP_SCREEN)
                    }) {
                        ListItem(
                            headlineContent = { Text(text = it.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            leadingContent = {
                                Box(modifier = Modifier
                                    .fillMaxHeight(), contentAlignment = Alignment.Center) {
                                    Box(modifier = Modifier
                                        .width(48.dp)
                                        .height(48.dp)
                                        .background(color = MaterialTheme.colorScheme.tertiaryContainer, shape = CircleShape)
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
                                        Text(text = it.id.toString(), color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.titleMedium)
                                    }
                                }
                            }
                        )
                    }
                }
                item {
                    Spacer(Modifier.padding(bottom = it.calculateBottomPadding()))
                }
            }
        }
    )
}