package com.example.denikv1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar

class AddActivity : AppCompatActivity() {
    // Instance modelu pro práci s databází
    private val cestaModel: CestaModel = CestaModelImpl(this)


    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

        // Nastavení tlačítka zpět v action baru
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        // Inicializace a nastavení obsahu Spinnerů
        setupSpinner()

        // Nastavení pro tlačítko přidání cesty
        val addCestaButton: Button = findViewById(R.id.saveButton)
        addCestaButton.setOnClickListener {
            newCesta()
        }

        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }
    }

    // Metoda pro inicializaci obsahů Spinnerů (3 spinnery)
    private fun setupSpinner() {
        // Odkazy na Spinnery z layoutu zapis.xml
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadCharacterSpinner: Spinner = findViewById(R.id.characterSpinner)

        // Načtení hodnot pro Spinners ze string.xml
        val difficultyLevels = resources.getStringArray(R.array.Grade)
        val StyleLevels = resources.getStringArray(R.array.Style)
        val characterLevels = resources.getStringArray(R.array.Character)

        // Adaptéry pro přiřazení hodnot k Spinnerům
        val adapterDif = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevels)
        val adapterStyle = ArrayAdapter(this, android.R.layout.simple_spinner_item, StyleLevels)
        val adapterCharacter = ArrayAdapter(this, android.R.layout.simple_spinner_item, characterLevels)

        // Nastavení vzhledu a chování Spinnerů
        adapterDif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCharacter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Přiřazení adaptérů k Spinnerům
        roadGradeSpinner.adapter = adapterDif
        roadStyleSpinner.adapter = adapterStyle
        roadCharacterSpinner.adapter = adapterCharacter
    }

    // Metoda pro přidání nové cesty
    private fun newCesta() {
        // Odkazy na vstupní prvky z layoutu
        val roadNameEditText: EditText = findViewById(R.id.nameEditText)
        val fallEditText: EditText = findViewById(R.id.fallEditText)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadCharSpinner: Spinner = findViewById(R.id.characterSpinner)
        val minuteEditText: EditText = findViewById(R.id.minutesEditText)
        val secondEditText: EditText = findViewById(R.id.secondsEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val opinionEditText: EditText = findViewById(R.id.opinionEditText)

        // Získání hodnot z vstupních prvků
        val cestaName = roadNameEditText.text.toString()
        val fallCountString = fallEditText.text.toString()
        val fallCount: Int= fallCountString.toInt()
        val styleSpinner = roadStyleSpinner.selectedItem.toString()
        val gradeSpinner = roadGradeSpinner.selectedItem.toString()
        val charSpinner = roadCharSpinner.selectedItem.toString()
        val minuteString = minuteEditText.text.toString()
        val minuteCount: Int= minuteString.toInt()
        val secondString = secondEditText.text.toString()
        val secondCount: Int= secondString.toInt()
        val descriptionRoad = descriptionEditText.text.toString()
        val opinionRoad = opinionEditText.text.toString()


        if (cestaName.isNotBlank() && fallCountString.isNotBlank() && minuteString.isNotBlank() && secondString.isNotBlank()
            && styleSpinner.isNotBlank() && gradeSpinner.isNotBlank() && charSpinner.isNotBlank() && descriptionRoad.isNotBlank() && opinionRoad.isNotBlank()) {
            lifecycleScope.launch {
                cestaModel.addNewCesta(
                    cestaName,
                    fallCount,
                    styleSpinner,
                    gradeSpinner,
                    charSpinner,
                    minuteCount,
                    secondCount,
                    descriptionRoad,
                    opinionRoad,
                    selectedDate
                )
                finish()                                            // Zavře aktivitu a vrátí se o 1 slide zpátky
                showToast("Cesta přidána!", 6)      // Zobrazí krátkou zprávu
            }
        } else {
            finish()
            showToast("Nevyplnil jste všechna políčka.", 5)
        }
    }

    //// Metoda pro zobrazení zprávy
    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    // Metoda pro nastavení akce tlačítka zpět
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
