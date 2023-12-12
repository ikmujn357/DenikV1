package com.example.denikv1

import android.content.Context
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.runBlocking

// Rozhraní pro model statistik se sloupcovým grafem
interface AllStatisticsModel {
    fun getDataGraph(context: Context): BarGraphSeries<DataPoint>
    fun getXLabelsGraph(context: Context): Array<String>
    fun getUniqueDifficulties(context: Context): List<String>
}

// Implementace rozhraní pro demonstraci dat
class AllStatisticsModelImpl(private val cestaModel: CestaModel) : AllStatisticsModel {

    //seřazení obtížností
    private val difficultyComparator = Comparator<String> { difficulty1, difficulty2 ->
        val orderMap = mapOf(
            "1" to 1, "2" to 2, "2+" to 3, "3-" to 4, "3" to 5, "3+" to 6,
            "4-" to 7, "4" to 8, "4+" to 9, "5-" to 10, "5" to 11, "5+" to 12,
            "6-" to 13, "6" to 14, "6+" to 15, "7-" to 16, "7" to 17, "7+" to 18,
            "8-" to 19, "8" to 20, "8+" to 21, "9-" to 22, "9" to 23, "9+" to 24,
            "10-" to 25, "10" to 26, "11-" to 27, "11" to 28, "11+" to 29,
            "12-" to 30, "12" to 31
        )

        orderMap[difficulty1]!!.compareTo(orderMap[difficulty2]!!)
    }

    override fun getDataGraph(context: Context): BarGraphSeries<DataPoint> {
        val allCesta = runBlocking { cestaModel.getAllCesta() }
        val distinctDifficulties = getUniqueDifficulties(context).sortedWith(difficultyComparator)

        val dataPoints = distinctDifficulties.mapIndexed { index, difficulty ->
            val count = allCesta.count { it.grade == difficulty }
            DataPoint(index + 1.0, count.toDouble())
        }.toTypedArray()

        return BarGraphSeries(dataPoints)
    }

    override fun getXLabelsGraph(context: Context): Array<String> {
        val labels = getUniqueDifficulties(context).sortedWith(difficultyComparator).toTypedArray()
        return arrayOf(*labels, "")
    }

    override fun getUniqueDifficulties(context: Context): List<String> {
        val allCesta = runBlocking { cestaModel.getAllCesta() }
        return allCesta.map { it.grade }.distinct()
    }
}
