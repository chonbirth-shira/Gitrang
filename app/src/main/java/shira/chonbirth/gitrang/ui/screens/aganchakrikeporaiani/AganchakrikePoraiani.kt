package shira.chonbirth.gitrang.ui.screens.aganchakrikeporaiani

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import shira.chonbirth.gitrang.data.models.APData
import shira.chonbirth.gitrang.ui.theme.LARGEST_PADDING
import shira.chonbirth.gitrang.ui.theme.LARGE_PADDING
import shira.chonbirth.gitrang.ui.theme.MEDIUM_PADDING
import shira.chonbirth.gitrang.ui.theme.SMALL_PADDING
import shira.chonbirth.gitrang.util.RequestState
import shira.chonbirth.gitrang.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AganchakgrikePoraiani(sharedViewModel: SharedViewModel, navControllerMain: NavHostController){

    val selectedAP by sharedViewModel.selectedAP.collectAsState()
//    val item = (selectedAP as RequestState.Success<APData>).data

    val scrollState = rememberScrollState()
    val scrollBehavior1 = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold( modifier = Modifier.nestedScroll(scrollBehavior1.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (selectedAP is RequestState.Success){(selectedAP as RequestState.Success<APData>).data.id.toString()} else {"..."})
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navControllerMain.navigateUp()}
                    ){
                        Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }, scrollBehavior = scrollBehavior1
            )
        },
        content = {
            if (selectedAP is RequestState.Success){
                Surface(modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())) {
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        Text(
                            text = (selectedAP as RequestState.Success<APData>).data.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = LARGEST_PADDING, start = LARGE_PADDING, end = LARGE_PADDING)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        val d1 = (selectedAP as RequestState.Success<APData>).data.d1?.replace(oldValue = "\\n", newValue = "\n")
                        val j1 = (selectedAP as RequestState.Success<APData>).data.j1?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("1",d1,j1)

                        val d2 = (selectedAP as RequestState.Success<APData>).data.d2?.replace(oldValue = "\\n", newValue = "\n")
                        val j2 = (selectedAP as RequestState.Success<APData>).data.j2?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("2",d2,j2)

                        val d3 = (selectedAP as RequestState.Success<APData>).data.d3?.replace(oldValue = "\\n", newValue = "\n")
                        val j3 = (selectedAP as RequestState.Success<APData>).data.j3?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("3",d3,j3)

                        val d4 = (selectedAP as RequestState.Success<APData>).data.d4?.replace(oldValue = "\\n", newValue = "\n")
                        val j4 = (selectedAP as RequestState.Success<APData>).data.j4?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("4",d4,j4)

                        val d5 = (selectedAP as RequestState.Success<APData>).data.d5?.replace(oldValue = "\\n", newValue = "\n")
                        val j5 = (selectedAP as RequestState.Success<APData>).data.j5?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("5",d5,j5)

                        val d6 = (selectedAP as RequestState.Success<APData>).data.d6?.replace(oldValue = "\\n", newValue = "\n")
                        val j6 = (selectedAP as RequestState.Success<APData>).data.j6?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("6",d6,j6)

                        val d7 = (selectedAP as RequestState.Success<APData>).data.d7?.replace(oldValue = "\\n", newValue = "\n")
                        val j7 = (selectedAP as RequestState.Success<APData>).data.j7?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("7",d7,j7)

                        val d8 = (selectedAP as RequestState.Success<APData>).data.d8?.replace(oldValue = "\\n", newValue = "\n")
                        val j8 = (selectedAP as RequestState.Success<APData>).data.j8?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("8",d8,j8)

                        val d9 = (selectedAP as RequestState.Success<APData>).data.d9?.replace(oldValue = "\\n", newValue = "\n")
                        val j9 = (selectedAP as RequestState.Success<APData>).data.j9?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("9",d9,j9)

                        val d10 = (selectedAP as RequestState.Success<APData>).data.d10?.replace(oldValue = "\\n", newValue = "\n")
                        val j10 = (selectedAP as RequestState.Success<APData>).data.j10?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("10",d10,j10)

                        val d11 = (selectedAP as RequestState.Success<APData>).data.d11?.replace(oldValue = "\\n", newValue = "\n")
                        val j11 = (selectedAP as RequestState.Success<APData>).data.j11?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("11",d11,j11)

                        val d12 = (selectedAP as RequestState.Success<APData>).data.d12?.replace(oldValue = "\\n", newValue = "\n")
                        val j12 = (selectedAP as RequestState.Success<APData>).data.j12?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("12",d12,j12)

                        val d13 = (selectedAP as RequestState.Success<APData>).data.d13?.replace(oldValue = "\\n", newValue = "\n")
                        val j13 = (selectedAP as RequestState.Success<APData>).data.j13?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("13",d13,j13)

                        val d14 = (selectedAP as RequestState.Success<APData>).data.d14?.replace(oldValue = "\\n", newValue = "\n")
                        val j14 = (selectedAP as RequestState.Success<APData>).data.j14?.replace(oldValue = "\\n", newValue = "\n")
                        Combine("14",d14,j14)

                        Spacer(modifier = Modifier.size(30.dp))

                        val verse = (selectedAP as RequestState.Success<APData>).data.verse.replace(oldValue = "\\n", newValue = "\n")
                        Text(text = verse, style = MaterialTheme.typography.titleLarge, modifier = Modifier
                            .padding(start = LARGE_PADDING, end = LARGE_PADDING, bottom = LARGEST_PADDING)
                            .fillMaxWidth(), textAlign = TextAlign.Center, fontStyle = FontStyle.Italic)

                        Spacer(modifier = Modifier.height(100.dp))

                    }
                }
            } else{

            }
        }
    )
}

@Composable
fun Combine(SlNo: String, Dilgipa: String?, Jilma: String?){
    if (Dilgipa != null && Jilma != null){
        Row {
            Box(modifier = Modifier
                .fillMaxHeight().padding(start = 10.dp, top = 10.dp), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
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
                    Text(text = SlNo, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleMedium)
                }
            }
        Column(modifier = Modifier.padding(10.dp).clip(shape = RoundedCornerShape(20.dp)).background(color = MaterialTheme.colorScheme.primaryContainer).padding(5.dp)) {
            Dilgipa(Dilgipa)
            HorizontalDivider()
            Jilma(Jilma)
        }
    }
    }
}

@Composable
fun Dilgipa(text: String){
    Column(modifier = Modifier.padding(SMALL_PADDING).fillMaxWidth()) {
        Text(text = text, style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp), modifier = Modifier
            .padding(MEDIUM_PADDING).fillMaxWidth(), textAlign = TextAlign.Start, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun Jilma(text: String){
    Column(modifier = Modifier.padding(SMALL_PADDING).fillMaxWidth()) {
        Text(text = text, style = MaterialTheme.typography.titleLarge.copy(lineHeight = 28.sp), modifier = Modifier
            .padding(MEDIUM_PADDING).fillMaxWidth(), textAlign = TextAlign.Start, color = MaterialTheme.colorScheme.primary)
    }
}