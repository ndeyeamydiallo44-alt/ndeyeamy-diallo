package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_habits")
data class PersonalHabit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String, // "MINDSET", "SANTE", "DEEP_WORK", "FINANCES"
    val targetFrequency: String = "Quotidien",
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val isCompletedToday: Boolean = false,
    val impactWeight: Int = 10,
    val iconName: String = "Star"
)
