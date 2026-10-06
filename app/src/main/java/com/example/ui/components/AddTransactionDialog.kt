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
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, amount: Double, type: String, category: String, date: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amountStr by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("EXPENSE") } // "INCOME" or "EXPENSE"
    var category by remember { mutableStateOf("BESOINS_50") }
    var date by remember { mutableStateOf("Aujourd'hui") }
    var errorMsg by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceCard,
        title = {
            Text(
                text = if (type == "INCOME") "Enregistrer un Revenu" else "Enregistrer une Dépense",
                style = MaterialTheme.typography.titleLarge,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Type selector: Revenu vs Dépense
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val isExp = type == "EXPENSE"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isExp) CoralRed else MidnightNavy,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                type = "EXPENSE"
                                category = "BESOINS_50"
                            }
                            .padding(vertical = 10.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "DÉPENSE (-)",
                            color = if (isExp) PlatinumWhite else SlateMuted,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    val isInc = type == "INCOME"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isInc) EmeraldLight else MidnightNavy,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                type = "INCOME"
                                category = "SALAIRE_PRO"
                            }
                            .padding(vertical = 10.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "REVENU (+)",
                            color = if (isInc) ObsidianDark else SlateMuted,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Libellé du flux") },
                    placeholder = { Text(if (type == "INCOME") "ex. Contrat SaaS, Dividendes" else "ex. Loyer, Alimentation bio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_tx_title"),
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
                    value = amountStr,
                    onValueChange = { amountStr = it },
                    label = { Text("Montant (€)") },
                    placeholder = { Text("ex. 450") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_tx_amount"),
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
                    text = if (type == "EXPENSE") "Structure Budgétaire (Règle 50/30/20) :" else "Source de revenu :",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )

                Spacer(modifier = Modifier.height(4.dp))

                val availableCats = if (type == "EXPENSE") {
                    listOf("BESOINS_50" to "Besoins 50%", "ENVIE_30" to "Plaisir 30%", "EPARGNE_20" to "Épargne 20%")
                } else {
                    listOf("SALAIRE_PRO" to "Salaire", "BUSINESS_SAAS" to "SaaS/Biz", "DIVIDENDES" to "Dividendes")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    availableCats.forEach { (catKey, catLabel) ->
                        val selected = category == catKey
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (selected) (if (type == "INCOME") EmeraldLight else CoralRed) else MidnightNavy,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { category = catKey }
                                .padding(vertical = 8.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = catLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) (if (type == "INCOME") ObsidianDark else PlatinumWhite) else SlateMuted,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
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
                    val amount = amountStr.toDoubleOrNull()
                    if (title.isBlank()) {
                        errorMsg = "Veuillez saisir un libellé."
                    } else if (amount == null || amount <= 0) {
                        errorMsg = "Veuillez saisir un montant valide."
                    } else {
                        onConfirm(title, amount, type, category, date)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (type == "INCOME") EmeraldLight else CoralRed,
                    contentColor = if (type == "INCOME") ObsidianDark else PlatinumWhite
                ),
                modifier = Modifier.testTag("btn_confirm_tx")
            ) {
                Text("Ajouter Flux", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler", color = SlateMuted)
            }
        }
    )
}
