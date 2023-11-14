package com.example.denikv1

import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

interface DailyStatisticsController {
    fun getDataGraph1(): BarGraphSeries<DataPoint>
    fun getXLabelsGraph1(): Array<String>
    fun getDataGraph2(): BarGraphSeries<DataPoint>
    fun getXLabelsGraph2(): Array<String>
}

class DailyStatisticsControllerImpl(
    private val view: DailyStatisticsView,
    private val model: DailyStatisticsModel
) : DailyStatisticsController {

    override fun getDataGraph1(): BarGraphSeries<DataPoint> {
        return model.getDataGraph1()
    }

    override fun getXLabelsGraph1(): Array<String> {
        return model.getXLabelsGraph1()
    }

    override fun getDataGraph2(): BarGraphSeries<DataPoint> {
        return model.getDataGraph2()
    }

    override fun getXLabelsGraph2(): Array<String> {
        return model.getXLabelsGraph2()
    }
}
