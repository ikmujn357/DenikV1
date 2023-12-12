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
    fun getXLabelsGraph1(context: Context): Array<String>

    // Metoda pro získání unikátních obtížností
    fun getUniqueDifficulties(context: Context): List<String>

    // Metoda pro získání dat pro druhý sloupcový graf
    fun getDataGraph2(): BarGraphSeries<DataPoint>

    // Metoda pro získání osy X pro druhý sloupcový graf
    fun getXLabelsGraph2(): Array<String>
}

// Implementace rozhraní pro model denních statistik
class DailyStatisticsModelImpl(private val cestaModel: CestaModel) : DailyStatisticsModel {

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

    // Metoda pro získání dat pro první sloupcový graf
    override fun getDataGraph1(context: Context, startDate: Long, endDate: Long): BarGraphSeries<DataPoint> {
        // Načtení všech cest pro zadaný rozsah dat
        val allCesta = runBlocking { cestaModel.getAllCestaForDateRange(startDate, endDate) }

        // Pokud nejsou žádná data, vrátíme prázdný graf
        if (allCesta == null) {
            return BarGraphSeries(emptyArray())
        }

        // Seřazení unikátních obtížností podle definovaného pořadí
        val distinctDifficulties = getUniqueDifficulties(context).sortedWith(difficultyComparator)

        // Vytvoření datových bodů pro sloupcový graf
        val dataPoints = distinctDifficulties.mapIndexed { index, difficulty ->
            val count = allCesta.count { it.grade == difficulty }
            DataPoint(index + 1.0, count.toDouble())
        }.toTypedArray()

        return BarGraphSeries(dataPoints)
    }

    // Metoda pro získání osy X pro první sloupcový graf
    override fun getXLabelsGraph1(context: Context): Array<String> {
        // Seřazení unikátních obtížností podle definovaného pořadí a přidání prázdného labelu na konec
        val labels = getUniqueDifficulties(context).sortedWith(difficultyComparator).toTypedArray()
        return arrayOf(*labels, "")
    }

    // Metoda pro získání unikátních obtížností ze všech cest
    override fun getUniqueDifficulties(context: Context): List<String> {
        val allCesta = runBlocking { cestaModel.getAllCesta() }
        return allCesta.map { it.grade }.distinct()
    }

    // Předdefinovaná data pro druhý sloupcový graf
    private val dataGraph2 = BarGraphSeries(
        arrayOf(
            DataPoint(1.0, 2.0),
            DataPoint(3.0, 4.0)
        )
    )

    // Předdefinovaná data pro osu X druhého sloupcového grafu
    private val xLabelsGraph2 = arrayOf(
        "FLash", "RP", "PP", "TR", "AF",
    )

    // Metoda pro získání dat pro druhý sloupcový graf
    override fun getDataGraph2(): BarGraphSeries<DataPoint> {
        return dataGraph2
    }

    // Metoda pro získání osy X pro druhý sloupcový graf
    override fun getXLabelsGraph2(): Array<String> {
        return xLabelsGraph2
    }
}
