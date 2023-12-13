package com.example.denikv1

import android.content.Context
import android.util.Log
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Rozhraní pro model denních statistik s sloupcovým grafem
interface DailyStatisticsModel {
    // Metoda pro získání dat pro první sloupcový graf
    fun getDataGraph1(context: Context, startDate: Long, endDate: Long): BarGraphSeries<DataPoint>

    // Metoda pro získání osy X pro první sloupcový graf
    fun getXLabelsGraph1(context: Context, startDate: Long, endDate: Long): Array<String>

    fun getDataByDate (startDate: Long, endDate: Long): List<CestaEntity>
    // Metoda pro získání unikátních obtížností
    fun getUniqueDifficulty(context: Context, startDate: Long, endDate: Long): List<String>
    fun getUniqueStyle(context: Context, startDate: Long, endDate: Long): List<String>

    // Metoda pro získání dat pro druhý sloupcový graf
    fun getDataGraph2(context: Context, startDate: Long, endDate: Long): BarGraphSeries<DataPoint>

    // Metoda pro získání osy X pro druhý sloupcový graf
    fun getXLabelsGraph2(context: Context, startDate: Long, endDate: Long): Array<String>
}

// Implementace rozhraní pro model denních statistik
class DailyStatisticsModelImpl(private val cestaModel: CestaModel) : DailyStatisticsModel {
    override fun getDataByDate (startDate: Long, endDate: Long): List<CestaEntity> {
        return runBlocking { cestaModel.getAllCestaForDateRange(startDate, endDate) }
    }

    override fun getUniqueDifficulty(context: Context, startDate: Long, endDate: Long): List<String> {
        val allCesta = getDataByDate (startDate, endDate)
        return allCesta.map { it.grade }.distinct()
    }

    override fun getUniqueStyle(context: Context, startDate: Long, endDate: Long): List<String> {
        val allCesta = getDataByDate (startDate, endDate)
        return allCesta.map { it.climbStyle }.distinct()
    }


    // Komparátor pro porovnání obtížností podle jejich pořadí
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

    private val climbingStyleComparator = Comparator<String> { style1, style2 ->
        val orderMap = mapOf(
            "Flash" to 1, "On sight" to 2, "Top rope" to 3,
            "Red point" to 4, "Pink point" to 5, "All free" to 6
        )

        orderMap[style1]!!.compareTo(orderMap[style2]!!)
    }


    // Metoda pro získání dat pro první sloupcový graf
    override fun getDataGraph1(context: Context, startDate: Long, endDate: Long): BarGraphSeries<DataPoint> {
        // Načtení všech cest pro zadaný rozsah dat
        val allCesta = getDataByDate(startDate,endDate)

        // Pokud nejsou žádná data, vrátíme prázdný graf
        if (allCesta == null) {
            return BarGraphSeries(emptyArray())
        }

        // Seřazení unikátních obtížností podle definovaného pořadí
        val distinctDifficulties = getUniqueDifficulty(context, startDate,endDate).sortedWith(difficultyComparator)

        // Vytvoření datových bodů pro sloupcový graf
        val dataPoints = distinctDifficulties.mapIndexed { index, difficulty ->
            val count = allCesta.count { it.grade == difficulty }
            DataPoint(index + 1.0, count.toDouble())
        }.toTypedArray()

        return BarGraphSeries(dataPoints)
    }

    // Metoda pro získání osy X pro první sloupcový graf
    override fun getXLabelsGraph1(context: Context, startDate: Long, endDate: Long): Array<String> {
        // Seřazení unikátních obtížností podle definovaného pořadí a přidání prázdného labelu na konec
        val labels = getUniqueDifficulty(context, startDate, endDate).sortedWith(difficultyComparator).toTypedArray()
        return arrayOf(*labels, "")
    }

    // Metoda pro získání dat pro druhý sloupcový graf
    override fun getDataGraph2(context: Context, startDate: Long, endDate: Long): BarGraphSeries<DataPoint> {
        // Načtení všech cest pro zadaný rozsah dat
        val allCesta = getDataByDate(startDate,endDate)

        // Pokud nejsou žádná data, vrátíme prázdný graf
        if (allCesta == null) {
            return BarGraphSeries(emptyArray())
        }

        // Seřazení unikátních obtížností podle definovaného pořadí
        val distinctStyle = getUniqueStyle(context, startDate,endDate).sortedWith(climbingStyleComparator)

        // Vytvoření datových bodů pro sloupcový graf
        val dataPoints = distinctStyle.mapIndexed { index, style ->
            val count = allCesta.count { it.climbStyle == style }
            DataPoint(index + 1.0, count.toDouble())
        }.toTypedArray()

        return BarGraphSeries(dataPoints)
    }

    // Metoda pro získání osy X pro druhý sloupcový graf
    override fun getXLabelsGraph2(context: Context, startDate: Long, endDate: Long): Array<String> {
        // Seřazení unikátních obtížností podle definovaného pořadí a přidání prázdného labelu na konec
        val labels = getUniqueStyle(context, startDate, endDate).sortedWith(climbingStyleComparator).toTypedArray()
        return arrayOf(*labels, "")
    }
}
