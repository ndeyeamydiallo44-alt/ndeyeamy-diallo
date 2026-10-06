package com.example.data.repository

import com.example.data.dao.AscendDao
import com.example.data.database.AscendDatabase
import com.example.data.model.CashflowTransaction
import com.example.data.model.ExecutiveJournalEntry
import com.example.data.model.FinancialAsset
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AscendRepository(private val dao: AscendDao) {

    val allAssets: Flow<List<FinancialAsset>> = dao.getAllAssets()
    val allTransactions: Flow<List<CashflowTransaction>> = dao.getAllTransactions()
    val allHabits: Flow<List<PersonalHabit>> = dao.getAllHabits()
    val allGoals: Flow<List<StrategicGoalOKR>> = dao.getAllGoals()
    val allJournalEntries: Flow<List<ExecutiveJournalEntry>> = dao.getAllJournalEntries()

    suspend fun checkAndSeedIfEmpty() {
        val existingAssets = dao.getAllAssets().first()
        if (existingAssets.isEmpty()) {
            AscendDatabase.populateInitialSeedData(dao)
        }
    }

    // Assets
    suspend fun insertAsset(asset: FinancialAsset) = dao.insertAsset(asset)
    suspend fun updateAsset(asset: FinancialAsset) = dao.updateAsset(asset)
    suspend fun deleteAsset(asset: FinancialAsset) = dao.deleteAsset(asset)

    // Transactions
    suspend fun insertTransaction(transaction: CashflowTransaction) = dao.insertTransaction(transaction)
    suspend fun deleteTransaction(transaction: CashflowTransaction) = dao.deleteTransaction(transaction)

    // Habits
    suspend fun insertHabit(habit: PersonalHabit) = dao.insertHabit(habit)
    suspend fun updateHabit(habit: PersonalHabit) = dao.updateHabit(habit)
    suspend fun deleteHabit(habit: PersonalHabit) = dao.deleteHabit(habit)

    suspend fun toggleHabitCompletion(habit: PersonalHabit) {
        val newCompleted = !habit.isCompletedToday
        val newStreak = if (newCompleted) habit.currentStreak + 1 else maxOf(0, habit.currentStreak - 1)
        val newBest = maxOf(habit.bestStreak, newStreak)
        dao.updateHabit(
            habit.copy(
                isCompletedToday = newCompleted,
                currentStreak = newStreak,
                bestStreak = newBest
            )
        )
    }

    // OKR Goals
    suspend fun insertGoal(goal: StrategicGoalOKR) = dao.insertGoal(goal)
    suspend fun updateGoal(goal: StrategicGoalOKR) = dao.updateGoal(goal)
    suspend fun deleteGoal(goal: StrategicGoalOKR) = dao.deleteGoal(goal)

    suspend fun updateGoalProgress(goal: StrategicGoalOKR, delta: Double) {
        val newCurrent = (goal.currentValue + delta).coerceAtLeast(0.0)
        val isCompleted = newCurrent >= goal.targetValue
        dao.updateGoal(goal.copy(currentValue = newCurrent, isCompleted = isCompleted))
    }

    // Journal
    suspend fun insertJournalEntry(entry: ExecutiveJournalEntry) = dao.insertJournalEntry(entry)
    suspend fun deleteJournalEntry(entry: ExecutiveJournalEntry) = dao.deleteJournalEntry(entry)
}
