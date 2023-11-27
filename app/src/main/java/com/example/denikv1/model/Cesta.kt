package com.example.denikv1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entita reprezentující informace o lezení na cestě.
@Entity
data class CestaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var roadName: String,
    var fallCount: Int,
    var climbStyle: String,
    var grade: String,
    var roadChar: String,
    var timeMinute: Int,
    var timeSecond: Int,
    var description: String,
    var opinion: String,
    var date: Long
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
