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
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, category: String, targetFrequency: String, impactWeight: Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("MINDSET") }
    var frequency by remember { mutableStateOf("Quotidien") }
    var impactWeight by remember { mutableStateOf(10) }
    var errorMsg by remember { mutableStateOf("") }

    val categories = listOf("MINDSET" to "Clarté Mentale", "DEEP_WORK" to "Focus Pro", "SANTE" to "Énergie & Corps", "FINANCES" to "Discipline Budget")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceCard,
        title = {
            Text(
                text = "Nouvelle Routine d'Excellence",
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
                    label = { Text("Titre de la routine / habitude") },
                    placeholder = { Text("ex. Méditation 15min, Deep Work 90min...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_habit_title"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberCyan,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Pilier de développement personnel :",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.forEach { (catKey, catLabel) ->
                        val selected = category == catKey
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (selected) CyberCyan.copy(alpha = 0.2f) else MidnightNavy,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { category = catKey }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = catLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selected) CyberCyan else PlatinumWhite,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Poids dans l'indice d'excellence : $impactWeight pts",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(10, 15, 20).forEach { weight ->
                        val selected = impactWeight == weight
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (selected) CyberCyan else MidnightNavy,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { impactWeight = weight }
                                .padding(vertical = 6.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = "$weight pts",
                                color = if (selected) ObsidianDark else PlatinumWhite,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

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
                    if (title.isBlank()) {
                        errorMsg = "Veuillez renseigner un titre."
                    } else {
                        onConfirm(title, category, frequency, impactWeight)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberCyan,
                    contentColor = ObsidianDark
                ),
                modifier = Modifier.testTag("btn_confirm_habit")
            ) {
                Text("Créer Routine", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = SlateMuted)
            }
        }
    )
}
