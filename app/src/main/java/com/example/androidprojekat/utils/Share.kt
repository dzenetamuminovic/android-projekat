package com.example.androidprojekat.utils

import android.content.Context
import android.content.Intent

object Share {
    fun shareData(context: Context, text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(sendIntent, "Podijeli putem:"))
    }
}
