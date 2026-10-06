package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CashflowTransaction
import com.example.data.model.ExecutiveJournalEntry
import com.example.data.model.FinancialAsset
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
import kotlinx.coroutines.flow.Flow

@Dao
interface AscendDao {

    // --- Financial Assets ---
    @Query("SELECT * FROM financial_assets ORDER BY isLiability ASC, value DESC")
    fun getAllAssets(): Flow<List<FinancialAsset>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: FinancialAsset): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAssets(assets: List<FinancialAsset>)

    @Update
    suspend fun updateAsset(asset: FinancialAsset)

    @Delete
    suspend fun deleteAsset(asset: FinancialAsset)

    // --- Cashflow Transactions ---
    @Query("SELECT * FROM cashflow_transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<CashflowTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: CashflowTransaction): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTransactions(transactions: List<CashflowTransaction>)

    @Delete
    suspend fun deleteTransaction(transaction: CashflowTransaction)

    // --- Personal Habits ---
    @Query("SELECT * FROM personal_habits ORDER BY isCompletedToday ASC, currentStreak DESC")
    fun getAllHabits(): Flow<List<PersonalHabit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: PersonalHabit): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHabits(habits: List<PersonalHabit>)

    @Update
    suspend fun updateHabit(habit: PersonalHabit)

    @Delete
    suspend fun deleteHabit(habit: PersonalHabit)

    // --- Strategic OKR Goals ---
    @Query("SELECT * FROM strategic_goals ORDER BY isCompleted ASC, id ASC")
    fun getAllGoals(): Flow<List<StrategicGoalOKR>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: StrategicGoalOKR): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGoals(goals: List<StrategicGoalOKR>)

    @Update
    suspend fun updateGoal(goal: StrategicGoalOKR)

    @Delete
    suspend fun deleteGoal(goal: StrategicGoalOKR)

    // --- Executive Journal ---
    @Query("SELECT * FROM executive_journal ORDER BY timestamp DESC")
    fun getAllJournalEntries(): Flow<List<ExecutiveJournalEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: ExecutiveJournalEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllJournalEntries(entries: List<ExecutiveJournalEntry>)

    @Delete
    suspend fun deleteJournalEntry(entry: ExecutiveJournalEntry)
}
