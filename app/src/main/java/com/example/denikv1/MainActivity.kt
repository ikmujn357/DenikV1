package com.example.denikv1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    private val cesty = ArrayList<Cesta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        cesty.add(Cesta("Pata cesta", "8+"))
        cesty.add(Cesta("Ctvrta cesta", "6"))
        cesty.add(Cesta("Treti cesta", "4"))
        cesty.add(Cesta("Druha cesta", "7-"))
        cesty.add(Cesta("Prvni cesta", "9"))

        val taskList = findViewById<RecyclerView>(R.id.recyclerView)
        taskList.layoutManager = LinearLayoutManager(this)
        taskList.adapter = CestaAdapter(cesty)

        supportActionBar?.elevation = 0f

        // Inicializace RecyclerView a přiřazení layout manageru
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager



        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }

        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }


}


