package com.example.denikv1

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Rozhraní pro datový model.
interface CestaModel {
    // Získání všech uložených cest
    suspend fun getAllCesta(): List<CestaEntity>
    // Odstranění cesty z databáze NEPOUŽÍVÁ SE ZATÍM
    suspend fun removeCesta(cesta: CestaEntity)
    // Vložení nové cesty do databáze.
    suspend fun insertCesta(cesta: CestaEntity)
    // Přidání nové cesty s informacemi
    suspend fun addNewCesta(
        roadName: String,
        fallCount: Int,
        climbStyle: String,
        grade: String,
        roadChar: String,
        timeMinute: Int,
        timeSecond: Int,
        description: String,
        opinion: String,
        selectedDate: Long
    )

    // Export dat do souboru ve formátu UTF-8
    suspend fun exportDataToFile(context: Context, fileName: String)

    suspend fun getAllCestaForDate(selectedDate: Long): List<CestaEntity>
    suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity>
}

class CestaModelImpl(private val context: Context) : CestaModel {
    private val cestaDao = AppDatabase.getDatabase(context).cestaDao()

    //Získání všech uložených cest z databáze
    override suspend fun getAllCesta(): List<CestaEntity> = withContext(Dispatchers.IO) {
        return@withContext cestaDao.getAllCesta()
    }

    // Odstranění cesty z databáze   NEPOUŽÍVÁ SE ZATÍM
    override suspend fun removeCesta(cesta: CestaEntity) {
        cestaDao.deleteCesta(cesta)
    }

    // Vložení nové cesty do databáze
    override suspend fun insertCesta(cesta: CestaEntity) = withContext(Dispatchers.IO) {
        cestaDao.insertCesta(cesta)
    }


    // Přidání nové cesty s poskytnutými informacemi.
    override suspend fun addNewCesta(
        roadName: String,
        fallCount: Int,
        climbStyle: String,
        grade: String,
        roadChar: String,
        timeMinute: Int,
        timeSecond: Int,
        description: String,
        opinion: String,
        selectedDate: Long
    ) {
        val newCesta = CestaEntity(
            roadName = roadName,
            fallCount = fallCount,
            climbStyle = climbStyle,
            grade = grade,
            roadChar = roadChar,
            timeMinute = timeMinute,
            timeSecond = timeSecond,
            description = description,
            opinion = opinion,
            date = selectedDate
        )
        insertCesta(newCesta)
    }

    // Export dat do souboru ve formátu UTF-8
    override suspend fun exportDataToFile(context: Context, fileName: String) {
        withContext(Dispatchers.IO) {
            val data = cestaDao.getAllCesta()
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

            try {
                val csvWriter = FileWriter(file, false)

                // Header
                csvWriter.append("Jméno cesty,Počet pádů,Styl přelezu,Obtížnost,Charakter cesty,Minuty,Sekundy,Popis cesty,Názor, Datum\n")

                // Data
                data.forEach { cesta ->
                    csvWriter.append("${cesta.roadName},${cesta.fallCount},${cesta.climbStyle},${cesta.grade},${cesta.roadChar},${cesta.timeMinute},${cesta.timeSecond},${cesta.description},${cesta.opinion},${formatDate(cesta.date)}\n")
                }

                csvWriter.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun formatDate(date: Long): String {
        val pattern = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(Date(date))
    }

    override suspend fun getAllCestaForDate(selectedDate: Long): List<CestaEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext cestaDao.getAllCestaForDate(selectedDate)
        }
    }

    override suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext cestaDao.getAllCestaForDateRange(startDate, endDate)
        }
    }
}
