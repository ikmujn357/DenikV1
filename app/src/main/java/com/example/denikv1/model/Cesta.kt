package com.example.denikv1

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entita reprezentující informace o lezení na cestě.
@Entity()
data class CestaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val roadName: String,
    val fallCount: Int,
    val climbStyle: String,
    val grade: String,
    val roadChar: String,
    val timeMinute: Int,
    val timeSecond: Int,
    val description: String,
    val opinion: String,
    val date: Long
) {
    // Konstruktor pro vytvoření instance
    constructor(
        roadName: String,
        fallCount: Int,
        climbStyle: String,
        grade: String,
        roadChar: String,
        timeMinute: Int,
        timeSecond: Int,
        description: String,
        opinion: String,
        date: Long
    ) : this(
        0, roadName, fallCount, climbStyle, grade, roadChar, timeMinute, timeSecond, description, opinion, date
    )
}
