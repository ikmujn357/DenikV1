package com.example.denikv1

import android.os.Bundle
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
    private val cestaModel: CestaModel = CestaModelImpl(this)

    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        setupSpinner()

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

        // Získání předaného ID cesty
        val receivedIntent = intent
        val cestaId = receivedIntent.getLongExtra("cestaId", 0)

        if (cestaId != 0L) {
            // Načtení informací o cestě pomocí ID a zobrazení v UI
            lifecycleScope.launch {
                val cesta = cestaModel.getCestaById(cestaId)
                populateUI(cesta)
            }
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
        val styleLevels = resources.getStringArray(R.array.Style)
        val characterLevels = resources.getStringArray(R.array.Character)

        // Upravení adaptérů pro použití vlastního layoutu
        val adapterDif = CustomArrayAdapter(this, R.layout.item_spinner, difficultyLevels.toList())
        val adapterStyle = CustomArrayAdapter(this, R.layout.item_spinner, styleLevels.toList())
        val adapterCharacter = CustomArrayAdapter(this, R.layout.item_spinner, characterLevels.toList())

        roadGradeSpinner.adapter = adapterDif
        roadStyleSpinner.adapter = adapterStyle
        roadCharacterSpinner.adapter = adapterCharacter

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
        val styleSpinner = roadStyleSpinner.selectedItem.toString()
        val gradeSpinner = roadGradeSpinner.selectedItem.toString()
        val charSpinner = roadCharSpinner.selectedItem.toString()
        val minuteString = minuteEditText.text.toString()
        val secondString = secondEditText.text.toString()
        val descriptionRoad = descriptionEditText.text.toString()
        val opinionRoad = opinionEditText.text.toString()

        // Zkontrolovat, zda uživatel vybral datum; pokud ne, použít aktuální datum
        val currentDate = if (selectedDate == 0L) System.currentTimeMillis() else selectedDate

        // Kontrola prázdných políček
        if (cestaName.isNotBlank() && fallCountString.isNotBlank() && minuteString.isNotBlank() && secondString.isNotBlank()
            && styleSpinner.isNotBlank() && gradeSpinner.isNotBlank() && charSpinner.isNotBlank() && descriptionRoad.isNotBlank() && opinionRoad.isNotBlank()) {

            // Získání předaného ID cesty
            val receivedIntent = intent
            val cestaId = receivedIntent.getLongExtra("cestaId", 0)

            lifecycleScope.launch {
                // Pokud máme cestaId, provedeme aktualizaci existující cesty
                if (cestaId != 0L) {
                    val existingCesta = cestaModel.getCestaById(cestaId)

                    // Aktualizace existující cesty s novými hodnotami
                    existingCesta.apply {
                        this.roadName = cestaName
                        this.fallCount = fallCountString.toInt()
                        this.climbStyle = styleSpinner
                        this.grade = gradeSpinner
                        this.roadChar = charSpinner
                        this.timeMinute = minuteString.toInt()
                        this.timeSecond = secondString.toInt()
                        this.description = descriptionRoad
                        this.opinion = opinionRoad
                        this.date = currentDate
                    }

                    // Aktualizace cesty v databázi
                    cestaModel.removeCesta(existingCesta)
                    cestaModel.insertCesta(existingCesta)

                    showToast("Cesta aktualizována!", Toast.LENGTH_LONG)
                    finish()
                } else {
                    // Pokud nemáme cestaId, vytvoříme a vložíme novou cestu
                    cestaModel.addNewCesta(
                        cestaName,
                        fallCountString.toInt(),
                        styleSpinner,
                        gradeSpinner,
                        charSpinner,
                        minuteString.toInt(),
                        secondString.toInt(),
                        descriptionRoad,
                        opinionRoad,
                        currentDate
                    )

                    showToast("Cesta přidána!", Toast.LENGTH_LONG)
                    finish()
                }
            }
        } else {
            showToast("Nevyplnil jste všechno.", Toast.LENGTH_SHORT)
        }
    }

    // Metoda pro zobrazení zprávy
    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    // Metoda pro nastavení akce tlačítka zpět
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    private fun populateUI(cesta: CestaEntity) {
        // Nastavení hodnot do jednotlivých polí ve vaší aktivitě
        val roadNameEditText: EditText = findViewById(R.id.nameEditText)
        val fallEditText: EditText = findViewById(R.id.fallEditText)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadCharSpinner: Spinner = findViewById(R.id.characterSpinner)
        val minuteEditText: EditText = findViewById(R.id.minutesEditText)
        val secondEditText: EditText = findViewById(R.id.secondsEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val opinionEditText: EditText = findViewById(R.id.opinionEditText)

        // Nastavení hodnot získaných z objektu cesty
        roadNameEditText.setText(cesta.roadName)
        fallEditText.setText(cesta.fallCount.toString())
        minuteEditText.setText(cesta.timeMinute.toString())
        secondEditText.setText(cesta.timeSecond.toString())
        descriptionEditText.setText(cesta.description)
        opinionEditText.setText(cesta.opinion)

        // Nastavení vybraného data
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.setDate(cesta.date)

        // Nastavení hodnot do spinnerů
        val difficultyLevels = resources.getStringArray(R.array.Grade)
        val styleLevels = resources.getStringArray(R.array.Style)
        val characterLevels = resources.getStringArray(R.array.Character)

        // Nastavení pozice vybrané hodnoty v každém spinneru
        roadGradeSpinner.setSelection(difficultyLevels.indexOf(cesta.grade))
        roadStyleSpinner.setSelection(styleLevels.indexOf(cesta.climbStyle))
        roadCharSpinner.setSelection(characterLevels.indexOf(cesta.roadChar))
    }

}

