package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AscendDatabase
import com.example.data.model.CashflowTransaction
import com.example.data.model.ExecutiveJournalEntry
import com.example.data.model.FinancialAsset
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
import com.example.data.repository.AscendRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.ln
import kotlin.math.pow

data class FinancialSummary(
    val totalGrossAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val netWorth: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val netSavings: Double = 0.0,
    val savingsRate: Double = 0.0,
    val liquidCash: Double = 0.0,
    val runwayMonths: Double = 0.0
)

data class MasterySummary(
    val completedHabitsToday: Int = 0,
    val totalHabits: Int = 0,
    val habitCompletionPercent: Int = 0,
    val masteryIndexScore: Int = 0,
    val activeOkrsCount: Int = 0,
    val avgOkrProgressPercent: Int = 0
)

data class SimulatorParams(
    val initialCapital: Double = 50000.0,
    val monthlySavings: Double = 1500.0,
    val annualReturnPercent: Double = 8.0,
    val horizonYears: Int = 15,
    val annualExpenses: Double = 30000.0
)

data class SimulationResult(
    val finalCapital: Double,
    val totalInvested: Double,
    val totalGains: Double,
    val fireTarget: Double,
    val yearsToFire: Double,
    val isFireReachedAtHorizon: Boolean
)

class AscendViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AscendRepository

    val assets: StateFlow<List<FinancialAsset>>
    val transactions: StateFlow<List<CashflowTransaction>>
    val habits: StateFlow<List<PersonalHabit>>
    val goals: StateFlow<List<StrategicGoalOKR>>
    val journalEntries: StateFlow<List<ExecutiveJournalEntry>>

    // Simulator State
    private val _simulatorParams = MutableStateFlow(SimulatorParams())
    val simulatorParams: StateFlow<SimulatorParams> = _simulatorParams.asStateFlow()

    init {
        val db = AscendDatabase.getDatabase(application)
        repository = AscendRepository(db.ascendDao())

        assets = repository.allAssets
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        transactions = repository.allTransactions
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        habits = repository.allHabits
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        goals = repository.allGoals
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        journalEntries = repository.allJournalEntries
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        viewModelScope.launch {
            repository.checkAndSeedIfEmpty()
        }
    }

    // Computed Financial KPI
    val financialSummary: StateFlow<FinancialSummary> = combine(assets, transactions) { assetList, txList ->
        val grossAssets = assetList.filter { !it.isLiability }.sumOf { it.value }
        val liabilities = assetList.filter { it.isLiability }.sumOf { it.value }
        val net = grossAssets - liabilities

        val income = txList.filter { it.type == "INCOME" }.sumOf { it.amount }
        val expenses = txList.filter { it.type == "EXPENSE" }.sumOf { it.amount }
        val savings = income - expenses
        val rate = if (income > 0) ((savings / income) * 100).coerceIn(0.0, 100.0) else 0.0

        val liquid = assetList.filter { it.category == "LIQUIDITES" }.sumOf { it.value }
        val runway = if (expenses > 0) liquid / expenses else 12.0

        FinancialSummary(
            totalGrossAssets = grossAssets,
            totalLiabilities = liabilities,
            netWorth = net,
            totalIncome = income,
            totalExpenses = expenses,
            netSavings = savings,
            savingsRate = rate,
            liquidCash = liquid,
            runwayMonths = runway
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FinancialSummary())

    // Computed Mastery KPI
    val masterySummary: StateFlow<MasterySummary> = combine(habits, goals, financialSummary) { habitList, goalList, finSummary ->
        val totalH = habitList.size
        val completedH = habitList.count { it.isCompletedToday }
        val habitPercent = if (totalH > 0) (completedH * 100) / totalH else 0

        val activeOkrs = goalList.filter { !it.isCompleted }
        val avgOkr = if (goalList.isNotEmpty()) {
            (goalList.map { it.progressRatio }.average() * 100).toInt()
        } else 0

        // Comprehensive Executive Mastery Index (Weighted calculation)
        // 45% Habits + 30% Savings Rate + 25% Goal progress
        val index = (habitPercent * 0.45 + finSummary.savingsRate.coerceIn(0.0, 100.0) * 0.30 + avgOkr * 0.25).toInt()
            .coerceIn(0, 100)

        MasterySummary(
            completedHabitsToday = completedH,
            totalHabits = totalH,
            habitCompletionPercent = habitPercent,
            masteryIndexScore = index,
            activeOkrsCount = activeOkrs.size,
            avgOkrProgressPercent = avgOkr
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MasterySummary())

    // Actions: Assets
    fun addAsset(name: String, category: String, value: Double, isLiability: Boolean, notes: String) {
        viewModelScope.launch {
            repository.insertAsset(
                FinancialAsset(
                    name = name,
                    category = category,
                    value = value,
                    isLiability = isLiability,
                    notes = notes
                )
            )
        }
    }

    fun deleteAsset(asset: FinancialAsset) {
        viewModelScope.launch {
            repository.deleteAsset(asset)
        }
    }

    // Actions: Transactions
    fun addTransaction(title: String, amount: Double, type: String, category: String, date: String) {
        viewModelScope.launch {
            repository.insertTransaction(
                CashflowTransaction(
                    title = title,
                    amount = amount,
                    type = type,
                    category = category,
                    date = date
                )
            )
        }
    }

    fun deleteTransaction(transaction: CashflowTransaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    // Actions: Habits
    fun toggleHabit(habit: PersonalHabit) {
        viewModelScope.launch {
            repository.toggleHabitCompletion(habit)
        }
    }

    fun addHabit(title: String, category: String, targetFrequency: String, impactWeight: Int) {
        viewModelScope.launch {
            repository.insertHabit(
                PersonalHabit(
                    title = title,
                    category = category,
                    targetFrequency = targetFrequency,
                    impactWeight = impactWeight
                )
            )
        }
    }

    fun deleteHabit(habit: PersonalHabit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    // Actions: Goals
    fun addGoal(title: String, pillar: String, targetValue: Double, currentValue: Double, unit: String, targetDate: String) {
        viewModelScope.launch {
            repository.insertGoal(
                StrategicGoalOKR(
                    title = title,
                    pillar = pillar,
                    targetValue = targetValue,
                    currentValue = currentValue,
                    unit = unit,
                    targetDate = targetDate
                )
            )
        }
    }

    fun updateGoalProgress(goal: StrategicGoalOKR, delta: Double) {
        viewModelScope.launch {
            repository.updateGoalProgress(goal, delta)
        }
    }

    fun deleteGoal(goal: StrategicGoalOKR) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
        }
    }

    // Actions: Journal
    fun addJournalEntry(dateStr: String, focus: Int, financialDiscipline: Int, energy: Int, wins: String, lesson: String) {
        viewModelScope.launch {
            repository.insertJournalEntry(
                ExecutiveJournalEntry(
                    dateStr = dateStr,
                    focusScore = focus,
                    financialDisciplineScore = financialDiscipline,
                    energyScore = energy,
                    mainWins = wins,
                    strategicLesson = lesson
                )
            )
        }
    }

    fun deleteJournalEntry(entry: ExecutiveJournalEntry) {
        viewModelScope.launch {
            repository.deleteJournalEntry(entry)
        }
    }

    // Actions: Simulator
    fun updateSimulator(
        initialCapital: Double = _simulatorParams.value.initialCapital,
        monthlySavings: Double = _simulatorParams.value.monthlySavings,
        annualReturnPercent: Double = _simulatorParams.value.annualReturnPercent,
        horizonYears: Int = _simulatorParams.value.horizonYears,
        annualExpenses: Double = _simulatorParams.value.annualExpenses
    ) {
        _simulatorParams.value = SimulatorParams(
            initialCapital = initialCapital,
            monthlySavings = monthlySavings,
            annualReturnPercent = annualReturnPercent,
            horizonYears = horizonYears,
            annualExpenses = annualExpenses
        )
    }

    fun calculateSimulation(): SimulationResult {
        val p = _simulatorParams.value
        val r = (p.annualReturnPercent / 100.0) / 12.0 // monthly interest
        val n = (p.horizonYears * 12).toDouble()

        // Future Value of initial capital + monthly annuities
        val fvInitial = p.initialCapital * (1.0 + r).pow(n)
        val fvAnnuity = if (r > 0) p.monthlySavings * (((1.0 + r).pow(n) - 1.0) / r) else p.monthlySavings * n
        val finalCapital = fvInitial + fvAnnuity

        val totalInvested = p.initialCapital + (p.monthlySavings * n)
        val totalGains = maxOf(0.0, finalCapital - totalInvested)

        // FIRE calculation: Rule of 25x annual spending
        val fireTarget = p.annualExpenses * 25.0

        // Time to FIRE formula estimation:
        val yearsToFire = if (p.monthlySavings > 0 && r > 0 && fireTarget > p.initialCapital) {
            val numerator = ln((fireTarget * r + p.monthlySavings) / (p.initialCapital * r + p.monthlySavings))
            val denominator = ln(1.0 + r) * 12.0
            if (denominator > 0) (numerator / denominator).coerceAtLeast(0.0) else 0.0
        } else if (p.initialCapital >= fireTarget) {
            0.0
        } else {
            25.0
        }

        return SimulationResult(
            finalCapital = finalCapital,
            totalInvested = totalInvested,
            totalGains = totalGains,
            fireTarget = fireTarget,
            yearsToFire = yearsToFire,
            isFireReachedAtHorizon = finalCapital >= fireTarget
        )
    }
}
