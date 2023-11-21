package com.example.denikv1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CestaDao {
    @Query("SELECT * FROM cesta_table")
    fun getAllCesty(): Flow<List<CestaEntity>>

    @Insert
    suspend fun insertCesta(cesta: CestaEntity)
}