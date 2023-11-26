package com.example.denikv1

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface CestaController {
    suspend fun addCesta(cesta: CestaEntity)
    suspend fun getAllCesta(): List<CestaEntity>
    suspend fun exportDataToFile(context: Context, fileName: String)

    suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity>
    suspend fun getAllCestaForDate(selectedDate: Long): List<CestaEntity>
    suspend fun getAllCestaByName(roadName: String): List<CestaEntity>
}

class CestaControllerImpl(
    private val cestaModel: CestaModel,
) : CestaController {

    override suspend fun addCesta(cesta: CestaEntity) {
        cestaModel.insertCesta(cesta)
        getAllCesta()
    }

    override suspend fun getAllCesta(): List<CestaEntity> {
        return cestaModel.getAllCesta()
    }

    override suspend fun exportDataToFile(context: Context, fileName: String) {
        cestaModel.exportDataToFile(context, fileName)
    }

    override suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity> {
        return cestaModel.getAllCestaForDateRange(startDate, endDate)
    }
    override suspend fun getAllCestaForDate(date: Long): List<CestaEntity> {
        return cestaModel.getAllCestaForDate(date)
    }
    override suspend fun getAllCestaByName(roadName: String): List<CestaEntity> {
        return cestaModel.getAllCestaByName(roadName)
    }
}



