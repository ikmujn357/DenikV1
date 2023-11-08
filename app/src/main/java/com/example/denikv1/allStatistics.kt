package com.example.denikv1

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

class AllStatistics : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.celkova, container, false)

        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost)

        val series = BarGraphSeries(arrayOf(
            DataPoint(1.0, 2.0),
            DataPoint(3.0, 4.0)

        ))

        graphView.addSeries(series)

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
        val xLabels = arrayOf(
            "4","5", "5+", "6-", "6","6+", "7-", "7", "7+", "8-", "8", "8+", "9-", "9"
        )
        staticLabelsFormatter.setHorizontalLabels(xLabels)
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        return view
    }

}
