package shira.chonbirth.gitrang.ui.screens.home.bookmark

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import shira.chonbirth.gitrang.ui.theme.HeavyWhiteGray

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EmptyContent() {
    var boolean by remember { mutableStateOf(false)}
    LaunchedEffect(key1 = true){
        delay(100)
        boolean = true
    }
    AnimatedVisibility(visible = boolean, enter = fadeIn(animationSpec = tween(800))) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background), verticalArrangement = Arrangement.Center, Alignment.CenterHorizontally) {
        Icon(modifier = Modifier.size(100.dp), imageVector = Icons.Default.Favorite, contentDescription = null, tint = HeavyWhiteGray)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "No bookmark to show", color = HeavyWhiteGray, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h6.fontSize)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "All bookmark added will be shown here ", color = HeavyWhiteGray.copy(alpha = 0.6f), fontSize = MaterialTheme.typography.caption.fontSize)
    }
    }
}

@Composable
@Preview
private fun EmptyContentPreview() {
    EmptyContent()
}