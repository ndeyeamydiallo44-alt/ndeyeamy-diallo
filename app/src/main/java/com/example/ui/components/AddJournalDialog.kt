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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun AddJournalDialog(
    onDismiss: () -> Unit,
    onConfirm: (dateStr: String, focus: Int, financialDiscipline: Int, energy: Int, wins: String, lesson: String) -> Unit
) {
    var dateStr by remember { mutableStateOf("Aujourd'hui") }
    var focusScore by remember { mutableStateOf(5) }
    var finScore by remember { mutableStateOf(5) }
    var energyScore by remember { mutableStateOf(4) }
    var wins by remember { mutableStateOf("") }
    var lesson by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceCard,
        title = {
            Text(
                text = "Débriefing Stratégique Exécutif",
                style = MaterialTheme.typography.titleLarge,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                RatingRow(label = "Focus & Clarté Mentale", score = focusScore, onScoreSelect = { focusScore = it }, activeColor = CyberCyan)
                Spacer(modifier = Modifier.height(8.dp))
                RatingRow(label = "Discipline Financière", score = finScore, onScoreSelect = { finScore = it }, activeColor = EmeraldLight)
                Spacer(modifier = Modifier.height(8.dp))
                RatingRow(label = "Énergie & Vitalité", score = energyScore, onScoreSelect = { energyScore = it }, activeColor = PrestigeGold)

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = wins,
                    onValueChange = { wins = it },
                    label = { Text("3 Victoires & Réalisations Clés") },
                    placeholder = { Text("ex. Virement DCA effectué, contrat signé, 90min Deep Work...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_journal_wins"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldLight,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = lesson,
                    onValueChange = { lesson = it },
                    label = { Text("Principe Stratégique / Leçon Apprise") },
                    placeholder = { Text("ex. Principe Dalio : regarder la réalité sans complaisance...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_journal_lesson"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrestigeGold,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        dateStr,
                        focusScore,
                        finScore,
                        energyScore,
                        if (wins.isBlank()) "Objectifs et routines accomplis avec discipline." else wins,
                        if (lesson.isBlank()) "Progression continue et maîtrise des impulsions." else lesson
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldLight,
                    contentColor = ObsidianDark
                ),
                modifier = Modifier.testTag("btn_confirm_journal")
            ) {
                Text("Enregistrer Bilan", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer", color = SlateMuted)
            }
        }
    )
}

@Composable
private fun RatingRow(
    label: String,
    score: Int,
    onScoreSelect: (Int) -> Unit,
    activeColor: androidx.compose.ui.graphics.Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = SlateMuted)
            Text(text = "$score/5", style = MaterialTheme.typography.labelSmall, color = activeColor, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { num ->
                val isSelected = num <= score
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            if (isSelected) activeColor else MidnightNavy,
                            CircleShape
                        )
                        .clickable { onScoreSelect(num) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$num",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) ObsidianDark else SlateMuted,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
