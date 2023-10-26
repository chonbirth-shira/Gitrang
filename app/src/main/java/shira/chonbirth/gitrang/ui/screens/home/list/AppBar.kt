package shira.chonbirth.gitrang.ui.screens.home.list

import android.app.appsearch.AppSearchManager.SearchContext
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.theme.HeavyWhiteGray
import shira.chonbirth.gitrang.ui.theme.TOP_APP_BAR_HEIGHT
import shira.chonbirth.gitrang.ui.theme.VeryDarkGray
import shira.chonbirth.gitrang.util.Constants.AP_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(sharedViewModel: SharedViewModel, scrollBehavior: TopAppBarScrollBehavior, navHostController: NavHostController){
    var expanded = remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text("Gitrang", maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
                DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false}){
                    DropdownMenuItem(text = { Text("Aganchakrike Poraiani") }, onClick = {
                          navHostController.navigate(AP_SCREEN)
//                        expanded.value = false
//                        sharedViewModel.openAPDialog.value = true
                    }, enabled = true)
                }
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { sharedViewModel.searchAppBarState.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
                  },
        scrollBehavior = scrollBehavior
    )
//    Surface(modifier = Modifier.fillMaxWidth(), color = VeryDarkGray, shadowElevation = TOP_APP_BAR_HEIGHT) {
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp)){
////            IconButton(onClick = {  }, modifier = Modifier.align(
////                Alignment.CenterStart)) {
////                Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, tint = HeavyWhiteGray)
////            }
//            Text(modifier = Modifier.align(Alignment.Center), text = "Gitrang", color = Color.White, style = MaterialTheme.typography.titleLarge)
//            IconButton(onClick = {sharedViewModel.searchAppBarState.value = true}, modifier = Modifier.align(
//                Alignment.CenterEnd)) {
//                Icon(imageVector = Icons.Outlined.Search, contentDescription = null, tint = HeavyWhiteGray)
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    sharedViewModel: SharedViewModel,
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
){
    val focusRequester = FocusRequester()
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(72.dp), shadowElevation = TOP_APP_BAR_HEIGHT, color = MaterialTheme.colorScheme.background) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .focusRequester(focusRequester = focusRequester)
            ,
            value = text,
            onValueChange = {
                onTextChange(it)
                onSearchClicked(it)
            },
            placeholder = { Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Search",
                color = HeavyWhiteGray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
            },
            textStyle = TextStyle(
                color = Color.LightGray,
                fontSize = MaterialTheme.typography.titleSmall.fontSize),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {  },
//                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = HeavyWhiteGray)
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (sharedViewModel.searchTextState.value.isNotEmpty()){
                        sharedViewModel.searchTextState.value = ""
                    }else{
                        sharedViewModel.searchAppBarState.value = false
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null, tint = HeavyWhiteGray)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions (
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
        LaunchedEffect(key1 = true){
            focusRequester.requestFocus()
        }
    }
}