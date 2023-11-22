package com.example.denikv1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.denikv1.R

// vyhledávání cest
class FindActivity : AppCompatActivity() {

    // Metoda volaná při vytvoření aktivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nastavení obsahu aktivity na layout vyhledavani.xml
        setContentView(R.layout.vyhledavani)
        // Nastavení tlačítka zpět v action baru
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Odstranění stínu z action baru
        supportActionBar?.elevation = 0f

    }

    // Metoda volaná při stisku tlačítka zpět v action baru
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}