package com.example.denikv1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface CestaView {
    fun displayCesty()
    fun addButton()
    fun findButton()
    fun statisticsButton()
    // Další metody pro zobrazení informací na uživatelském rozhraní
}

class CestaViewImp  : AppCompatActivity(), CestaView {
    private lateinit var controller: CestaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val cestaRepository: CestaModel = CestaModelImpl()
        controller = CestaControllerImpl(cestaRepository,this)

        supportActionBar?.elevation = 0f

        displayCesty()
        addButton()
        findButton()
        statisticsButton()

    }

    override fun displayCesty() {
        val taskList = findViewById<RecyclerView>(R.id.recyclerView)
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = CestaAdapter(controller.getAllCesty())
    }

    override fun addButton() {
        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun findButton(){
        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }
    }

    override fun statisticsButton(){
        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }
}
