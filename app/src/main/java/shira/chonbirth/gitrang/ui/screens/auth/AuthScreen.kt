package shira.chonbirth.gitrang.ui.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.R
import shira.chonbirth.gitrang.util.Constants.HOME_SCREEN
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun AuthScreen(sharedViewModel: SharedViewModel, navControllerMain: NavHostController){


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
//                    oneTapSignInState.open()
                },
//                enabled = !oneTapSignInState.opened
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                    Spacer(Modifier.size(8.dp))
                    Text(text = "Sign in with Google")
                }
            }
            Spacer(Modifier.size(12.dp))
            TextButton(
                onClick = {
                    navControllerMain.navigate(HOME_SCREEN)
                }
            ){
                Text(text = "Skip for now")
            }
        }
    }
}