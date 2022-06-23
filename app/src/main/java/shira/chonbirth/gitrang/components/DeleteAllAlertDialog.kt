package shira.chonbirth.gitrang.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun DeleteAllAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    sharedViewModel: SharedViewModel
){
    if (openDialog){

        LaunchedEffect(key1 = true){
            sharedViewModel.getBookmarkIds()
        }

        val bookmarkIds by sharedViewModel.bookmarkIds.collectAsState()

        AlertDialog(
            title = {
                    Text(
                        text = title,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold
                    )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                            Button(
                                onClick = {
                                    onYesClicked()
                                    closeDialog()
                                    sharedViewModel.deleteAll()
                                    bookmarkIds.forEach {
                                        sharedViewModel.addRemoveBookmark(gitId = it,bookmark = false)
                                    }
                                }
                            ) {
                                Text(text = "Yes")
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