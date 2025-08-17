package com.example.beluniapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = pumpkin_orange,
    onPrimary = pure_white,
    secondary = cobalt,
    onSecondary = pure_white,
    background = pure_white,
    onBackground = mirage,
    surface = mirage.copy(alpha = 0.05f), // Very light gray tone from Mirage
    onSurface = mirage,
    surfaceBright = light_gray
)


private val DarkColors = darkColorScheme(
    primary = pumpkin_orange,             // Bright CTA color
    onPrimary = pure_white,
    secondary = cobalt,                  // Strong highlight
    onSecondary = pure_white,
    tertiary = mirage,                   // Deep background accent
    onTertiary = pure_white,
    background = mirage,                // Dark background
    onBackground = pure_white,
    surface = cobalt,                    // Cards or elevated surfaces
    onSurface = pure_white,
    surfaceBright = dark_gray
)



@Composable
fun BelUniAppTheme(
    useDarkTheme: Boolean =  isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
//if to use a wallpapertheme
//@Composable
//fun AppTheme(
//    useDarkTheme: Boolean =  isSystemInDarkTheme(),
//    content: @Composable () -> Unit
//) {
//    val context = LocalContext.current
//    val colors = when {
//        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
//            if (useDarkTheme) dynamicDarkColorScheme(context)
//            else dynamicLightColorScheme(context)
//        }
//        useDarkTheme -> DarkColors
//        else -> LightColors
//    }
//
//    MaterialTheme(
//        colorScheme = colors,
//        content = content
//    )
//}