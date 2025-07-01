package com.example.androidprojekat.ui.screens

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.example.androidprojekat.viewmodel.Factory.ExpiredDLCardsViewModelFactory
import com.example.androidprojekat.repository.ExpiredDLCardsRepository
import com.example.androidprojekat.data.RetrofitInstance
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarChartScreen(
    navController: NavController,
    universalViewModel: UniversalViewModel,
    viewModel: ExpiredDLCardsViewModel
)
 {
    val context = LocalContext.current
    val expiredDLCards by viewModel.expiredDLCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val dataSource by viewModel.dataSource.collectAsState()
    val entityIndex by universalViewModel.selectedEntityIndexDL.collectAsState()
    val cantonIndex by universalViewModel.selectedCantonIndexDL.collectAsState()

    LaunchedEffect(entityIndex, cantonIndex) {
        viewModel.fetchExpiredDLCards(forceRefresh = true)
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                favouritesRoute = "favourites",
                homeRoute = "home",
                statisticsRoute = "statistics"
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Ukupno podataka: ${expiredDLCards.size} ($dataSource)",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Broj isteklih vozačkih dozvola po entitetima",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading && expiredDLCards.isEmpty()) {
                CircularProgressIndicator()
            } else if (expiredDLCards.isEmpty()) {
                Text(text = "Nema podataka za prikaz.")
            } else {
                val entityCounts = expiredDLCards.groupingBy { it.entity }.eachCount()

                val labels = entityCounts.keys.map {
                    when (it.trim().lowercase()) {
                        "federacija bosne i hercegovine" -> "FBiH"
                        "republika srpska" -> "RS"
                        "brčko distrikt bosne i hercegovine" -> "Brčko"
                        else -> it
                    }
                }

                val entries = entityCounts.entries.mapIndexed { index, entry ->
                    BarEntry(index.toFloat(), entry.value.toFloat())
                }

                AndroidView(factory = {
                    BarChart(context).apply {
                        val dataSet = BarDataSet(entries, "Broj EDL kartica po entitetima")
                        dataSet.color = Color.BLUE
                        dataSet.valueTextSize = 12f
                        dataSet.barBorderWidth = 0.5f

                        val barData = BarData(dataSet)
                        barData.barWidth = 0.4f
                        this.data = barData

                        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                        xAxis.granularity = 1f
                        xAxis.setDrawGridLines(false)
                        xAxis.labelRotationAngle = 0f
                        xAxis.textSize = 12f
                        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

                        axisLeft.textSize = 12f
                        axisRight.isEnabled = false

                        description = Description().apply { text = "" }

                        legend.isEnabled = true
                        animateY(1000)
                        invalidate()
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 24.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "Polna struktura isteklih vozačkih dozvola",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Prikazuje omjer muških i ženskih korisnika čije su dozvole istekle.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val totalMale = expiredDLCards.sumOf { it.maleTotal }
                val totalFemale = expiredDLCards.sumOf { it.femaleTotal }

                AndroidView(factory = {
                    PieChart(context).apply {
                        val entriesPie = listOf(
                            PieEntry(totalMale.toFloat(), "Muški"),
                            PieEntry(totalFemale.toFloat(), "Ženski")
                        )

                        val dataSetPie = PieDataSet(entriesPie, "")
                        dataSetPie.colors = listOf(Color.BLUE, Color.YELLOW)
                        dataSetPie.valueTextSize = 14f

                        this.data = PieData(dataSetPie)

                        description = Description().apply { text = "" }

                        centerText = "Istekle dozvole"
                        setCenterTextSize(14f)
                        legend.isEnabled = true
                        setUsePercentValues(true)
                        animateY(1000)
                        invalidate()
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                )
            }
        }
    }
}
