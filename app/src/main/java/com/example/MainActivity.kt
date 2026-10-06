package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddAssetDialog
import com.example.ui.components.AddGoalDialog
import com.example.ui.components.AddHabitDialog
import com.example.ui.components.AddJournalDialog
import com.example.ui.components.AddTransactionDialog
import com.example.ui.components.AscendBottomNavigation
import com.example.ui.components.NavScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FinancesScreen
import com.example.ui.screens.MasteryScreen
import com.example.ui.screens.PrinciplesJournalScreen
import com.example.ui.screens.SimulatorScreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.PrestigeGoldLight
import com.example.ui.theme.SlateMuted
import com.example.viewmodel.AscendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AscendApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AscendApp(viewModel: AscendViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<NavScreen>(NavScreen.Dashboard) }

    // Dialog States
    var showAddAssetDialog by remember { mutableStateOf(false) }
    var showAddTxDialog by remember { mutableStateOf(false) }
    var showAddHabitDialog by remember { mutableStateOf(false) }
    var showAddGoalDialog by remember { mutableStateOf(false) }
    var showAddJournalDialog by remember { mutableStateOf(false) }

    // Flow Collections
    val financialSummary by viewModel.financialSummary.collectAsStateWithLifecycle()
    val masterySummary by viewModel.masterySummary.collectAsStateWithLifecycle()
    val assets by viewModel.assets.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val habits by viewModel.habits.collectAsStateWithLifecycle()
    val goals by viewModel.goals.collectAsStateWithLifecycle()
    val journalEntries by viewModel.journalEntries.collectAsStateWithLifecycle()
    val simulatorParams by viewModel.simulatorParams.collectAsStateWithLifecycle()

    // Back handling for navigation
    BackHandler(enabled = currentScreen != NavScreen.Dashboard) {
        currentScreen = NavScreen.Dashboard
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianDark,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(PrestigeGold.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                    .border(1.dp, PrestigeGold.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Diamond,
                                    contentDescription = "Logo",
                                    tint = PrestigeGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = "ASCEND PRO",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = PlatinumWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "Finances & Maîtrise Personnelle",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SlateMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        // SaaS Membership Status Badge
                        Box(
                            modifier = Modifier
                                .background(MidnightNavy, RoundedCornerShape(20.dp))
                                .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(EmeraldLight, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "PRO SAAS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PlatinumWhite,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianDark,
                    titleContentColor = PlatinumWhite
                ),
                modifier = Modifier
                    .statusBarsPadding()
                    .testTag("executive_top_bar")
            )
        },
        bottomBar = {
            AscendBottomNavigation(
                currentScreen = currentScreen,
                onNavigate = { screen -> currentScreen = screen }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                NavScreen.Dashboard -> DashboardScreen(
                    financialSummary = financialSummary,
                    masterySummary = masterySummary,
                    habits = habits,
                    assets = assets,
                    goals = goals,
                    onToggleHabit = { habit -> viewModel.toggleHabit(habit) },
                    onOpenAddAsset = { showAddAssetDialog = true },
                    onOpenAddTransaction = { showAddTxDialog = true },
                    onOpenAddJournal = { showAddJournalDialog = true },
                    onNavigateToSimulator = { currentScreen = NavScreen.Simulator }
                )

                NavScreen.Finances -> FinancesScreen(
                    financialSummary = financialSummary,
                    assets = assets,
                    transactions = transactions,
                    onAddAsset = { showAddAssetDialog = true },
                    onDeleteAsset = { asset -> viewModel.deleteAsset(asset) },
                    onAddTransaction = { showAddTxDialog = true },
                    onDeleteTransaction = { tx -> viewModel.deleteTransaction(tx) }
                )

                NavScreen.Mastery -> MasteryScreen(
                    masterySummary = masterySummary,
                    habits = habits,
                    goals = goals,
                    onToggleHabit = { habit -> viewModel.toggleHabit(habit) },
                    onDeleteHabit = { habit -> viewModel.deleteHabit(habit) },
                    onAddHabit = { showAddHabitDialog = true },
                    onUpdateGoalProgress = { goal, delta -> viewModel.updateGoalProgress(goal, delta) },
                    onDeleteGoal = { goal -> viewModel.deleteGoal(goal) },
                    onAddGoal = { showAddGoalDialog = true }
                )

                NavScreen.Simulator -> SimulatorScreen(
                    params = simulatorParams,
                    simulationResult = viewModel.calculateSimulation(),
                    onUpdateParams = { initial, monthly, rate, years, expenses ->
                        viewModel.updateSimulator(
                            initialCapital = initial,
                            monthlySavings = monthly,
                            annualReturnPercent = rate,
                            horizonYears = years,
                            annualExpenses = expenses
                        )
                    }
                )

                NavScreen.Principles -> PrinciplesJournalScreen(
                    journalEntries = journalEntries,
                    onAddJournalEntry = { showAddJournalDialog = true },
                    onDeleteJournalEntry = { entry -> viewModel.deleteJournalEntry(entry) }
                )
            }
        }
    }

    // Modal Dialogs
    if (showAddAssetDialog) {
        AddAssetDialog(
            onDismiss = { showAddAssetDialog = false },
            onConfirm = { name, category, value, isLiability, notes ->
                viewModel.addAsset(name, category, value, isLiability, notes)
                showAddAssetDialog = false
            }
        )
    }

    if (showAddTxDialog) {
        AddTransactionDialog(
            onDismiss = { showAddTxDialog = false },
            onConfirm = { title, amount, type, category, date ->
                viewModel.addTransaction(title, amount, type, category, date)
                showAddTxDialog = false
            }
        )
    }

    if (showAddHabitDialog) {
        AddHabitDialog(
            onDismiss = { showAddHabitDialog = false },
            onConfirm = { title, category, frequency, impactWeight ->
                viewModel.addHabit(title, category, frequency, impactWeight)
                showAddHabitDialog = false
            }
        )
    }

    if (showAddGoalDialog) {
        AddGoalDialog(
            onDismiss = { showAddGoalDialog = false },
            onConfirm = { title, pillar, target, current, unit, targetDate ->
                viewModel.addGoal(title, pillar, target, current, unit, targetDate)
                showAddGoalDialog = false
            }
        )
    }

    if (showAddJournalDialog) {
        AddJournalDialog(
            onDismiss = { showAddJournalDialog = false },
            onConfirm = { dateStr, focus, finDiscipline, energy, wins, lesson ->
                viewModel.addJournalEntry(dateStr, focus, finDiscipline, energy, wins, lesson)
                showAddJournalDialog = false
            }
        )
    }
}
