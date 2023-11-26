package com.example.denikv1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// Data Access Object pro manipulaci s entitou CestaEntity v databázi.
//obsahuje metody pro vkládání, získávání a mazání záznamů v tabulce CestaEntity.
@Dao
interface CestaDao {
    @Insert
    suspend fun insertCesta(cesta: CestaEntity)

    @Query("SELECT * FROM CestaEntity")
    suspend fun getAllCesta(): List<CestaEntity>

    @Delete
    suspend fun deleteCesta(cesta: CestaEntity)

    @Query("SELECT * FROM CestaEntity WHERE date = :selectedDate")
    suspend fun getAllCestaForDate(selectedDate: Long): List<CestaEntity>

    @Query("SELECT * FROM CestaEntity WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAllCestaForDateRange(startDate: Long, endDate: Long): List<CestaEntity>

    @Query("SELECT * FROM CestaEntity WHERE id = :cestaId")
    suspend fun getCestaById(cestaId: Long): CestaEntity

    @Query("SELECT * FROM CestaEntity WHERE roadName = :roadName")
    suspend fun getAllCestaByName(roadName: String): List<CestaEntity>
}