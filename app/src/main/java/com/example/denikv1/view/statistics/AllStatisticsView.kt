package com.example.denikv1

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries

interface AllStatisticsView {
    fun displayGraph(view: View)
}

class AllStatisticsFragment : Fragment(), AllStatisticsView {
    private lateinit var controller: AllStatisticsController
    private lateinit var cestaModel: CestaModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.celkova, container, false)
        cestaModel = CestaModelImpl(requireContext())
        val statisticsModel = AllStatisticsModelImpl(cestaModel, requireContext())
        controller = AllStatisticsControllerImpl(statisticsModel, requireContext())
        displayGraph(view)
        return view
    }

    override fun displayGraph(view: View) {
        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost)
        val series = controller.getDataGraph(requireContext())

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.isYAxisBoundsManual = true

        val maxY = series.highestValueY
        graphView.viewport.setMaxX(series.highestValueX)
        graphView.viewport.setMaxY(maxY + 1.0)

        graphView.addSeries(series)

        val numLabels = controller.getXLabelsGraph(requireContext()).size
        graphView.gridLabelRenderer.numHorizontalLabels = numLabels
        graphView.gridLabelRenderer.numVerticalLabels = 5

        graphView.gridLabelRenderer.labelHorizontalHeight = 50
        graphView.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)
        graphView.gridLabelRenderer.setHorizontalLabelsAngle(0)

        val staticLabelsFormatter = StaticLabelsFormatter(graphView)
        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph(requireContext()))
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        val barWidthPx = 75
        series.spacing = barWidthPx
    }
}
