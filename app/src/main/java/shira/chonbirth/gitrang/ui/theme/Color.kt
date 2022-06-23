package shira.chonbirth.gitrang.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Colors.BlackWhite: Color
    @Composable
    get() = if(isLight) Color.LightGray else Grey

val VeryDarkGray = Color(0xFF282828)
val HeavyWhiteGray = Color(0xFFCCCCCC)
val LovelyPink = Color(0xFFFF6C9E)
val PleasentBlue = Color(0xFF80D8FF)
val AlmostWhite = Color(0xFFF3F3F3)
val AlmostBlack = Color(0xFF1B1B1B)
val Grey = Color(0xFF5F5F5F)
val LightGrey = Color(0xFFF0F0F0)
val para = Color(0xFF80D8FF)
val chorus1 = Color(0xFFB9F6CA)
val Alpha50Light = Color(0x80FFFFFF)
val Alpha50Dark = Color(0x80000000)

val Colors.Alpha50: Color
    @Composable
    get() = if(isLight) Alpha50Dark else Alpha50Light

val Colors.StickyHeaderBackground: Color
    @Composable
    get() = if(isLight) AlmostWhite else AlmostBlack

val Colors.StickyHeaderContent: Color
    @Composable
    get() = if(isLight) VeryDarkGray else HeavyWhiteGray

val Colors.Paragraph: Color
    @Composable
    get() = if(isLight) Color(0xFF80D8FF) else Color(0xFF01579B)

val Colors.Chorus: Color
    @Composable
    get() = if(isLight) Color(0xFFB9F6CA) else Color(0xFF004D40)

val Colors.IdCircle: Color
    @Composable
    get() = if(isLight) Color(0xFFBBDEFB) else Color(0xFF01579B)

val Colors.GoTop: Color
    @Composable
    get() = if(isLight) Color(0xFFFF9E80) else Color(0xFFBF360C)

val Colors.IdColor: Color
    @Composable
    get() = if(isLight) VeryDarkGray else HeavyWhiteGray

val Colors.CategoryPill: Color
    @Composable
    get() = if(isLight) LightGrey else Grey

val ShimmerColorShades = listOf(

    Color.LightGray.copy(0.9f),

    Color.LightGray.copy(0.2f),

    Color.LightGray.copy(0.9f)

)