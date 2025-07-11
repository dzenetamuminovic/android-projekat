package com.example.androidprojekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidprojekat.ui.screens.MainScreen
import com.example.androidprojekat.ui.theme.AndroidProjekatTheme
import com.example.androidprojekat.utils.LocaleHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LocaleHelper.setLocale(this, LocaleHelper.getSavedLanguage(this))
        super.onCreate(savedInstanceState)
        setContent {
            AndroidProjekatTheme {
                MainScreen()
            }
        }
    }
}