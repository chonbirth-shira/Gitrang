package shira.chonbirth.gitrang.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import shira.chonbirth.gitrang.R
import shira.chonbirth.gitrang.ui.theme.Alpha50Light
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(sharedViewModel: SharedViewModel, navControllerMain: NavHostController){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val painter = rememberAsyncImagePainter(R.drawable.mesh)
    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     title = {
                         Text(text = "Settings")
                     }, scrollBehavior = scrollBehavior
                 )
        },
        content = {
            var boolean by remember { mutableStateOf(false) }
            LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding()).fillMaxSize()) {
//                item {
//                    Card(modifier = Modifier.padding(12.dp)) {
//                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Center) {
//                            Box(modifier = Modifier.size(width = 100.dp, height = 100.dp).clip(shape = RoundedCornerShape(100.dp))) {
//                                Image(
//                                    painter = painter,
//                                    contentDescription = "Forest Image",
//                                    modifier = Modifier.fillMaxSize(),
//                                    contentScale = ContentScale.Crop
//                                )
//                            }
//                            Spacer(Modifier.size(16.dp))
//                            Column{
//                                Text(text = "Creator:", style = MaterialTheme.typography.labelLarge)
//                                Text(text = "Chonbirth D. Shira", style = MaterialTheme.typography.titleLarge)
//                                Spacer(Modifier.size(8.dp))
//                                Text(text = "Published on:", style = MaterialTheme.typography.labelLarge)
//                                Text(text = "1/10/2023", style = MaterialTheme.typography.titleMedium)
//                            }
//                        }
//                    }
//                }
                item {
                    Card(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(width = 60.dp, height = 60.dp).clip(shape = RoundedCornerShape(100.dp))) {
                                        Image(
                                            painter = painter,
                                            contentDescription = "Profile pic",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(Modifier.size(16.dp))
                                    Column{
                                        Text(text = "Sign-in as:", style = MaterialTheme.typography.labelLarge)
                                        Text(text = "Chonbirth D. Shira", style = MaterialTheme.typography.titleLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                            TextButton(onClick = {}){
                                Text(text = "Sign out")
                            }
                        }
                    }
                }
                item {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column{
                                Text(text = "Data found:", style = MaterialTheme.typography.labelLarge)
                                Text(text = "HH:mm:ss", style = MaterialTheme.typography.titleLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            TextButton(onClick = {}){
                                Text(text = "Backup")
                            }
                        }
                        HorizontalDivider()
                    }
                }
                item {
                    ListItem(
                        headlineContent = {
                            Text(text = "Theme Mode Change")
                        },
                        supportingContent = {
                            Text(text = "Light mode/Dark mode")
                        },
                        trailingContent = {
                            Switch(checked = boolean, onCheckedChange = { if (boolean == false){boolean = true} else {boolean = false} })
                        }
                    )
                }
            }
        }
    )
}