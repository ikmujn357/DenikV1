package com.example.denikv1

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar

// Rozhraní
interface DailyStatisticsView {
    fun displayGraph1(view: View, year: Int, month: Int, dayOfMonth: Int)
    fun displayGraph2(view: View)
}

// Třída DailyStatisticsFragment implementující rozhraní
class DailyStatisticsFragment : Fragment(), DailyStatisticsView {
    private lateinit var controller: DailyStatisticsController
    private lateinit var cestaModel: CestaModel
    private lateinit var calendarView: CalendarView
    private lateinit var graphView1: GraphView

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.denni, container, false)
        cestaModel = CestaModelImpl(requireContext())
        val statisticsModel = DailyStatisticsModelImpl(cestaModel)
        controller = DailyStatisticsControllerImpl(this, statisticsModel)

        // Inicializace pohledů
        graphView1 = view.findViewById(R.id.graph_obtiznost_denni)
        calendarView = view.findViewById(R.id.calendarView)

        // Nastavení Listener pro změnu data v CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            (controller as DailyStatisticsControllerImpl).onDateChanged(year, month, dayOfMonth)
        }

        val currentDate = calendar.timeInMillis
        (controller as DailyStatisticsControllerImpl).onDateChanged(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        return view
    }

    // Aktualizace prvního grafu
    fun updateGraph(series: BarGraphSeries<DataPoint>) {
        // Nastavení mezí pro osu X a Y
        graphView1.viewport.isXAxisBoundsManual = true
        graphView1.viewport.isYAxisBoundsManual = true

        val maxY = series.highestValueY
        graphView1.viewport.setMaxX(series.highestValueX + 0.5)
        graphView1.viewport.setMaxY(maxY + 1.0)

        // Odebrání předchozích dat a přidání nových
        graphView1.removeAllSeries()
        graphView1.addSeries(series)

        // Nastavení počtu horizontálních a vertikálních popisků
        val numLabels = controller.getXLabelsGraph1(requireContext()).size
        graphView1.gridLabelRenderer.numHorizontalLabels = numLabels
        graphView1.gridLabelRenderer.numVerticalLabels = 5

        // Nastavení popisků
        graphView1.gridLabelRenderer.labelHorizontalHeight = 50
        graphView1.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)
        graphView1.gridLabelRenderer.setHorizontalLabelsAngle(-25)

        // Nastavení statických popisků pro osu X
        val staticLabelsFormatter = StaticLabelsFormatter(graphView1)
        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph1(requireContext()))
        graphView1.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        // Nastavení minimální hodnoty pro osu X
        graphView1.viewport.setMinX(0.5)

        // Nastavení mezer mezi sloupci
        val barWidthPx = 25
        series.spacing = barWidthPx
    }

    // Zobrazení prvního grafu na podle vybraného data
    override fun displayGraph1(view: View, year: Int, month: Int, dayOfMonth: Int) {
        // Získání reference na GraphView
        val graphView = view.findViewById<GraphView>(R.id.graph_obtiznost_denni)

        // Nastavení aktuálního data a času
        calendarView = view.findViewById(R.id.calendarView)
        calendarView.date = System.currentTimeMillis()
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // Nastavení posluchače pro změnu data v CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            (controller as DailyStatisticsControllerImpl).onDateChanged(
                year,
                month,
                dayOfMonth
            )
        }

        // Načtení a zobrazení dat pro aktuální datum
        val series = controller.getDataGraph1(requireContext(), currentDate)

        // Nastavení mezí pro osu X a Y
        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.isYAxisBoundsManual = true

        val maxY = series.highestValueY
        graphView.viewport.setMaxX(series.highestValueX + 0.5)
        graphView.viewport.setMaxY(maxY + 1.0)

        graphView.removeAllSeries()
        graphView.addSeries(series)

        val numLabels = controller.getXLabelsGraph1(requireContext()).size
        graphView.gridLabelRenderer.numHorizontalLabels = numLabels
        graphView.gridLabelRenderer.numVerticalLabels = 5

        graphView.gridLabelRenderer.labelHorizontalHeight = 50
        graphView.gridLabelRenderer.setVerticalLabelsAlign(Paint.Align.CENTER)
        graphView.gridLabelRenderer.setHorizontalLabelsAngle(-25)

        val staticLabelsFormatter = StaticLabelsFormatter(graphView)
        staticLabelsFormatter.setHorizontalLabels(controller.getXLabelsGraph1(requireContext()))
        graphView.gridLabelRenderer.labelFormatter = staticLabelsFormatter

        // posunutí popisků o jedno místo doprava
        graphView.viewport.setMinX(0.5)

        // Nastavení mezer mezi sloupci
        val barWidthPx = 25
        series.spacing = barWidthPx
    }

    // Zobrazení druhého grafu
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
