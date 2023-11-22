package com.example.denikv1

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter

interface AllStatisticsView {
    fun displayGraph(view: View)
}

// Třída fragmentu pro celkovou statistiku
class AllStatisticsFragment : Fragment(), AllStatisticsView {
    // Kontrolér pro práci s modelem a získání dat pro zobrazení
    private lateinit var controller: AllStatisticsController

    // Metoda volaná při vytváření pohledu fragmentu
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.celkova, container, false)

        controller = AllStatisticsControllerImpl(AllStatisticsModelImp())

        // Zobrazení grafu
        displayGraph(view)

        return view
    }

    // Implementace metody pro zobrazení grafu
    override fun displayGraph(view: View) {
        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost)

        // Přidání datové řady do grafu získané z kontroléru
        graphView.addSeries(controller.getDataGraph())

        // Nastavení rozsahu os u grafu
        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.setMinX(0.5)
        graphView.viewport.setMaxX(1.5)

        graphView.viewport.isYAxisBoundsManual = true
        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxY(10.0)

        // Nastavení formátování mřížky u grafu
        graphView.gridLabelRenderer.numHorizontalLabels = 5
        graphView.gridLabelRenderer.numVerticalLabels = 5

        graphView.gridLabelRenderer.labelHorizontalHeight = 25

        graphView.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)

        graphView.gridLabelRenderer.setHorizontalLabelsAngle(50)

        // Nastavení statického formátu popisek osy x
        val staticLabelsFormatter = StaticLabelsFormatter(graphView)

        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph())
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }
}
