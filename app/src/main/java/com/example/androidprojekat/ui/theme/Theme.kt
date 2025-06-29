package com.example.androidprojekat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    background = DarkBlue,
    onBackground = Color.White,
    surface = DarkBlue,
    onSurface = Color.White
)


private val LightColorScheme = lightColorScheme(
    background = FWhite,
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onBackground = PrimaryTextBlue,
    onSurface = PrimaryTextBlue,
    surface = TopBarColor
)

@Composable
fun AndroidProjekatTheme(
    useDarkTheme: Boolean = true,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
