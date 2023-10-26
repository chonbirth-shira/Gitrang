package shira.chonbirth.gitrang.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shira.chonbirth.gitrang.data.models.BookmarkListData
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun ActionDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
    navigateToContentScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){
    AnimatedVisibility(visible = openDialog, enter = fadeIn(animationSpec = tween(300)), exit = fadeOut(tween(300))){
        val id by sharedViewModel.id
        val title by sharedViewModel.title
        var boolean by sharedViewModel.bookmark1
        val bookmark = BookmarkListData(id = id.toInt(), title = title, category = sharedViewModel.category.value)
        AlertDialog(
            icon = {
                Box(contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints = constraints)
                            val currentHeight = placeable.height
                            var heightCircle = currentHeight
                            if (placeable.width > heightCircle) {
                                heightCircle = placeable.width
                            }
                            layout(heightCircle, heightCircle) {
                                placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                            }
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = sharedViewModel.id.value, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    }
                }
            },
            title = {
                    Text(modifier = Modifier.fillMaxWidth(), text = sharedViewModel.title.value, maxLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            },
            text = {
            },
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch{
                        sharedViewModel.getSelectedItem(id.toInt())
                        navigateToContentScreen(id.toInt())
                        sharedViewModel.actionDialog.value = false
                    }
                }){
                    Text(text = "Open")
                }

            },
            dismissButton = {
                if (boolean){
                    FilledTonalButton(onClick = {
                        coroutineScope.launch{
                            sharedViewModel.deleteBookmark(bookmark)
                            sharedViewModel.addRemoveBookmark(id.toInt(), false)
                            sharedViewModel.bookmark1.value = false
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "Git No. ${id} Removed!",
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
                    }){
                        Row(modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Remove from")
                            Spacer(Modifier.size(6.dp))
                            Icon(Icons.Outlined.Favorite, contentDescription = null)
                        }
                    }
                } else {
                    FilledTonalButton(onClick = {
                        coroutineScope.launch{
                            sharedViewModel.addBookmark(bookmark)
                            sharedViewModel.addRemoveBookmark(id.toInt(), true)
                            sharedViewModel.bookmark1.value = true
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "Git No. ${id} Added!",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    sharedViewModel.deleteBookmark(bookmark)
                                    sharedViewModel.addRemoveBookmark(bookmark.id, false)
                                }
                                SnackbarResult.Dismissed -> {
                                    /* Handle snackbar dismissed */
                                }
                            }
                        }
                    }){
                        Row(modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Add to")
                            Spacer(Modifier.size(6.dp))
                            Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
                        }
                    }
                }
            },
            onDismissRequest = {
                closeDialog()
            }
        )
    }
}