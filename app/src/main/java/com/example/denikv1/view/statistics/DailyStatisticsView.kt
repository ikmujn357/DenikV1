package com.example.denikv1

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter

interface DailyStatisticsView {
    fun displayGraph1(view: View)
    fun displayGraph2(view: View)
    // Další metody pro zobrazení informací na uživatelském rozhraní
}

class DailyStatisticsFragment : Fragment(), DailyStatisticsView {
    private lateinit var controller: DailyStatisticsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.denni, container, false)

        controller = DailyStatisticsControllerImpl(this, DailyStatisticsModelImpl())

        displayGraph1(view)
        displayGraph2(view)

        return view
    }

    override fun displayGraph1(view: View) {
        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost_denni)

        graphView.addSeries(controller.getDataGraph1())

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.setMinX(0.5)
        graphView.viewport.setMaxX(1.5)

        graphView.viewport.isYAxisBoundsManual = true
        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxY(10.0)

        graphView.gridLabelRenderer.numHorizontalLabels = 5
        graphView.gridLabelRenderer.numVerticalLabels = 5

        graphView.gridLabelRenderer.labelHorizontalHeight = 25

        graphView.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)

        graphView.gridLabelRenderer.setHorizontalLabelsAngle(50)

        val staticLabelsFormatter = StaticLabelsFormatter(graphView)

        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph1())
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }

    override fun displayGraph2(view: View) {
        val graphView = view.findViewById<GraphView>(R.id.graph_styl_prelezu)

        graphView.addSeries(controller.getDataGraph2())

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.setMinX(0.5)
        graphView.viewport.setMaxX(1.5)

        graphView.viewport.isYAxisBoundsManual = true
        graphView.viewport.setMinY(0.0)
        graphView.viewport.setMaxY(10.0)

        graphView.gridLabelRenderer.numHorizontalLabels = 5
        graphView.gridLabelRenderer.numVerticalLabels = 5

        graphView.gridLabelRenderer.labelHorizontalHeight = 25

        graphView.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)

        graphView.gridLabelRenderer.setHorizontalLabelsAngle(50)

        val staticLabelsFormatter = StaticLabelsFormatter(graphView)
        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph2())
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }
}
