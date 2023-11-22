package com.example.denikv1

import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

interface AllStatisticsController {
    fun getDataGraph(): BarGraphSeries<DataPoint>
    fun getXLabelsGraph(): Array<String>
}

// implementace kontroléru pro celkové statistiky
class AllStatisticsControllerImpl(
    private val model: AllStatisticsModel
) : AllStatisticsController {

    //metoda pro získání dat pro sloupcový graf
    override fun getDataGraph(): BarGraphSeries<DataPoint> {
        return model.getDataGraph()
    }

    //metoda pro získání osy X pro graf.
    override fun getXLabelsGraph(): Array<String> {
        return model.getXLabelsGraph()
    }
}
