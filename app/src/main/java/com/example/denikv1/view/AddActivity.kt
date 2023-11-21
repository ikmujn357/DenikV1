package com.example.denikv1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private val cestaModel: CestaModel = CestaModelImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zapis)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        setupDifficultySpinner()

        val addCestaButton: Button = findViewById(R.id.saveButton)
        addCestaButton.setOnClickListener {
            newCesta()
        }
    }

    private fun setupDifficultySpinner() {
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)

        val difficultyLevels = resources.getStringArray(R.array.Grade)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevels)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roadGradeSpinner.adapter = adapter
    }

    private fun newCesta() {
        val roadNameEditText: EditText = findViewById(R.id.nameEditText)
        val roadGradeSpinner: Spinner = findViewById(R.id.difficultySpinner)

        val cestaName = roadNameEditText.text.toString()
        val gradeSpinner = roadGradeSpinner.selectedItem.toString()

        if (cestaName.isNotBlank() && gradeSpinner.isNotBlank()) {
            lifecycleScope.launch {
                cestaModel.addNewCesta(
                    cestaName,
                    0,
                    "TR",
                    gradeSpinner,
                    "Technická",
                    2,
                    24,
                    "Pěkná lehká cesta",
                    "Dolez do topu byl super"
                )
                finish()
                showToast("Cesta přidána!", Toast.LENGTH_SHORT)
            }
        } else {
            showToast("Vyplňte prosím všechny informace.", Toast.LENGTH_SHORT)
        }
    }

    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
