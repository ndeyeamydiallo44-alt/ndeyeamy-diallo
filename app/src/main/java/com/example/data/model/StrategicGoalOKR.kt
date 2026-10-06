package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strategic_goals")
data class StrategicGoalOKR(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pillar: String, // "FINANCE", "MAITRISE_PERSO", "BUSINESS", "SANTE"
    val title: String,
    val targetValue: Double,
    val currentValue: Double,
    val unit: String,
    val targetDate: String,
    val isCompleted: Boolean = false
) {
    val progressRatio: Float
        get() = if (targetValue > 0) (currentValue / targetValue).coerceIn(0.0, 1.0).toFloat() else 0f
}
