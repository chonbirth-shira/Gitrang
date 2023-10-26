package shira.chonbirth.gitrang

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.navigation.AppNavigation
import shira.chonbirth.gitrang.ui.theme.GitrangTheme
import shira.chonbirth.gitrang.viewmodels.SharedViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var navControllerMain: NavHostController
    private lateinit var navControllerBottom: NavHostController

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            GitrangTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()
//                SideEffect {
//                    systemUiController.setSystemBarsColor(color = VeryDarkGray, darkIcons = false)
//                }
                DisposableEffect(systemUiController, useDarkIcons) {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )

                    // setStatusBarColor() and setNavigationBarColor() also exist
                    onDispose {}
                }

                auth = Firebase.auth

                oneTapClient = Identity.getSignInClient(this)
                signInRequest = BeginSignInRequest.builder()
                    .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            // Your server's client ID, not your Android client ID.
                            .setServerClientId(getString(R.string.your_web_client_id))
                            // Only show accounts previously used to sign in.
                            .setFilterByAuthorizedAccounts(true)
                            .build())
                    // Automatically sign in when exactly one credential is retrieved.
                    .setAutoSelectEnabled(true)
                    .build()

                navControllerMain = rememberNavController()
//                val navBackStackEntryMain by navControllerMain.currentBackStackEntryAsState()
//                val currentRouteMain = navBackStackEntryMain?.destination?.route

                navControllerBottom = rememberNavController()
//                val navBackStackEntryBotNav by navControllerBottom.currentBackStackEntryAsState()
//                val currentRouteBotNav = navBackStackEntryBotNav?.destination?.route
//
//                val searchAppBarState by sharedViewModel.searchAppBarState
//                val searchTextState: String by sharedViewModel.searchTextState


                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                // A surface container using the 'background' color from the theme
                AppNavigation(
                    navControllerMain = navControllerMain,
                    navControllerBottom = navControllerBottom,
                    sharedViewModel = sharedViewModel
                )
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Column {
//                        Spacer(modifier = Modifier.size(100.dp))
//                        var booo by remember { mutableStateOf("Loading...") }
//                        Button(
//                            onClick = {
//
//                                oneTapClient.beginSignIn(signInRequest)
//                                    .addOnSuccessListener(Activity()) { result ->
//                                        try {
//                                            startIntentSenderForResult(
//                                                result.pendingIntent.intentSender, 2,
//                                                null, 0, 0, 0, null)
//                                            booo = "try"
//                                        } catch (e: IntentSender.SendIntentException) {
//                                            booo = "catch"
//                                        }
//                                    }.addOnFailureListener(Activity()) {
//                                        booo = "fail: "+it
//                                    }
//                            }
//                        ){
//                            Text(text = "Sign In")
//                        }
//                        Text(text = booo)
//                        AppNavigation(
//                            navControllerMain = navControllerMain,
//                            navControllerBottom = navControllerBottom,
//                            sharedViewModel = sharedViewModel,
//                            snackbarHostState = snackbarHostState
//                        )
            }
        }
    }
}