package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CoralRed
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun AddGoalDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, pillar: String, targetValue: Double, currentValue: Double, unit: String, targetDate: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var pillar by remember { mutableStateOf("FINANCE") }
    var targetStr by remember { mutableStateOf("") }
    var currentStr by remember { mutableStateOf("0") }
    var unit by remember { mutableStateOf("€") }
    var targetDate by remember { mutableStateOf("31 Déc 2026") }
    var errorMsg by remember { mutableStateOf("") }

    val pillars = listOf("FINANCE" to "Finance", "MAITRISE_PERSO" to "Maîtrise", "BUSINESS" to "Business", "SANTE" to "Santé")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceCard,
        title = {
            Text(
                text = "Définir un Objectif OKR",
                style = MaterialTheme.typography.titleLarge,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Objectif mesurable (Key Result)") },
                    placeholder = { Text("ex. Atteindre 150k€ de patrimoine, 12 livres...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_okr_title"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrestigeGold,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Pilier stratégique :",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    pillars.forEach { (pilKey, pilLabel) ->
                        val selected = pillar == pilKey
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (selected) PrestigeGold else MidnightNavy,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    pillar = pilKey
                                    if (pilKey == "FINANCE") unit = "€"
                                    if (pilKey == "MAITRISE_PERSO") unit = "livres"
                                }
                                .padding(vertical = 6.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = pilLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) ObsidianDark else PlatinumWhite,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = currentStr,
                        onValueChange = { currentStr = it },
                        label = { Text("Actuel") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_okr_current"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrestigeGold,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = PlatinumWhite,
                            unfocusedTextColor = PlatinumWhite
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = targetStr,
                        onValueChange = { targetStr = it },
                        label = { Text("Cible") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_okr_target"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrestigeGold,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = PlatinumWhite,
                            unfocusedTextColor = PlatinumWhite
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = unit,
                        onValueChange = { unit = it },
                        label = { Text("Unité") },
                        modifier = Modifier.weight(0.7f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrestigeGold,
                            unfocusedBorderColor = BorderSubtle,
                            focusedTextColor = PlatinumWhite,
                            unfocusedTextColor = PlatinumWhite
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = targetDate,
                    onValueChange = { targetDate = it },
                    label = { Text("Échéance trimestrielle") },
                    placeholder = { Text("ex. 31 Décembre 2026") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrestigeGold,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    singleLine = true
                )

                if (errorMsg.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = errorMsg,
                        color = CoralRed,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val target = targetStr.toDoubleOrNull()
                    val current = currentStr.toDoubleOrNull() ?: 0.0
                    if (title.isBlank()) {
                        errorMsg = "Veuillez renseigner un intitulé."
                    } else if (target == null || target <= 0) {
                        errorMsg = "Veuillez spécifier une cible numérique positive."
                    } else {
                        onConfirm(title, pillar, target, current, unit, targetDate)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrestigeGold,
                    contentColor = ObsidianDark
                ),
                modifier = Modifier.testTag("btn_confirm_okr")
            ) {
                Text("Activer OKR", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = SlateMuted)
            }
        }
    )
}
