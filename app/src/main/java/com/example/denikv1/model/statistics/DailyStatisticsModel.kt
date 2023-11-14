package com.example.denikv1

import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

interface DailyStatisticsModel {
    fun getDataGraph1(): BarGraphSeries<DataPoint>
    fun getXLabelsGraph1(): Array<String>
    fun getDataGraph2(): BarGraphSeries<DataPoint>

    fun getXLabelsGraph2(): Array<String>
}

class DailyStatisticsModelImpl : DailyStatisticsModel {

    private val dataGraph1 = BarGraphSeries(
        arrayOf(
            DataPoint(1.0, 2.0),
            DataPoint(3.0, 4.0)
        )
    )

    private val dataGraph2 = BarGraphSeries(
        arrayOf(
            DataPoint(1.0, 2.0),
            DataPoint(3.0, 4.0)
        )
    )

    private val xLabelsGraph1 = arrayOf(
        "4", "5", "5+", "6-", "6", "6+", "7-", "7", "7+", "8-", "8", "8+", "9-", "9"
    )

    private val xLabelsGraph2 = arrayOf(
        "FLash", "RP", "PP", "TR", "AF",
    )

    override fun getDataGraph1(): BarGraphSeries<DataPoint> {
        return dataGraph1
    }

    override fun getXLabelsGraph1(): Array<String> {
        return xLabelsGraph1
    }

    override fun getDataGraph2(): BarGraphSeries<DataPoint> {
        return dataGraph2
    }

    override fun getXLabelsGraph2(): Array<String> {
        return xLabelsGraph2
    }
}
