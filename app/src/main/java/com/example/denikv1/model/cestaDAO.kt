package com.example.denikv1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CestaDao {
    @Insert
    suspend fun insertCesta(cesta: CestaEntity)

    @Query("SELECT * FROM CestaEntity")
    suspend fun getAllCesta(): List<CestaEntity>

    @Delete
    suspend fun deleteCesta(cesta: CestaEntity)
}