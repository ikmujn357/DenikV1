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

// Implementace view seznam cest
class CestaViewImp : AppCompatActivity(), CestaView,  CoroutineScope by MainScope(){
    private lateinit var controller: CestaController

    // Metoda volaná při vytvoření aktivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializace repozitáře pro práci s cestami
        val cestaRepository: CestaModel = CestaModelImpl(this)
        controller = CestaControllerImpl(cestaRepository)

        // Odstranění stínu z action baru
        supportActionBar?.elevation = 0f

        // Zobrazení seznamu cest, a tlačítek přidat cestu, najít cestu a statistika
        displayCesty()
        addButton()
        findButton()
        statisticsButton()
    }

    // Metoda pro zobrazení seznamu cest
    override fun displayCesty() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        lifecycleScope.launch(Dispatchers.Main) {
            val layoutManager = LinearLayoutManager(this@CestaViewImp)
            layoutManager.reverseLayout = true  // Obrácené zobrazení
            layoutManager.stackFromEnd = true    // Zobrazit položky odspoda
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = CestaAdapter(controller.getAllCesta())
        }
    }

    // Metoda pro přidání tlačítka pro přidání nové cesty
    override fun addButton() {
        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    // Metoda pro přidání tlačítka pro vyhledávání
    override fun findButton() {
        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }
    }

    // Metoda pro přidání tlačítka pro zobrazení statistik
    override fun statisticsButton() {
        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }

    // Metoda volaná při obnovení aktivity
    override fun onResume() {
        super.onResume()

        // Spuštění metody pro zobrazení seznamu cest v rámci aktivity
        launch {
            displayCesty()
        }
    }
}
