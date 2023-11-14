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

class AllStatisticsFragment : Fragment(), AllStatisticsView {
    private lateinit var controller: AllStatisticsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.celkova, container, false)

        controller = AllStatisticsControllerImpl(AllStatisticsModelImp())

        displayGraph(view)

        return view
    }

    override fun displayGraph(view: View) {
        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost)

        graphView.addSeries(controller.getDataGraph())

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

        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph())
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }
}
