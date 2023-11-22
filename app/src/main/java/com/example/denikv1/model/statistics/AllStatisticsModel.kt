package com.example.denikv1

import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

// Rozhraní pro model statistik se sloupcovým grafem
interface AllStatisticsModel {
    //  Získá data pro sloupcový graf
    fun getDataGraph(): BarGraphSeries<DataPoint>
    //Získá popisky osy X pro graf.
    fun getXLabelsGraph(): Array<String>
}

// Implementace rozhraní pro demonstraci dat DOČASNÉ ŘEŠENÍ
class AllStatisticsModelImp: AllStatisticsModel {

    private val dataGraph = BarGraphSeries(arrayOf(
        DataPoint(1.0, 2.0),
        DataPoint(3.0, 4.0)
    ))

    private val xLabelsGraph = arrayOf(
        "4","5", "5+", "6-", "6","6+", "7-", "7", "7+", "8-", "8", "8+", "9-", "9"
    )

    override fun getDataGraph(): BarGraphSeries<DataPoint> {
        return dataGraph
    }

    override fun getXLabelsGraph(): Array<String> {
        return xLabelsGraph
    }
}