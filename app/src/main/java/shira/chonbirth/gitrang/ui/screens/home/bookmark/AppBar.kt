package shira.chonbirth.gitrang.ui.screens.home.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.theme.HeavyWhiteGray
import shira.chonbirth.gitrang.ui.theme.VeryDarkGray

@Composable
fun BookmarkAppBar(sharedViewModel: SharedViewModel) {
    val count by sharedViewModel.getCount.collectAsState(0)
    Surface(modifier = Modifier.fillMaxWidth(), elevation = AppBarDefaults.TopAppBarElevation, color = VeryDarkGray) {
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)){
            Text(modifier = Modifier.align(Alignment.Center), text = "Bookmarks", style = MaterialTheme.typography.h5, color = Color.White)
            IconButton(onClick = { expanded = true }, modifier = Modifier.align(
                Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null, tint = HeavyWhiteGray)
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        sharedViewModel.openDeleteDialog.value = true
                    }, enabled = count != 0) {
                        Text(text = "Delete All", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}