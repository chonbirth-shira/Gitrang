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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun DeleteItemAlertDialog(
    bookmark: BookmarkListData,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    sharedViewModel: SharedViewModel
){
    if (openDialog){
        AlertDialog(
            title = {
                    Text(text = "Remove Git No. ${bookmark.id} from Bookmark")
            },
            text = {
                Text(
                    text = "Are you sure you want to remove?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            sharedViewModel.deleteBookmark(bookmark)
                            sharedViewModel.addRemoveBookmark(bookmark.id, false)
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "Git No. ${bookmark.id} Removed!",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    sharedViewModel.addBookmark(bookmark)
                                    sharedViewModel.addRemoveBookmark(bookmark.id, true)
                                }
                                SnackbarResult.Dismissed -> {
                                    /* Handle snackbar dismissed */
                                }
                            }
                        }
                        closeDialog()
                    }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Row(Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                        Spacer(Modifier.width(5.dp))
                        Text(text = "Remove", color = MaterialTheme.colorScheme.onError)
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