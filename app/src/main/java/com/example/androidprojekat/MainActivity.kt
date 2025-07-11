package com.example.androidprojekat

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.androidprojekat.data.preferences.LanguageDataStore
import com.example.androidprojekat.ui.screens.MainScreen
import com.example.androidprojekat.ui.theme.AndroidProjekatTheme
import com.example.androidprojekat.utils.LocaleHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    // ✅ Omogućava postavljanje jezika PRIJE nego što se učita UI
    override fun attachBaseContext(newBase: Context) {
        val lang = runBlocking {
            LanguageDataStore.getLanguage(newBase).first()
        }
        val context = LocaleHelper.wrap(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            // Nema potrebe da ovde pozivaš LocaleHelper.setLocale jer je već riješeno u attachBaseContext
            setContent {
                AndroidProjekatTheme {
                    MainScreen()
                }
            }
        }
    }
}
