package com.example.denikv1

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Rozhraní pro datový model.
interface CestaModel {
    // Získání všech uložených cest
    suspend fun getAllCesta(): List<CestaEntity>
    // Odstranění cesty z databáze
    suspend fun removeCesta(cesta: CestaEntity)
    //Vložení nové cesty do databáze.
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
        opinion: String)
}

    class CestaModelImpl(private val context: Context) : CestaModel {
        private val cestaDao = AppDatabase.getDatabase(context).cestaDao()

        //Získání všech uložených cest z databáze
        override suspend fun getAllCesta(): List<CestaEntity> = withContext(Dispatchers.IO) {
            return@withContext cestaDao.getAllCesta()
        }
        // Odstranění cesty z databáze na pozadí   NEPOUŽÍVÁ SE ZATÍM
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
            opinion: String) {
            val newCesta = CestaEntity(
                    roadName = roadName,
                    fallCount = fallCount,
                    climbStyle = climbStyle,
                    grade = grade,
                    roadChar = roadChar,
                    timeMinute = timeMinute,
                    timeSecond = timeSecond,
                    description = description,
                    opinion = opinion)
            insertCesta(newCesta)
        }
}
