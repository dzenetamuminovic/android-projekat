package com.example.androidprojekat.ui.screens

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.androidprojekat.R
import com.example.androidprojekat.ui.components.BottomBar
import com.example.androidprojekat.utils.getBarChartData
import com.example.androidprojekat.utils.getPieChartData
import com.example.androidprojekat.viewmodel.ExpiredDLCardsViewModel
import com.example.androidprojekat.viewmodel.UniversalViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarChartScreen(
    navController: NavController,
    universalViewModel: UniversalViewModel,
    viewModel: ExpiredDLCardsViewModel
) {
    val context = LocalContext.current
    val expiredDLCards by viewModel.expiredDLCards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
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
                text = stringResource(id = R.string.total_data, expiredDLCards.size, dataSource),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.bar_chart_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading && expiredDLCards.isEmpty()) {
                CircularProgressIndicator()
            } else if (expiredDLCards.isEmpty()) {
                Text(text = stringResource(id = R.string.no_data))
            } else {
                // Dohvatanje podataka za bar chart
                val (entries, labels) = getBarChartData(expiredDLCards)

                AndroidView(factory = {
                    BarChart(context).apply {
                        val dataSet = BarDataSet(entries, context.getString(R.string.bar_chart_label))
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
                    text = stringResource(id = R.string.gender_chart_title),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = stringResource(id = R.string.gender_chart_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Dohvatanje podataka za pie chart
                val pieEntries = getPieChartData(expiredDLCards)

                AndroidView(factory = {
                    PieChart(context).apply {
                        val dataSetPie = PieDataSet(pieEntries, "")
                        dataSetPie.colors = listOf(Color.BLUE, Color.YELLOW)
                        dataSetPie.valueTextSize = 14f

                        this.data = PieData(dataSetPie)

                        description = Description().apply { text = "" }

                        centerText = context.getString(R.string.chart_center_text)
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
