package shira.chonbirth.gitrang.ui.screens.home.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shira.chonbirth.gitrang.viewmodels.SharedViewModel
import shira.chonbirth.gitrang.ui.theme.HeavyWhiteGray
import shira.chonbirth.gitrang.ui.theme.VeryDarkGray

@Composable
fun DefaultAppBar(sharedViewModel: SharedViewModel){
    Surface(modifier = Modifier.fillMaxWidth(), color = VeryDarkGray, elevation = AppBarDefaults.TopAppBarElevation) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)){
//            IconButton(onClick = {  }, modifier = Modifier.align(
//                Alignment.CenterStart)) {
//                Icon(imageVector = Icons.Outlined.Settings, contentDescription = null, tint = HeavyWhiteGray)
//            }
            Text(modifier = Modifier.align(Alignment.Center), text = "Gitrang", color = Color.White, style = MaterialTheme.typography.h5)
            IconButton(onClick = {sharedViewModel.searchAppBarState.value = true}, modifier = Modifier.align(
                Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null, tint = HeavyWhiteGray)
            }
        }
    }
}

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
        .height(72.dp), elevation = AppBarDefaults.TopAppBarElevation, color = VeryDarkGray) {
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
                style = MaterialTheme.typography.h6
            )
            },
            textStyle = TextStyle(
                color = Color.LightGray,
                fontSize = MaterialTheme.typography.h6.fontSize),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {  }, modifier = Modifier.alpha(ContentAlpha.disabled)) {
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
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
        LaunchedEffect(key1 = true){
            focusRequester.requestFocus()
        }
    }
}