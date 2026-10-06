package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_assets")
data class FinancialAsset(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String, // "INVESTISSEMENT", "IMMOBILIER", "LIQUIDITES", "CRYPTO", "BUSINESS", "DETTE_PASSIF"
    val value: Double,
    val currency: String = "€",
    val monthlyYieldPercent: Double = 0.0,
    val isLiability: Boolean = false,
    val notes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
