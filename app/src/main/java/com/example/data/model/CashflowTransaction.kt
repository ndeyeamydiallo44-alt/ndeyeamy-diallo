package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashflow_transactions")
data class CashflowTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val type: String, // "INCOME" or "EXPENSE"
    val category: String, // "SALAIRE_PRO", "BUSINESS_SAAS", "DIVIDENDES", "IMMOBILIER", "BESOINS_50", "ENVIE_30", "EPARGNE_20"
    val date: String,
    val isRecurring: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)
