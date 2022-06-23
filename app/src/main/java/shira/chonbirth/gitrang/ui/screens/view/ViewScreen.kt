package shira.chonbirth.gitrang.ui.screens.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shira.chonbirth.gitrang.data.models.MainContentData
import shira.chonbirth.gitrang.ui.theme.*
import shira.chonbirth.gitrang.util.RequestState
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@Composable
fun ViewScreen(sharedViewModel: SharedViewModel){
    Scaffold(
        content = {
            MainContentView(sharedViewModel = sharedViewModel)
        }
    )
}


@Composable
fun MainContentView(sharedViewModel: SharedViewModel) {

    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    if (selectedTask is RequestState.Success){
        val scrollState = rememberScrollState()
        Scaffold(
            content = {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        Text(
                            text = (selectedTask as RequestState.Success<MainContentData>).data.title,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(top = LARGEST_PADDING, start = LARGE_PADDING, end = LARGE_PADDING)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        val english = (selectedTask as RequestState.Success<MainContentData>).data.english ?: ""
                        if (english.length > 4){
                            Text(text = english, style = MaterialTheme.typography.h6, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                        val english1 = (selectedTask as RequestState.Success<MainContentData>).data.english1 ?: ""
                        if (english1.length > 4){
                            Text(text = english1, style = MaterialTheme.typography.subtitle1, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING, bottom = LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center)
                        }

                        val verse = (selectedTask as RequestState.Success<MainContentData>).data.verse
                        if (verse.length > 4){
                            Text(text = verse, style = MaterialTheme.typography.subtitle2, modifier = Modifier
                                .padding(start = LARGE_PADDING, end = LARGE_PADDING, bottom = LARGEST_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)
                        }

                        val key = (selectedTask as RequestState.Success<MainContentData>).data.git_key ?: ""
                        if (key.length > 5){
                            Text(text = key, style = MaterialTheme.typography.h6, modifier = Modifier
                                .padding(LARGE_PADDING)
                                .fillMaxWidth(), textAlign = TextAlign.Start)
                        }

                        val para1 = (selectedTask as RequestState.Success<MainContentData>).data.para1?.replace(oldValue = "\\n", newValue = "\n")
                        if (para1 != null){
                            Paragraph(SlNo = "Pod 1", text = para1)
                        }

                        val chorus = (selectedTask as RequestState.Success<MainContentData>).data.chorus?.replace(oldValue = "\\n", newValue = "\n")
                        if (chorus!!.length > 5){
                            Chorus(text = chorus)
                        }

                        val para2 = (selectedTask as RequestState.Success<MainContentData>).data.para2?.replace(oldValue = "\\n", newValue = "\n")
                        if (para2!!.length > 5){
                            Paragraph(SlNo = "Pod 2", text = para2)
                        }

                        val para3 = (selectedTask as RequestState.Success<MainContentData>).data.para3?.replace(oldValue = "\\n", newValue = "\n")
                        if (para3!!.length > 5){
                            Paragraph(SlNo = "Pod 3", text = para3)
                        }

                        val para4 = (selectedTask as RequestState.Success<MainContentData>).data.para4?.replace(oldValue = "\\n", newValue = "\n")
                        if (para4!!.length > 5){
                            Paragraph(SlNo = "Pod 4", text = para4)
                        }

                        val para5 = (selectedTask as RequestState.Success<MainContentData>).data.para5?.replace(oldValue = "\\n", newValue = "\n")
                        if (para5!!.length > 5){
                            Paragraph(SlNo = "Pod 5", text = para5)
                        }

                        val para6 = (selectedTask as RequestState.Success<MainContentData>).data.para6?.replace(oldValue = "\\n", newValue = "\n")
                        if (para6!!.length > 5){
                            Paragraph(SlNo = "Pod 6", text = para6)
                        }

                        val para7 = (selectedTask as RequestState.Success<MainContentData>).data.para7?.replace(oldValue = "\\n", newValue = "\n")
                        if (para7!!.length > 5){
                            Paragraph(SlNo = "Pod 7", text = para7)
                        }

                        val para8 = (selectedTask as RequestState.Success<MainContentData>).data.para8?.replace(oldValue = "\\n", newValue = "\n")
                        if (para8!!.length > 5){
                            Paragraph(SlNo = "Pod 8", text = para8)
                        }

                        Spacer(modifier = Modifier.height(100.dp))

                    }
                }
            }
        )
    }
}

@Composable
fun Paragraph(SlNo: String, text: String){
    Surface(modifier = Modifier
        .padding(SMALL_PADDING)
        .fillMaxWidth()
        .wrapContentHeight(), elevation = 1.dp, shape = RoundedCornerShape(5.dp), color = MaterialTheme.colors.Paragraph) {
        Column {
            Box(modifier = Modifier
                .padding(LARGE_PADDING), contentAlignment = Alignment.Center) {
                Text(text = SlNo, style = MaterialTheme.typography.body1, color = MaterialTheme.colors.Alpha50)
            }
            Text(text = text, style = MaterialTheme.typography.body2.copy(lineHeight = 35.sp), modifier = Modifier
                .padding(
                    start = LARGE_PADDING,
                    bottom = LARGE_PADDING,
                    end = LARGE_PADDING
                )
                .fillMaxWidth(), textAlign = TextAlign.Start)
//            Text(text = text, style = MaterialTheme.typography.h6.copy(lineHeight = 35.sp), modifier = Modifier
//                .padding(
//                    start = LARGE_PADDING,
//                    bottom = LARGE_PADDING,
//                    end = LARGE_PADDING
//                )
//                .fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun Chorus(text: String){
    Surface(modifier = Modifier
        .padding(SMALL_PADDING)
        .fillMaxWidth()
        .wrapContentHeight(), elevation = 1.dp, shape = RoundedCornerShape(5.dp), color = MaterialTheme.colors.Chorus) {
        Column {
            Box(modifier = Modifier
                .padding(LARGE_PADDING), contentAlignment = Alignment.Center) {
                Text(text = "Ring·taitaiani", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.Alpha50)
            }
            Text(text = text, style = MaterialTheme.typography.body2.copy(lineHeight = 35.sp), modifier = Modifier
                .padding(
                    start = LARGE_PADDING,
                    bottom = LARGE_PADDING,
                    end = LARGE_PADDING
                )
                .fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}