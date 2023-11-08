package com.example.denikv1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportActionBar?.elevation = 0f

        // Inicializace RecyclerView a přiřazení layout manageru
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        val buttonShowAdd = findViewById<Button>(R.id.button_add)
        buttonShowAdd.setOnClickListener {
            // Po kliknutí na tlačítko zobrazíme statistiky (přejdeme na novou aktivitu)
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        val buttonShowFind = findViewById<Button>(R.id.button_find)
        buttonShowFind.setOnClickListener {
            // Po kliknutí na tlačítko zobrazíme statistiky (přejdeme na novou aktivitu)
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }

        val buttonShowStatistics = findViewById<Button>(R.id.button_statistics)
        buttonShowStatistics.setOnClickListener {
            // Po kliknutí na tlačítko zobrazíme statistiky (přejdeme na novou aktivitu)
            val intent = Intent(this, ShowStatistics::class.java)
            startActivity(intent)
        }
    }
}








/*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

    }
}

 */






/*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vyhledavani)

        // Inicializace RecyclerView a přiřazení layout manageru
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
    }
}

 */