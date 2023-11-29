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

// Implementace rozhraní pro demonstraci dat DOČASNÉ ŘEŠENÍ
class AllStatisticsModelImpl(private val cestaModel: CestaModel, private val context: Context) : AllStatisticsModel {

    override fun getDataGraph(context: Context): BarGraphSeries<DataPoint> {
        val allCesta = runBlocking { cestaModel.getAllCesta() }
        val grades = context.resources.getStringArray(R.array.Grade)

        val distinctDifficulties = allCesta.map { it.grade }.distinct()

        val dataPoints = distinctDifficulties.mapIndexed { index, difficulty ->
            val count = allCesta.count { it.grade == difficulty }
            DataPoint(index + 1.0, count.toDouble()) // Přidán posun o 1 na ose x
        }.toTypedArray()

        return BarGraphSeries(dataPoints)
    }

    override fun getXLabelsGraph(context: Context): Array<String> {
        val labels = getUniqueDifficulties(context).toTypedArray()
        return arrayOf(*labels,"")  // Přidat prázdný label na začátek pole
    }


    override fun getUniqueDifficulties(context: Context): List<String> {
        val allCesta = runBlocking { cestaModel.getAllCesta() }
        return allCesta.map { it.grade }.distinct()
    }
}