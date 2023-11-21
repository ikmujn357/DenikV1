package com.example.denikv1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

interface CestaView {
    fun displayCesty()
    fun addButton()
    fun findButton()
    fun statisticsButton()
}

class CestaViewImp : AppCompatActivity(), CestaView,  CoroutineScope by MainScope(){
    private lateinit var controller: CestaController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val cestaRepository: CestaModel = CestaModelImpl(this)
        controller = CestaControllerImpl(cestaRepository)

        supportActionBar?.elevation = 0f

        displayCesty()
        addButton()
        findButton()
        statisticsButton()
    }

    override fun displayCesty() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Use the Main dispatcher for UI updates
        lifecycleScope.launch(Dispatchers.Main) {
            val layoutManager = LinearLayoutManager(this@CestaViewImp)
            layoutManager.reverseLayout = true  // Obrácené zobrazení
            layoutManager.stackFromEnd = true    // Zobrazit položky odspoda
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = CestaAdapter(controller.getAllCesta())
        }
    }

    override fun addButton() {
        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun findButton() {
        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }
    }

    override fun statisticsButton() {
        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Use the activity's coroutine scope and launch on the main thread
        launch {
            displayCesty()
        }
    }
}
