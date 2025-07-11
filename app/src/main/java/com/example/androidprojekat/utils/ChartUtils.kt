package com.example.androidprojekat.utils

import com.example.androidprojekat.data.api.expireddlcards.ExpiredDLCardInfo
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry

fun getBarChartData(expiredDLCards: List<ExpiredDLCardInfo>): Pair<List<BarEntry>, List<String>> {
    val entityCounts = expiredDLCards.groupingBy { it.entity }.eachCount()

    val labels = entityCounts.keys.map {
        when (it?.trim()?.lowercase()) {
            "federacija bosne i hercegovine" -> "FBiH"
            "republika srpska" -> "RS"
            "brčko distrikt bosne i hercegovine" -> "Brčko"
            else -> it ?: "Nepoznato"
        }
    }

    val entries = entityCounts.entries.mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.value.toFloat())
    }

    return Pair(entries, labels)
}

fun getPieChartData(expiredDLCards: List<ExpiredDLCardInfo>): List<PieEntry> {
    val totalMale = expiredDLCards.sumOf { it.maleTotal }
    val totalFemale = expiredDLCards.sumOf { it.femaleTotal }

    return listOf(
        PieEntry(totalMale.toFloat(), "Muški"),
        PieEntry(totalFemale.toFloat(), "Ženski")
    )
}
