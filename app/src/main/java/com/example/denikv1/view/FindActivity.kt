package com.example.denikv1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.util.Calendar

class FindActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutByName: LinearLayout
    private lateinit var layoutByDate: LinearLayout
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vyhledavani)

        calendarView = findViewById(R.id.calendarView)
        recyclerView = findViewById(R.id.recyclerViewVyhledavani)
        nameEditText = findViewById(R.id.nameEditText)
        layoutByName = findViewById(R.id.layoutByName)
        layoutByDate = findViewById(R.id.layoutByDate)
        radioGroup = findViewById(R.id.searchOptions)

        calendarView.date = System.currentTimeMillis()

        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        loadAndDisplayCesty(currentDate)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateChanged(year, month, dayOfMonth)
        }

        val findButton: Button = findViewById(R.id.findButton)
        findButton.setOnClickListener {
            onFindButtonClick()
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioName -> showLayoutByName()
                R.id.radioDate -> showLayoutByDate()
            }
        }

        showLayoutByName()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f
    }

    private fun onDateChanged(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDateStart = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val selectedDateEnd = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 23, 59, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        loadAndDisplayCesty(selectedDateStart, selectedDateEnd)
    }

    private fun onFindButtonClick() {
        if (isLayoutByNameVisible()) {
            val searchName = nameEditText.text.toString()
            if (searchName.isNotBlank()) {
                loadAndDisplayCesty(searchName)
            }
        } else {
            val selectedDate = calendarView.date
            loadAndDisplayCesty(selectedDate)
        }
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

    private fun loadAndDisplayCesty(searchName: String) {
        val controller = CestaControllerImpl(CestaModelImpl(this))
        lifecycleScope.launch {
            try {
                val cesty = controller.getAllCestaByName(searchName)
                updateRecyclerView(cesty)
            } catch (e: Exception) {
                Log.e("FindActivity", "Error loading and displaying cesty", e)
            }
        }
    }

    private fun updateRecyclerView(cesty: List<CestaEntity>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CestaAdapter(cesty) { _ -> }
    }

    private fun isLayoutByNameVisible(): Boolean {
        return layoutByName.visibility == LinearLayout.VISIBLE
    }

    private fun showLayoutByName() {
        layoutByName.visibility = LinearLayout.VISIBLE
        layoutByDate.visibility = LinearLayout.GONE
    }

    private fun showLayoutByDate() {
        layoutByName.visibility = LinearLayout.GONE
        layoutByDate.visibility = LinearLayout.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
