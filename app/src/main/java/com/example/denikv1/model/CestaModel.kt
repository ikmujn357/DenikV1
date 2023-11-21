package com.example.denikv1

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


interface CestaModel {
    suspend fun getAllCesta(): List<CestaEntity>
    suspend fun removeCesta(cesta: CestaEntity)
    suspend fun insertCesta(cesta: CestaEntity)
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
        override suspend fun getAllCesta(): List<CestaEntity> = withContext(Dispatchers.IO) {
            return@withContext cestaDao.getAllCesta()
        }

        override suspend fun removeCesta(cesta: CestaEntity) {
            cestaDao.deleteCesta(cesta)
        }

        override suspend fun insertCesta(cesta: CestaEntity) = withContext(Dispatchers.IO) {
            cestaDao.insertCesta(cesta)
        }


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
