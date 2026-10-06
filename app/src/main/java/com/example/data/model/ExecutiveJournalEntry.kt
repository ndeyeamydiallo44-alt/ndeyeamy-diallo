package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "executive_journal")
data class ExecutiveJournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateStr: String,
    val focusScore: Int = 5, // 1 to 5
    val financialDisciplineScore: Int = 5, // 1 to 5
    val energyScore: Int = 5, // 1 to 5
    val mainWins: String,
    val strategicLesson: String,
    val timestamp: Long = System.currentTimeMillis()
)
