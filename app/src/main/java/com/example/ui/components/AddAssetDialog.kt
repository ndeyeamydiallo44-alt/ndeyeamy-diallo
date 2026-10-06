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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CoralRed
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.SlateDark
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun AddAssetDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, category: String, value: Double, isLiability: Boolean, notes: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var valueStr by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("INVESTISSEMENT") }
    var isLiability by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    val categories = listOf("INVESTISSEMENT", "IMMOBILIER", "LIQUIDITES", "CRYPTO", "BUSINESS", "DETTE_PASSIF")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceCard,
        title = {
            Text(
                text = if (isLiability) "Ajouter un Passif / Dette" else "Ajouter un Actif Financier",
                style = MaterialTheme.typography.titleLarge,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Liability switch
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isLiability) "Type : Passif / Dette (-)" else "Type : Actif Valorisé (+)",
                        color = if (isLiability) CoralRed else EmeraldLight,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Switch(
                        checked = isLiability,
                        onCheckedChange = {
                            isLiability = it
                            if (it) category = "DETTE_PASSIF"
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CoralRed,
                            checkedTrackColor = CoralRed.copy(alpha = 0.3f),
                            uncheckedThumbColor = EmeraldLight,
                            uncheckedTrackColor = EmeraldLight.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.testTag("switch_asset_liability")
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom de l'actif / compte") },
                    placeholder = { Text("ex. PEA MSCI World, T2 Locatif...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_asset_name"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldLight,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = valueStr,
                    onValueChange = { valueStr = it },
                    label = { Text("Valeur (€)") },
                    placeholder = { Text("ex. 25000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_asset_value"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldLight,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Catégorie :",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val quickCats = if (isLiability) listOf("DETTE_PASSIF", "EMPRUNT") else listOf("INVESTISSEMENT", "IMMOBILIER", "LIQUIDITES", "CRYPTO")
                    quickCats.take(3).forEach { cat ->
                        val selected = category == cat
                        Box(
                            modifier = Modifier
                                .background(
                                    if (selected) EmeraldLight else MidnightNavy,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { category = cat }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat.take(12),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) ObsidianDark else PlatinumWhite,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Stratégie / Notes (Optionnel)") },
                    placeholder = { Text("ex. DCA 500€/mois, rendement 5%...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldLight,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = PlatinumWhite,
                        unfocusedTextColor = PlatinumWhite
                    ),
                    maxLines = 2
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
                    val amount = valueStr.toDoubleOrNull()
                    if (name.isBlank()) {
                        errorMsg = "Veuillez saisir un nom."
                    } else if (amount == null || amount < 0) {
                        errorMsg = "Veuillez saisir une valeur numérique valide."
                    } else {
                        onConfirm(name, category, amount, isLiability, notes)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLiability) CoralRed else EmeraldLight,
                    contentColor = ObsidianDark
                ),
                modifier = Modifier.testTag("btn_confirm_asset")
            ) {
                Text("Enregistrer", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = SlateMuted)
            }
        }
    )
}
