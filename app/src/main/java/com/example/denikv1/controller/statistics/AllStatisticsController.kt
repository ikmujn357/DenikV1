package com.example.denikv1

import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

interface AllStatisticsController {
    fun getDataGraph(): BarGraphSeries<DataPoint>
    fun getXLabelsGraph(): Array<String>
}

class AllStatisticsControllerImpl(
    private val model: AllStatisticsModel
) : AllStatisticsController {

    override fun getDataGraph(): BarGraphSeries<DataPoint> {
        return model.getDataGraph()
    }

    override fun getXLabelsGraph(): Array<String> {
        return model.getXLabelsGraph()
    }
}
