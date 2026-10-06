package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.AscendDao
import com.example.data.model.CashflowTransaction
import com.example.data.model.ExecutiveJournalEntry
import com.example.data.model.FinancialAsset
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        FinancialAsset::class,
        CashflowTransaction::class,
        PersonalHabit::class,
        StrategicGoalOKR::class,
        ExecutiveJournalEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AscendDatabase : RoomDatabase() {

    abstract fun ascendDao(): AscendDao

    companion object {
        @Volatile
        private var INSTANCE: AscendDatabase? = null

        fun getDatabase(context: Context): AscendDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AscendDatabase::class.java,
                    "ascend_pro_saas.db"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialSeedData(database.ascendDao())
                    }
                }
            }
        }

        suspend fun populateInitialSeedData(dao: AscendDao) {
            // Assets & Liabilities Seed
            dao.insertAllAssets(
                listOf(
                    FinancialAsset(
                        name = "Portefeuille PEA ETF MSCI World",
                        category = "INVESTISSEMENT",
                        value = 68500.0,
                        monthlyYieldPercent = 0.65,
                        isLiability = false,
                        notes = "DCA mensuel de 1 500€ automatisé"
                    ),
                    FinancialAsset(
                        name = "Appartement Locatif T2 (Lyon Part-Dieu)",
                        category = "IMMOBILIER",
                        value = 145000.0,
                        monthlyYieldPercent = 0.42,
                        isLiability = false,
                        notes = "Loué à 750€/mois hors charges"
                    ),
                    FinancialAsset(
                        name = "Trésorerie & Matelas de Sécurité",
                        category = "LIQUIDITES",
                        value = 18200.0,
                        monthlyYieldPercent = 0.25,
                        isLiability = false,
                        notes = "6 mois de runway de survie absolue"
                    ),
                    FinancialAsset(
                        name = "Holding & Équité Micro-SaaS",
                        category = "BUSINESS",
                        value = 35000.0,
                        monthlyYieldPercent = 1.2,
                        isLiability = false,
                        notes = "Revenus récurrents prévisibles"
                    ),
                    FinancialAsset(
                        name = "Cold Storage Bitcoin & Ethereum",
                        category = "CRYPTO",
                        value = 12800.0,
                        monthlyYieldPercent = 0.0,
                        isLiability = false,
                        notes = "Allocation asymétrique long terme"
                    ),
                    FinancialAsset(
                        name = "Emprunt Bancaire Immo Lyon",
                        category = "DETTE_PASSIF",
                        value = 98000.0,
                        monthlyYieldPercent = 0.0,
                        isLiability = true,
                        notes = "Taux fixe 1.35%, amortissement régulier"
                    )
                )
            )

            // Cashflow Transactions Seed
            dao.insertAllTransactions(
                listOf(
                    CashflowTransaction(
                        title = "Revenu Principal Exécutif",
                        amount = 4800.0,
                        type = "INCOME",
                        category = "SALAIRE_PRO",
                        date = "01 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Loyer Net Perçu (Locatif Lyon)",
                        amount = 750.0,
                        type = "INCOME",
                        category = "DIVIDENDES",
                        date = "05 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Revenus Récurrents SaaS",
                        amount = 1200.0,
                        type = "INCOME",
                        category = "BUSINESS_SAAS",
                        date = "04 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Loyer Résidence & Charges",
                        amount = 1350.0,
                        type = "EXPENSE",
                        category = "BESOINS_50",
                        date = "02 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Alimentation Santé & Épicerie",
                        amount = 550.0,
                        type = "EXPENSE",
                        category = "BESOINS_50",
                        date = "03 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Abonnements SaaS & Cloud Infra",
                        amount = 180.0,
                        type = "EXPENSE",
                        category = "BESOINS_50",
                        date = "02 Octobre"
                    ),
                    CashflowTransaction(
                        title = "Expériences & Lifestyle",
                        amount = 650.0,
                        type = "EXPENSE",
                        category = "ENVIE_30",
                        date = "04 Octobre"
                    ),
                    CashflowTransaction(
                        title = "DCA Automatisé ETF World",
                        amount = 1500.0,
                        type = "EXPENSE",
                        category = "EPARGNE_20",
                        date = "05 Octobre"
                    )
                )
            )

            // Habits Seed
            dao.insertAllHabits(
                listOf(
                    PersonalHabit(
                        title = "Méditation & Clarté Mentale (15 min)",
                        category = "MINDSET",
                        currentStreak = 14,
                        bestStreak = 28,
                        isCompletedToday = true,
                        impactWeight = 10,
                        iconName = "SelfImprovement"
                    ),
                    PersonalHabit(
                        title = "Session Deep Work Focus (90 min)",
                        category = "DEEP_WORK",
                        currentStreak = 9,
                        bestStreak = 19,
                        isCompletedToday = true,
                        impactWeight = 15,
                        iconName = "Psychology"
                    ),
                    PersonalHabit(
                        title = "Lecture Finance, Stratégie & Bio (30 min)",
                        category = "MINDSET",
                        currentStreak = 21,
                        bestStreak = 45,
                        isCompletedToday = false,
                        impactWeight = 10,
                        iconName = "AutoStories"
                    ),
                    PersonalHabit(
                        title = "Entraînement Athlétique / HIIT",
                        category = "SANTE",
                        currentStreak = 5,
                        bestStreak = 14,
                        isCompletedToday = true,
                        impactWeight = 15,
                        iconName = "FitnessCenter"
                    ),
                    PersonalHabit(
                        title = "Zéro Dépense Impulsive & Contrôle Cashflow",
                        category = "FINANCES",
                        currentStreak = 30,
                        bestStreak = 30,
                        isCompletedToday = true,
                        impactWeight = 15,
                        iconName = "Savings"
                    ),
                    PersonalHabit(
                        title = "Bilan Exécutif & Débriefing du Soir",
                        category = "DEEP_WORK",
                        currentStreak = 12,
                        bestStreak = 22,
                        isCompletedToday = false,
                        impactWeight = 10,
                        iconName = "FactCheck"
                    )
                )
            )

            // OKR Goals Seed
            dao.insertAllGoals(
                listOf(
                    StrategicGoalOKR(
                        pillar = "FINANCE",
                        title = "Portefeuille Actifs Liquides 100k€",
                        targetValue = 100000.0,
                        currentValue = 86700.0,
                        unit = "€",
                        targetDate = "31 Déc 2026"
                    ),
                    StrategicGoalOKR(
                        pillar = "MAITRISE_PERSO",
                        title = "Maîtriser 15 Livres de Stratégie & Finance",
                        targetValue = 15.0,
                        currentValue = 9.0,
                        unit = "livres",
                        targetDate = "15 Nov 2026"
                    ),
                    StrategicGoalOKR(
                        pillar = "BUSINESS",
                        title = "MRR SaaS Récurrent à 3 000€/mois",
                        targetValue = 3000.0,
                        currentValue = 1950.0,
                        unit = "€/m",
                        targetDate = "01 Jan 2027"
                    ),
                    StrategicGoalOKR(
                        pillar = "SANTE",
                        title = "Optimisation Métabolique & Vo2Max",
                        targetValue = 50.0,
                        currentValue = 44.5,
                        unit = "ml/kg",
                        targetDate = "20 Déc 2026"
                    )
                )
            )

            // Executive Journal Seed
            dao.insertAllJournalEntries(
                listOf(
                    ExecutiveJournalEntry(
                        dateStr = "Aujourd'hui",
                        focusScore = 5,
                        financialDisciplineScore = 5,
                        energyScore = 4,
                        mainWins = "Négociation de contrat SaaS clôturée (+1 200€/mois) & DCA de 1 500€ investi sans hésitation.",
                        strategicLesson = "Principe de Ray Dalio appliqué : confronter la réalité sans filtre élimine toute anxiété décisionnelle."
                    ),
                    ExecutiveJournalEntry(
                        dateStr = "Hier",
                        focusScore = 4,
                        financialDisciplineScore = 5,
                        energyScore = 5,
                        mainWins = "Session de Deep Work de 90 min sans aucune distraction le matin + 30 min de lecture financière.",
                        strategicLesson = "Inversion de Munger : en évitant les mauvaises décisions d'achat, on gagne plus qu'en cherchant des rendements risqués."
                    )
                )
            )
        }
    }
}
