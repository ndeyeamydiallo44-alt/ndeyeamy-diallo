package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CashflowTransaction
import com.example.data.model.FinancialAsset
import com.example.ui.components.ExecutiveCard
import com.example.ui.components.formatCurrency
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChartAmber
import com.example.ui.theme.ChartBlue
import com.example.ui.theme.ChartCyan
import com.example.ui.theme.ChartEmerald
import com.example.ui.theme.ChartPurple
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceElevated
import com.example.viewmodel.FinancialSummary

@Composable
fun FinancesScreen(
    financialSummary: FinancialSummary,
    assets: List<FinancialAsset>,
    transactions: List<CashflowTransaction>,
    onAddAsset: () -> Unit,
    onDeleteAsset: (FinancialAsset) -> Unit,
    onAddTransaction: () -> Unit,
    onDeleteTransaction: (CashflowTransaction) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Patrimoine & Actifs", "Trésorerie & Flux", "Règle 50/30/20")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDark)
    ) {
        // Tab Selector
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MidnightNavy,
            contentColor = EmeraldLight,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = EmeraldLight
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selectedTab == index) EmeraldLight else SlateMuted,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> AssetsTabContent(
                financialSummary = financialSummary,
                assets = assets,
                onAddAsset = onAddAsset,
                onDeleteAsset = onDeleteAsset
            )
            1 -> TransactionsTabContent(
                financialSummary = financialSummary,
                transactions = transactions,
                onAddTransaction = onAddTransaction,
                onDeleteTransaction = onDeleteTransaction
            )
            2 -> Budget503020TabContent(
                financialSummary = financialSummary,
                transactions = transactions
            )
        }
    }
}

