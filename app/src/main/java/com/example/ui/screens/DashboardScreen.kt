package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.FinancialAsset
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
import com.example.ui.components.ExecutiveCard
import com.example.ui.components.MetricKpiCard
import com.example.ui.components.formatCurrency
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChartAmber
import com.example.ui.theme.ChartBlue
import com.example.ui.theme.ChartCyan
import com.example.ui.theme.ChartEmerald
import com.example.ui.theme.ChartPurple
import com.example.ui.theme.ChartRose
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.PrestigeGoldLight
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceElevated
import com.example.viewmodel.FinancialSummary
import com.example.viewmodel.MasterySummary

@Composable
fun DashboardScreen(
    financialSummary: FinancialSummary,
    masterySummary: MasterySummary,
    habits: List<PersonalHabit>,
    assets: List<FinancialAsset>,
    goals: List<StrategicGoalOKR>,
    onToggleHabit: (PersonalHabit) -> Unit,
    onOpenAddAsset: () -> Unit,
    onOpenAddTransaction: () -> Unit,
    onOpenAddJournal: () -> Unit,
    onNavigateToSimulator: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero SaaS Executive Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            ExecutiveHeroBanner(
                financialSummary = financialSummary,
                masterySummary = masterySummary
            )
        }

        // Quick Executive Action Bar
        item {
            ExecutiveActionPills(
                onOpenAddAsset = onOpenAddAsset,
                onOpenAddTransaction = onOpenAddTransaction,
                onOpenAddJournal = onOpenAddJournal,
                onNavigateToSimulator = onNavigateToSimulator
            )
        }

        // 4 Core Executive KPI Cards Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricKpiCard(
                        title = "Valeur Nette",
                        value = formatCurrency(financialSummary.netWorth),
                        subtitle = "Patrimoine Net",
                        icon = Icons.Default.TrendingUp,
                        iconTint = EmeraldLight,
                        trendText = "+4.2% ce mois",
                        trendPositive = true,
                        modifier = Modifier.weight(1f)
                    )

                    MetricKpiCard(
                        title = "Score Maîtrise",
                        value = "${masterySummary.masteryIndexScore} / 100",
                        subtitle = "Indice d'Élite",
                        icon = Icons.Default.Speed,
                        iconTint = CyberCyan,
                        trendText = if (masterySummary.masteryIndexScore >= 75) "Top 5% Tier" else "En Progression",
                        trendPositive = masterySummary.masteryIndexScore >= 50,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricKpiCard(
                        title = "Taux d'Épargne",
                        value = "${financialSummary.savingsRate.toInt()}%",
                        subtitle = "Objectif > 20%",
                        icon = Icons.Default.Savings,
                        iconTint = PrestigeGold,
                        trendText = if (financialSummary.savingsRate >= 20.0) "Optimal" else "À Optimiser",
                        trendPositive = financialSummary.savingsRate >= 20.0,
                        modifier = Modifier.weight(1f)
                    )

                    MetricKpiCard(
                        title = "Runway Sécurité",
                        value = String.format("%.1f mois", financialSummary.runwayMonths),
                        subtitle = "Trésorerie / Mois",
                        icon = Icons.Default.Security,
                        iconTint = if (financialSummary.runwayMonths >= 6) EmeraldLight else CoralRed,
                        trendText = if (financialSummary.runwayMonths >= 6) "Anti-Fragile" else "Vigilance",
                        trendPositive = financialSummary.runwayMonths >= 6,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Asset Allocation Visual Bar
        item {
            AssetAllocationCard(assets = assets)
        }

        // Daily Routines Checklist (Personal Mastery in action)
        item {
            Text(
                text = "ROUTINES D'EXCELLENCE DU JOUR",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        items(habits.take(4)) { habit ->
            HabitDailyItem(habit = habit, onToggle = { onToggleHabit(habit) })
        }

        // OKR Strategic Goal Preview
        item {
            val topGoal = goals.firstOrNull { !it.isCompleted }
            if (topGoal != null) {
                OkrHighlightCard(goal = topGoal)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ExecutiveHeroBanner(
    financialSummary: FinancialSummary,
    masterySummary: MasterySummary
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
    ) {
        // Background Hero Image with Dark Gradient Overlay
        Image(
            painter = painterResource(id = R.drawable.img_hero_saas),
            contentDescription = "SaaS Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ObsidianDark.copy(alpha = 0.75f),
                            ObsidianDark.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(PrestigeGold.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = "Tier",
                            tint = PrestigeGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ASCEND PRO ÉLITE",
                        style = MaterialTheme.typography.labelLarge,
                        color = PrestigeGoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .background(EmeraldLight.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "SYSTÈME ACTIF",
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldLight,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Tableau de Bord Exécutif",
                style = MaterialTheme.typography.headlineMedium,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Optimisation continue du capital financier et du potentiel humain.",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text(
                        text = "Flux Mensuel Net",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                    Text(
                        text = formatCurrency(financialSummary.netSavings),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (financialSummary.netSavings >= 0) EmeraldLight else CoralRed,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(32.dp)
                        .background(BorderSubtle)
                )

                Column {
                    Text(
                        text = "Habitudes Validées",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                    Text(
                        text = "${masterySummary.completedHabitsToday} / ${masterySummary.totalHabits} aujourd'hui",
                        style = MaterialTheme.typography.titleMedium,
                        color = CyberCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ExecutiveActionPills(
    onOpenAddAsset: () -> Unit,
    onOpenAddTransaction: () -> Unit,
    onOpenAddJournal: () -> Unit,
    onNavigateToSimulator: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionChip(
            label = "+ Actif",
            icon = Icons.Default.Add,
            accentColor = EmeraldLight,
            onClick = onOpenAddAsset,
            modifier = Modifier.weight(1f).testTag("action_add_asset")
        )

        ActionChip(
            label = "+ Flux",
            icon = Icons.Default.Add,
            accentColor = CoralRed,
            onClick = onOpenAddTransaction,
            modifier = Modifier.weight(1f).testTag("action_add_tx")
        )

        ActionChip(
            label = "+ Débrief",
            icon = Icons.Default.Star,
            accentColor = PrestigeGold,
            onClick = onOpenAddJournal,
            modifier = Modifier.weight(1f).testTag("action_add_journal")
        )

        ActionChip(
            label = "FIRE Calc",
            icon = Icons.Default.AutoGraph,
            accentColor = CyberCyan,
            onClick = onNavigateToSimulator,
            modifier = Modifier.weight(1.1f).testTag("action_simulator")
        )
    }
}

@Composable
private fun ActionChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(SurfaceCard, RoundedCornerShape(12.dp))
            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = accentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = PlatinumWhite,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AssetAllocationCard(assets: List<FinancialAsset>) {
    val nonLiabilities = assets.filter { !it.isLiability }
    val totalVal = nonLiabilities.sumOf { it.value }.coerceAtLeast(1.0)

    val investVal = nonLiabilities.filter { it.category == "INVESTISSEMENT" }.sumOf { it.value }
    val immoVal = nonLiabilities.filter { it.category == "IMMOBILIER" }.sumOf { it.value }
    val cashVal = nonLiabilities.filter { it.category == "LIQUIDITES" }.sumOf { it.value }
    val bizVal = nonLiabilities.filter { it.category == "BUSINESS" }.sumOf { it.value }
    val cryptoVal = nonLiabilities.filter { it.category == "CRYPTO" }.sumOf { it.value }

    ExecutiveCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RÉPARTITION D'ALLOCATION D'ACTIFS",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${nonLiabilities.size} Actifs",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldLight
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Multi-segment horizontal bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                if (investVal > 0) {
                    Box(modifier = Modifier.weight((investVal / totalVal).toFloat()).fillMaxSize().background(ChartBlue))
                }
                if (immoVal > 0) {
                    Box(modifier = Modifier.weight((immoVal / totalVal).toFloat()).fillMaxSize().background(ChartEmerald))
                }
                if (cashVal > 0) {
                    Box(modifier = Modifier.weight((cashVal / totalVal).toFloat()).fillMaxSize().background(ChartAmber))
                }
                if (bizVal > 0) {
                    Box(modifier = Modifier.weight((bizVal / totalVal).toFloat()).fillMaxSize().background(ChartPurple))
                }
                if (cryptoVal > 0) {
                    Box(modifier = Modifier.weight((cryptoVal / totalVal).toFloat()).fillMaxSize().background(ChartCyan))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AllocationLegendItem("ETF/Actions", ((investVal / totalVal) * 100).toInt(), ChartBlue)
                AllocationLegendItem("Immobilier", ((immoVal / totalVal) * 100).toInt(), ChartEmerald)
                AllocationLegendItem("Cash", ((cashVal / totalVal) * 100).toInt(), ChartAmber)
                AllocationLegendItem("Business", ((bizVal / totalVal) * 100).toInt(), ChartPurple)
                AllocationLegendItem("Crypto", ((cryptoVal / totalVal) * 100).toInt(), ChartCyan)
            }
        }
    }
}

@Composable
private fun AllocationLegendItem(label: String, percent: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = SlateMuted, fontSize = 10.sp)
            Text(text = "$percent%", style = MaterialTheme.typography.labelSmall, color = PlatinumWhite, fontWeight = FontWeight.Bold, fontSize = 10.sp)
        }
    }
}

@Composable
private fun HabitDailyItem(habit: PersonalHabit, onToggle: () -> Unit) {
    ExecutiveCard(
        modifier = Modifier.clickable { onToggle() },
        backgroundColor = if (habit.isCompletedToday) SurfaceElevated else SurfaceCard,
        borderColor = if (habit.isCompletedToday) CyberCyan.copy(alpha = 0.4f) else BorderSubtle
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (habit.isCompletedToday) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Toggle",
                    tint = if (habit.isCompletedToday) CyberCyan else SlateMuted,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (habit.isCompletedToday) PlatinumWhite else SlateMuted,
                        fontWeight = if (habit.isCompletedToday) FontWeight.SemiBold else FontWeight.Normal
                    )
                    Text(
                        text = "${habit.category} • Série : ${habit.currentStreak} jours consécutifs",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (habit.currentStreak > 7) PrestigeGold else SlateMuted
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(MidnightNavy, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "+${habit.impactWeight} pts",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyberCyan,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun OkrHighlightCard(goal: StrategicGoalOKR) {
    ExecutiveCard(
        borderColor = PrestigeGold.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PALIER STRATÉGIQUE EN COURS",
                    style = MaterialTheme.typography.labelSmall,
                    color = PrestigeGold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = goal.targetDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = goal.title,
                style = MaterialTheme.typography.titleMedium,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { goal.progressRatio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrestigeGold,
                trackColor = MidnightNavy
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${goal.currentValue.toInt()} / ${goal.targetValue.toInt()} ${goal.unit}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PlatinumWhite,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${(goal.progressRatio * 100).toInt()}% accompli",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrestigeGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
