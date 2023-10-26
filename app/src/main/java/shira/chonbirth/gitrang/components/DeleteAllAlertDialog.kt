package shira.chonbirth.gitrang.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun DeleteAllAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    sharedViewModel: SharedViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    bookmarks: List<BookmarkListData>
){
    if (openDialog){

        LaunchedEffect(key1 = true){
            sharedViewModel.getAllBookmark()
        }

        AlertDialog(
            title = {
                    Text(text = title)
            },
            text = {
                Text(
                    text = message
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                        sharedViewModel.deleteAll()
                        bookmarks.forEach {
                            sharedViewModel.addRemoveBookmark(gitId = it.id,bookmark = false)
                        }
                        scope.launch {
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "All Bookmarks Removed!",
                                    actionLabel = "Undo",
//                                     Defaults to SnackbarDuration.Short
                                    duration = SnackbarDuration.Short
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    bookmarks.forEach {
                                        sharedViewModel.addBookmark(it)
                                        sharedViewModel.addRemoveBookmark(gitId = it.id,bookmark = false)
                                    }
                                }
                                SnackbarResult.Dismissed -> {
                                    /* Handle snackbar dismissed */
                                }
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Row(Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                        Spacer(Modifier.width(5.dp))
                        Text(text = "Remove All", color = MaterialTheme.colorScheme.onError)
                    }
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialog()
                    }) {
                    Text(text = "No")
                }
            },
            onDismissRequest = {
                closeDialog()
            }
        )
    }
}