@Composable
private fun AssetsTabContent(
    financialSummary: FinancialSummary,
    assets: List<FinancialAsset>,
    onAddAsset: () -> Unit,
    onDeleteAsset: (FinancialAsset) -> Unit
) {
    val activeAssets = assets.filter { !it.isLiability }
    val liabilities = assets.filter { it.isLiability }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Net Worth Balance Card
        item {
            ExecutiveCard(borderColor = EmeraldLight.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "BILAN PATRIMONIAL GLOBAL",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = formatCurrency(financialSummary.netWorth),
                        style = MaterialTheme.typography.headlineMedium,
                        color = PlatinumWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Actifs Bruts", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                            Text(
                                text = formatCurrency(financialSummary.totalGrossAssets),
                                style = MaterialTheme.typography.titleMedium,
                                color = EmeraldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Dettes & Passifs", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                            Text(
                                text = formatCurrency(financialSummary.totalLiabilities),
                                style = MaterialTheme.typography.titleMedium,
                                color = CoralRed,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Add Asset Button
        item {
            Button(
                onClick = onAddAsset,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldLight,
                    contentColor = ObsidianDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_open_add_asset")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ajouter un Actif ou Passif", fontWeight = FontWeight.Bold)
            }
        }

        // Section Actifs
        item {
            Text(
                text = "ACTIFS PRODUCTIFS & VALORISÉS (${activeAssets.size})",
                style = MaterialTheme.typography.labelSmall,
                color = EmeraldLight,
                letterSpacing = 1.sp
            )
        }

        items(activeAssets) { asset ->
            AssetItemCard(asset = asset, onDelete = { onDeleteAsset(asset) })
        }

        // Section Passifs
        if (liabilities.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "PASSIFS & DETTES (${liabilities.size})",
                    style = MaterialTheme.typography.labelSmall,
                    color = CoralRed,
                    letterSpacing = 1.sp
                )
            }

            items(liabilities) { liability ->
                AssetItemCard(asset = liability, onDelete = { onDeleteAsset(liability) })
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun AssetItemCard(asset: FinancialAsset, onDelete: () -> Unit) {
    val catColor = when (asset.category) {
        "INVESTISSEMENT" -> ChartBlue
        "IMMOBILIER" -> ChartEmerald
        "LIQUIDITES" -> ChartAmber
        "BUSINESS" -> ChartPurple
        "CRYPTO" -> ChartCyan
        else -> CoralRed
    }

    ExecutiveCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(catColor, CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = asset.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = PlatinumWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${asset.category} ${if (asset.notes.isNotBlank()) "• ${asset.notes}" else ""}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateMuted
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${if (asset.isLiability) "-" else ""}${formatCurrency(asset.value)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (asset.isLiability) CoralRed else EmeraldLight,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = SlateMuted.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionsTabContent(
    financialSummary: FinancialSummary,
    transactions: List<CashflowTransaction>,
    onAddTransaction: () -> Unit,
    onDeleteTransaction: (CashflowTransaction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary Card
        item {
            ExecutiveCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Entrées (Revenus)", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                        Text(
                            text = "+${formatCurrency(financialSummary.totalIncome)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = EmeraldLight,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Sorties (Dépenses)", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                        Text(
                            text = "-${formatCurrency(financialSummary.totalExpenses)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = CoralRed,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Cashflow Net", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                        Text(
                            text = formatCurrency(financialSummary.netSavings),
                            style = MaterialTheme.typography.titleMedium,
                            color = if (financialSummary.netSavings >= 0) CyberCyan else CoralRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Add Transaction Button
        item {
            Button(
                onClick = onAddTransaction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberCyan,
                    contentColor = ObsidianDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_open_add_tx")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Enregistrer un Flux de Trésorerie", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Text(
                text = "JOURNAL DE TRÉSORERIE RÉCENT (${transactions.size})",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        items(transactions) { tx ->
            val isIncome = tx.type == "INCOME"
            ExecutiveCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    (if (isIncome) EmeraldLight else CoralRed).copy(alpha = 0.15f),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isIncome) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                contentDescription = tx.type,
                                tint = if (isIncome) EmeraldLight else CoralRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = tx.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = PlatinumWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${tx.category} • ${tx.date}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateMuted
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${if (isIncome) "+" else "-"}${formatCurrency(tx.amount)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isIncome) EmeraldLight else CoralRed,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = { onDeleteTransaction(tx) }) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Delete",
                                tint = SlateMuted.copy(alpha = 0.6f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun Budget503020TabContent(
    financialSummary: FinancialSummary,
    transactions: List<CashflowTransaction>
) {
    val totalIncome = financialSummary.totalIncome.coerceAtLeast(1.0)
    val expenses = transactions.filter { it.type == "EXPENSE" }

    val needsExpense = expenses.filter { it.category == "BESOINS_50" }.sumOf { it.amount }
    val wantsExpense = expenses.filter { it.category == "ENVIE_30" }.sumOf { it.amount }
    val savingsInvest = expenses.filter { it.category == "EPARGNE_20" }.sumOf { it.amount } + maxOf(0.0, financialSummary.netSavings)

    val needsRatio = (needsExpense / totalIncome).toFloat()
    val wantsRatio = (wantsExpense / totalIncome).toFloat()
    val savingsRatio = (savingsInvest / totalIncome).toFloat()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ExecutiveCard(borderColor = CyberCyan.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "RÈGLE D'ALLOCATION ANTI-FRAGILE",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Équilibre Budgétaire 50 / 30 / 20",
                        style = MaterialTheme.typography.titleLarge,
                        color = PlatinumWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Basé sur vos revenus mensuels de ${formatCurrency(totalIncome)}.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateMuted
                    )
                }
            }
        }

        item {
            BudgetPillarCard(
                title = "50% Besoins Vitaux & Fixes",
                currentAmount = needsExpense,
                targetAmount = totalIncome * 0.50,
                ratio = needsRatio,
                targetPercent = 50,
                accentColor = ChartBlue,
                description = "Loyer, nourriture vitale, abonnements indispensables et assurances."
            )
        }

        item {
            BudgetPillarCard(
                title = "30% Plaisir, Confort & Lifestyle",
                currentAmount = wantsExpense,
                targetAmount = totalIncome * 0.30,
                ratio = wantsRatio,
                targetPercent = 30,
                accentColor = PrestigeGold,
                description = "Sorties, loisirs, matériel haut de gamme, expériences de vie."
            )
        }

        item {
            BudgetPillarCard(
                title = "20% Minimum - Épargne & Investissement",
                currentAmount = savingsInvest,
                targetAmount = totalIncome * 0.20,
                ratio = savingsRatio,
                targetPercent = 20,
                accentColor = EmeraldLight,
                description = "DCA Actions ETF, immobilier, réserve de crise, rachat de dettes."
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun BudgetPillarCard(
    title: String,
    currentAmount: Double,
    targetAmount: Double,
    ratio: Float,
    targetPercent: Int,
    accentColor: Color,
    description: String
) {
    ExecutiveCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = PlatinumWhite,
                    fontWeight = FontWeight.Bold
                )

                val currentPercent = (ratio * 100).toInt()
                val isCompliant = if (targetPercent == 20) currentPercent >= 20 else currentPercent <= targetPercent

                Box(
                    modifier = Modifier
                        .background(
                            (if (isCompliant) EmeraldLight else CoralRed).copy(alpha = 0.15f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$currentPercent% (Cible $targetPercent%)",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isCompliant) EmeraldLight else CoralRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { ratio.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = accentColor,
                trackColor = MidnightNavy
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Consommé : ${formatCurrency(currentAmount)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PlatinumWhite,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Budget Cible : ${formatCurrency(targetAmount)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = SlateMuted
            )
        }
    }
}
