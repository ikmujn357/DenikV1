package com.example.denikv1

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.util.Calendar
import android.widget.EditText

class FindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vyhledavani)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewVyhledavani)

        // Nastavení kalendáře na aktuální datum
        calendarView.date = System.currentTimeMillis()

        // Automatické získání aktuálního data
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // Volání funkce pro načtení cest z databáze pro aktuální datum
        loadAndDisplayCesty(currentDate)

        // Nastavení listeneru pro změnu data v kalendáři
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateChanged(year, month, dayOfMonth)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f
    }

    private fun onDateChanged(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDateStart = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // Nastavení na konec dne
        val selectedDateEnd = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 23, 59, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        // Volání funkce pro načtení cest z databáze pro vybraný rozsah datumů
        loadAndDisplayCesty(selectedDateStart, selectedDateEnd)
    }

    private fun loadAndDisplayCesty(startDate: Long, endDate: Long = startDate) {
        val controller = CestaControllerImpl(CestaModelImpl(this))
        lifecycleScope.launch {
            try {
                val cesty = controller.getAllCestaForDateRange(startDate, endDate)
                updateRecyclerView(cesty)
            } catch (e: Exception) {
                Log.e("FindActivity", "Error loading and displaying cesty", e)
            }
        }
    }

    private fun updateRecyclerView(cesty: List<CestaEntity>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewVyhledavani)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Předejte výchozí posluchač kliknutí, který nic nedělá
        recyclerView.adapter = CestaAdapter(cesty) { _ -> }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
