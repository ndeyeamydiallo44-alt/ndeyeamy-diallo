package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ExecutiveCard
import com.example.ui.components.formatCurrency
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChartAmber
import com.example.ui.theme.ChartBlue
import com.example.ui.theme.ChartEmerald
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.PrestigeGoldLight
import com.example.ui.theme.SlateMuted
import com.example.viewmodel.SimulationResult
import com.example.viewmodel.SimulatorParams

@Composable
fun SimulatorScreen(
    params: SimulatorParams,
    simulationResult: SimulationResult,
    onUpdateParams: (initial: Double, monthly: Double, returnRate: Double, years: Int, expenses: Double) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Executive Header Card
        item {
            ExecutiveCard(borderColor = EmeraldLight.copy(alpha = 0.4f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SIMULATEUR DE RICHESSE EXPONENTIELLE",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldLight,
                            letterSpacing = 1.sp
                        )

                        Box(
                            modifier = Modifier
                                .background(PrestigeGold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "MODÈLE 4% FIRE",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrestigeGoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Patrimoine Projeté à ${params.horizonYears} Ans",
                        style = MaterialTheme.typography.labelLarge,
                        color = SlateMuted
                    )

                    Text(
                        text = formatCurrency(simulationResult.finalCapital),
                        style = MaterialTheme.typography.displayLarge,
                        color = EmeraldLight,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Invested vs Interest Breakdown Bar
                    val totalCap = simulationResult.finalCapital.coerceAtLeast(1.0)
                    val investedRatio = (simulationResult.totalInvested / totalCap).toFloat().coerceIn(0f, 1f)
                    val gainsRatio = (simulationResult.totalGains / totalCap).toFloat().coerceIn(0f, 1f)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(investedRatio)
                                .fillMaxSize()
                                .background(ChartBlue)
                        )
                        Box(
                            modifier = Modifier
                                .weight(gainsRatio)
                                .fillMaxSize()
                                .background(EmeraldLight)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).background(ChartBlue, CircleShape))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Capital Épargné : ${formatCurrency(simulationResult.totalInvested)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateMuted
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).background(EmeraldLight, CircleShape))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Plus-values Intérêts : +${formatCurrency(simulationResult.totalGains)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = EmeraldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // FIRE Threshold Milestone Card
        item {
            ExecutiveCard(borderColor = PrestigeGold.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "INDÉPENDANCE FINANCIÈRE (FIRE)",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrestigeGold,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "Règle de Trinity (25x)",
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "Palier de Rente Perpétuelle",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateMuted
                            )
                            Text(
                                text = formatCurrency(simulationResult.fireTarget),
                                style = MaterialTheme.typography.titleLarge,
                                color = PlatinumWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Délai Estimé",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SlateMuted
                            )
                            Text(
                                text = if (simulationResult.yearsToFire <= 0) "Atteint !" else String.format("%.1f ans", simulationResult.yearsToFire),
                                style = MaterialTheme.typography.titleLarge,
                                color = if (simulationResult.yearsToFire <= params.horizonYears) EmeraldLight else PrestigeGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val fireProgress = (simulationResult.finalCapital / simulationResult.fireTarget).toFloat().coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { fireProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PrestigeGold,
                        trackColor = MidnightNavy
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Permet de générer ${formatCurrency(params.annualExpenses)}/an de rente passive (4% net) sans toucher au capital.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }
            }
        }

        // Interactive Sliders Section
        item {
            Text(
                text = "PARAMÈTRES D'INGÉNIERIE FINANCIÈRE",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        // Slider 1: Capital Initial
        item {
            SliderControlCard(
                title = "Capital de Départ Investi",
                formattedValue = formatCurrency(params.initialCapital),
                value = params.initialCapital.toFloat(),
                range = 0f..200000f,
                step = 5000f,
                onValueChange = { newVal ->
                    onUpdateParams(newVal.toDouble(), params.monthlySavings, params.annualReturnPercent, params.horizonYears, params.annualExpenses)
                },
                accentColor = ChartBlue
            )
        }

        // Slider 2: Épargne Mensuelle
        item {
            SliderControlCard(
                title = "Épargne Mensuelle Réinvestie (DCA)",
                formattedValue = "${formatCurrency(params.monthlySavings)} / mois",
                value = params.monthlySavings.toFloat(),
                range = 100f..5000f,
                step = 50f,
                onValueChange = { newVal ->
                    onUpdateParams(params.initialCapital, newVal.toDouble(), params.annualReturnPercent, params.horizonYears, params.annualExpenses)
                },
                accentColor = EmeraldLight
            )
        }

        // Slider 3: Rendement Annuel Estimé
        item {
            SliderControlCard(
                title = "Rendement Annuel Composé Estimé",
                formattedValue = "${String.format("%.1f", params.annualReturnPercent)}% par an",
                value = params.annualReturnPercent.toFloat(),
                range = 3f..15f,
                step = 0.5f,
                onValueChange = { newVal ->
                    onUpdateParams(params.initialCapital, params.monthlySavings, newVal.toDouble(), params.horizonYears, params.annualExpenses)
                },
                accentColor = PrestigeGold
            )
        }

        // Slider 4: Horizon Temporel
        item {
            SliderControlCard(
                title = "Horizon d'Investissement",
                formattedValue = "${params.horizonYears} Années",
                value = params.horizonYears.toFloat(),
                range = 1f..35f,
                step = 1f,
                onValueChange = { newVal ->
                    onUpdateParams(params.initialCapital, params.monthlySavings, params.annualReturnPercent, newVal.toInt(), params.annualExpenses)
                },
                accentColor = CyberCyan
            )
        }

        // Slider 5: Dépenses Annuelles Cibles
        item {
            SliderControlCard(
                title = "Dépenses Annuelles en Liberté (Cible FIRE)",
                formattedValue = "${formatCurrency(params.annualExpenses)} / an",
                value = params.annualExpenses.toFloat(),
                range = 15000f..100000f,
                step = 2500f,
                onValueChange = { newVal ->
                    onUpdateParams(params.initialCapital, params.monthlySavings, params.annualReturnPercent, params.horizonYears, newVal.toDouble())
                },
                accentColor = CoralRed
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun SliderControlCard(
    title: String,
    formattedValue: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    onValueChange: (Float) -> Unit,
    accentColor: Color
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateMuted
                )

                Text(
                    text = formattedValue,
                    style = MaterialTheme.typography.titleMedium,
                    color = PlatinumWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = range,
                colors = SliderDefaults.colors(
                    thumbColor = accentColor,
                    activeTrackColor = accentColor,
                    inactiveTrackColor = MidnightNavy
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
