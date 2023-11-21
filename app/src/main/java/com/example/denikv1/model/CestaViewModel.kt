package com.example.denikv1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map


class CestaViewModel(private val cestaDao: CestaDao) : ViewModel() {

    fun saveCesta(cesta: CestaEntity) {
        val cestaEntity = CestaEntity(
            roadName = cesta.roadName,
            fallCount = cesta.fallCount,
            climbStyle = cesta.climbStyle,
            grade = cesta.grade,
            roadChar = cesta.roadChar,
            timeMinute = cesta.timeMinute,
            timeSecond = cesta.timeSecond,
            description = cesta.description,
            opinion = cesta.opinion
        )

        viewModelScope.launch {
            cestaDao.insertCesta(cestaEntity)
        }
    }

    fun getCesty(): Flow<List<CestaEntity>> {
        return cestaDao.getAllCesty().map { cestaEntities: List<CestaEntity> ->
            cestaEntities.map { cestaEntity ->
                CestaEntity(
                    id = cestaEntity.id,
                    roadName = cestaEntity.roadName,
                    fallCount = cestaEntity.fallCount,
                    climbStyle = cestaEntity.climbStyle,
                    grade = cestaEntity.grade,
                    roadChar = cestaEntity.roadChar,
                    timeMinute = cestaEntity.timeMinute,
                    timeSecond = cestaEntity.timeSecond,
                    description = cestaEntity.description,
                    opinion = cestaEntity.opinion
                )
            }
        }
    }}
