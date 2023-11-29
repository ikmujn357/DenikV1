package com.example.denikv1

import android.content.Context
import android.widget.Toast

// Rozhraní
interface CestaController {


    // Metoda pro získání všech cest
    suspend fun getAllCesta(): List<CestaEntity>

    // Metoda pro export dat do souboru
    suspend fun exportDataToFile(context: Context, fileName: String)

    // Metoda pro získání všech cest v daném datovém rozsahu
    suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity>

    // Metoda pro získání všech cest se zadaným názvem
    suspend fun getAllCestaByName(roadName: String): List<CestaEntity>

    // Metoda pro validaci a přidání nové cesty
    suspend fun validateAndAddNewCesta(
        cestaName: String,
        fallCountString: String,
        styleSpinner: String,
        gradeSpinner: String,
        charSpinner: String,
        minuteString: String,
        secondString: String,
        descriptionRoad: String,
        opinionRoad: String,
        selectedDate: Long
    )

    // Metoda pro zobrazení Toast zprávy
    fun showToast(message: String, duration: Int)
}

// Implementace rozhraní CestaController
class CestaControllerImpl(private val cestaModel: CestaModel) : CestaController {

    // Metoda pro získání všech cest
    override suspend fun getAllCesta(): List<CestaEntity> {
        return cestaModel.getAllCesta()
    }

    // Metoda pro export dat do souboru
    override suspend fun exportDataToFile(context: Context, fileName: String) {
        cestaModel.exportDataToFile(context, fileName)
    }

    // Metoda pro získání všech cest v daném datovém rozsahu
    override suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity> {
        return cestaModel.getAllCestaForDateRange(startDate, endDate)
    }

    // Metoda pro získání všech cest se zadaným názvem
    override suspend fun getAllCestaByName(roadName: String): List<CestaEntity> {
        return cestaModel.getAllCestaByName(roadName)
    }

    // Metoda  přidání nové cesty s kontrolou, zda jsou vyplňěný některé údaje
    override suspend fun validateAndAddNewCesta(
        cestaName: String,
        fallCountString: String,
        styleSpinner: String,
        gradeSpinner: String,
        charSpinner: String,
        minuteString: String,
        secondString: String,
        descriptionRoad: String,
        opinionRoad: String,
        selectedDate: Long
    ) {
        // Validace vstupních polí
        if (cestaName.isNotBlank() && fallCountString.isNotBlank() && minuteString.isNotBlank() && secondString.isNotBlank()
            && styleSpinner.isNotBlank() && gradeSpinner.isNotBlank() && charSpinner.isNotBlank()) {

            // Vytvoření instance CestaEntity s použitím konstruktoru
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
                date = if (selectedDate == 0L) System.currentTimeMillis() else selectedDate
            )

            // Přidání nebo aktualizace cesty v modelu
            cestaModel.addOrUpdateCesta(newCesta)
        } else {
            // Případ, kdy některé pole není vyplněno
            showToast("Nevyplnil jste všechno.", Toast.LENGTH_SHORT)
        }
    }

    // Metoda pro zobrazení Toast zprávy
    override fun showToast(message: String, duration: Int) {

    }
}
