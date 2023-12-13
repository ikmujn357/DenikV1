package com.example.denikv1

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class AddActivity : AppCompatActivity() {
    private val cestaModel: CestaModel = CestaModelImpl(this)
    private val cestaController: CestaController = CestaControllerImpl(cestaModel)
    private var selectedDate: Long = 0
    private var selectedButton: Button? = null
    private var gradeModifier: String = ""
    private var cestaId: Long = 0
    private var selectedButtonTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        setupSpinner()

        val plusButton: Button = findViewById(R.id.button_plus)
        val nulaButton: Button = findViewById(R.id.button_nula)
        val minusButton: Button = findViewById(R.id.button_minus)

        plusButton.setOnClickListener {
            updateGradeModifier("+", plusButton)
            onButtonClicked(plusButton)
        }

        nulaButton.setOnClickListener {
            updateGradeModifier("0", nulaButton)
            onButtonClicked(nulaButton)
        }

        minusButton.setOnClickListener {
            updateGradeModifier("-", minusButton)
            onButtonClicked(minusButton)
        }

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

        val receivedIntent = intent
        cestaId = receivedIntent.getLongExtra("cestaId", 0)

        if (cestaId != 0L) {
            lifecycleScope.launch {
                val cesta = cestaModel.getCestaById(cestaId)
                populateUI(cesta)
            }
        }
    }

    fun onButtonClicked(view: View) {
        // Odmáčkne předchozí tlačítko, pokud existuje
        selectedButton?.isSelected = false

        // Select the new icon
        view.isSelected = true

        // Update selectedButtonTag based on the clicked icon
        selectedButtonTag = when (view.id) {
            R.id.button_plus -> "plus"
            R.id.button_nula -> "nula"
            R.id.button_minus -> "minus"
            else -> null
        }

        selectedButton = view as? Button

        // Aktualizujte barvy tlačítek
        updateSelectedButtonView()
    }

    private fun updateSelectedButtonView() {

        val buttonPlus: Button = findViewById(R.id.button_plus)
        val buttonNula: Button = findViewById(R.id.button_nula)
        val buttonMinus: Button = findViewById(R.id.button_minus)

        // Reset barvy všech tlačítek
        buttonPlus.isSelected = false
        buttonNula.isSelected = false
        buttonMinus.isSelected = false

        // Nastavení barvy pozadí vybraného tlačítka
        val selectedColor = ContextCompat.getColor(this, R.color.purple_200)
        when (selectedButtonTag) {
            "plus" -> buttonPlus.isSelected = true
            "nula" -> buttonNula.isSelected = true
            "minus" -> buttonMinus.isSelected = true
        }

        // Reset barvy všech tlačítek
        buttonPlus.background.clearColorFilter()
        buttonNula.background.clearColorFilter()
        buttonMinus.background.clearColorFilter()

        when (selectedButtonTag) {
            "plus" -> buttonPlus.background.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP)
            "nula" -> buttonNula.background.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP)
            "minus" -> buttonMinus.background.setColorFilter(selectedColor, PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun setupSpinner() {
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadCharacterSpinner: Spinner = findViewById(R.id.characterSpinner)

        val difficultyLevels = resources.getStringArray(R.array.Grade)
        val styleLevels = resources.getStringArray(R.array.Style)
        val characterLevels = resources.getStringArray(R.array.Character)

        val adapterDif = CustomArrayAdapter(this, R.layout.item_spinner, difficultyLevels.toList())
        val adapterStyle = CustomArrayAdapter(this, R.layout.item_spinner, styleLevels.toList())
        val adapterCharacter = CustomArrayAdapter(this, R.layout.item_spinner, characterLevels.toList())

        roadGradeSpinner.adapter = adapterDif
        roadStyleSpinner.adapter = adapterStyle
        roadCharacterSpinner.adapter = adapterCharacter

        adapterDif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCharacter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        roadGradeSpinner.adapter = adapterDif
        roadStyleSpinner.adapter = adapterStyle
        roadCharacterSpinner.adapter = adapterCharacter
    }

    private fun updateGradeModifier(value: String, button: Button) {
        gradeModifier = value
        selectedButton?.isSelected = false
        button.isSelected = true
        selectedButton = button
    }

    private fun newCesta() {
        val roadNameEditText: EditText = findViewById(R.id.nameEditText)
        val fallEditText: EditText = findViewById(R.id.fallEditText)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadCharSpinner: Spinner = findViewById(R.id.characterSpinner)
        val minuteEditText: EditText = findViewById(R.id.minutesEditText)
        val secondEditText: EditText = findViewById(R.id.secondsEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val opinionEditText: EditText = findViewById(R.id.opinionEditText)

        val cestaName = roadNameEditText.text.toString()
        val fallCountString = fallEditText.text.toString()
        val styleSpinner = roadStyleSpinner.selectedItem.toString()
        val gradeSpinner = roadGradeSpinner.selectedItem.toString() + gradeModifier
        val charSpinner = roadCharSpinner.selectedItem.toString()
        val minuteString = minuteEditText.text.toString()
        val secondString = secondEditText.text.toString()
        val descriptionRoad = descriptionEditText.text.toString()
        val opinionRoad = opinionEditText.text.toString()

        val currentDate = if (selectedDate == 0L) System.currentTimeMillis() else selectedDate

        if (cestaName.isNotBlank() && fallCountString.isNotBlank() && minuteString.isNotBlank() && secondString.isNotBlank()
            && styleSpinner.isNotBlank() && gradeSpinner.isNotBlank() && charSpinner.isNotBlank()) {

            val newCesta = CestaEntity(
                roadName = cestaName,
                fallCount = fallCountString.toInt(),
                climbStyle = styleSpinner,
                grade = gradeSpinner,
                roadChar = charSpinner,
                timeMinute = minuteString.toInt(),
                timeSecond = secondString.toInt(),
                description = descriptionRoad,
                opinion = opinionRoad,
                date = currentDate
            )

            lifecycleScope.launch {
                if (cestaId != 0L) {
                    val existingCesta = cestaModel.getCestaById(cestaId)
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

                    cestaModel.addOrUpdateCesta(existingCesta)

                    cestaController.showToast("Cesta aktualizována!", Toast.LENGTH_LONG)
                    finish()
                } else {
                    cestaModel.addOrUpdateCesta(newCesta)

                    cestaController.showToast("Cesta přidána!", Toast.LENGTH_LONG)
                    finish()
                }
            }
        } else {
            cestaController.showToast("Nevyplnil jste všechno.", Toast.LENGTH_SHORT)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun populateUI(cesta: CestaEntity) {
        val roadNameEditText: EditText = findViewById(R.id.nameEditText)
        val fallEditText: EditText = findViewById(R.id.fallEditText)
        val roadStyleSpinner: Spinner = findViewById(R.id.styleSpinner)
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)
        val roadCharSpinner: Spinner = findViewById(R.id.characterSpinner)
        val minuteEditText: EditText = findViewById(R.id.minutesEditText)
        val secondEditText: EditText = findViewById(R.id.secondsEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val opinionEditText: EditText = findViewById(R.id.opinionEditText)

        roadNameEditText.setText(cesta.roadName)
        fallEditText.setText(cesta.fallCount.toString())
        minuteEditText.setText(cesta.timeMinute.toString())
        secondEditText.setText(cesta.timeSecond.toString())
        descriptionEditText.setText(cesta.description)
        opinionEditText.setText(cesta.opinion)

        // Nastavení vybraného tlačítka
        selectedButtonTag = when (cesta.grade.last()) {
            '+' -> "plus"
            '0' -> "nula"
            '-' -> "minus"
            else -> null
        }

        // Znovu aktualizovat zobrazení vybraného tlačítka
        updateSelectedButtonView()

        // Zobrazení data v CalendarView
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.date = cesta.date

        // Nastavení pozice v Spinnerch
        val difficultyLevels = resources.getStringArray(R.array.Grade)
        val styleLevels = resources.getStringArray(R.array.Style)
        val characterLevels = resources.getStringArray(R.array.Character)

        roadGradeSpinner.setSelection(difficultyLevels.indexOf(cesta.grade.dropLast(1)))
        roadStyleSpinner.setSelection(styleLevels.indexOf(cesta.climbStyle))
        roadCharSpinner.setSelection(characterLevels.indexOf(cesta.roadChar))
    }
}
