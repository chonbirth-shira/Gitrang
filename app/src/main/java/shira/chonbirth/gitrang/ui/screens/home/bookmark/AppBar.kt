package shira.chonbirth.gitrang.ui.screens.home.bookmark

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkAppBar(sharedViewModel: SharedViewModel, scrollBehavior: TopAppBarScrollBehavior) {
    val count by sharedViewModel.getCount.collectAsState(0)
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text("Bookmark", maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description"
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}){
                    DropdownMenuItem(text = { Text("Delete All") }, onClick = {
                        expanded = false
                        sharedViewModel.openDeleteDialog.value = true
                    }, enabled = count != 0)
                    DropdownMenuItem(text = { Text("Delete one by one") }, onClick = {
                        expanded = false
                        sharedViewModel.openDeleteDialog.value = true
                    }, enabled = count != 0)
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
//    Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = TOP_APP_BAR_HEIGHT, color = VeryDarkGray) {
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp)){
//            Text(modifier = Modifier.align(Alignment.Center), text = "Bookmarks", style = MaterialTheme.typography.titleLarge, color = Color.White)
//            IconButton(onClick = { expanded = true }, modifier = Modifier.align(
//                Alignment.CenterEnd)) {
//                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null, tint = HeavyWhiteGray)
//                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                    DropdownMenuItem(text = { Text(text = "Delete All", fontWeight = FontWeight.Bold) },
//                        onClick = {  expanded = false
//                            sharedViewModel.openDeleteDialog.value = true }, enabled = count != 0)
////                    DropdownMenuItem(onClick = {
////                        expanded = false
////                        sharedViewModel.openDeleteDialog.value = true
////                    }, enabled = count != 0) {
////                        Text(text = "Delete All", fontWeight = FontWeight.Bold)
////                    }
//                }
//            }
//        }
//    }
}