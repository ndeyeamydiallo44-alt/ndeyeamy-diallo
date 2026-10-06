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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExecutiveJournalEntry
import com.example.data.model.MentalModelPrinciple
import com.example.data.model.MentalModelRepository
import com.example.ui.components.ExecutiveCard
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.PlatinumWhite
import com.example.ui.theme.PrestigeGold
import com.example.ui.theme.PrestigeGoldLight
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SurfaceCard

@Composable
fun PrinciplesJournalScreen(
    journalEntries: List<ExecutiveJournalEntry>,
    onAddJournalEntry: () -> Unit,
    onDeleteJournalEntry: (ExecutiveJournalEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Journal & Débriefing", "Modèles Mentaux & Principes")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDark)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MidnightNavy,
            contentColor = PrestigeGold,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = PrestigeGold
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
                            color = if (selectedTab == index) PrestigeGold else SlateMuted,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> JournalTab(
                journalEntries = journalEntries,
                onAddJournalEntry = onAddJournalEntry,
                onDeleteJournalEntry = onDeleteJournalEntry
            )
            1 -> PrinciplesTab()
        }
    }
}

@Composable
private fun JournalTab(
    journalEntries: List<ExecutiveJournalEntry>,
    onAddJournalEntry: () -> Unit,
    onDeleteJournalEntry: (ExecutiveJournalEntry) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Button(
                onClick = onAddJournalEntry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrestigeGold,
                    contentColor = ObsidianDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_open_add_journal_tab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Rédiger le Débriefing du Soir", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Text(
                text = "HISTORIQUE DES DÉBRIEFINGS STRATÉGIQUES (${journalEntries.size})",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        items(journalEntries) { entry ->
            JournalEntryCard(entry = entry, onDelete = { onDeleteJournalEntry(entry) })
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun JournalEntryCard(entry: ExecutiveJournalEntry, onDelete: () -> Unit) {
    ExecutiveCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(PrestigeGold.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = entry.dateStr.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = PrestigeGoldLight,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = SlateMuted.copy(alpha = 0.5f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Scores Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScoreTag("Focus : ${entry.focusScore}/5", CyberCyan)
                ScoreTag("Finance : ${entry.financialDisciplineScore}/5", EmeraldLight)
                ScoreTag("Énergie : ${entry.energyScore}/5", PrestigeGold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Victoires & Réalisations :",
                style = MaterialTheme.typography.labelSmall,
                color = EmeraldLight
            )
            Text(
                text = entry.mainWins,
                style = MaterialTheme.typography.bodyMedium,
                color = PlatinumWhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Leçon Stratégique :",
                style = MaterialTheme.typography.labelSmall,
                color = PrestigeGold
            )
            Text(
                text = entry.strategicLesson,
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted
            )
        }
    }
}

@Composable
private fun ScoreTag(text: String, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PrinciplesTab() {
    val principles = MentalModelRepository.principles

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            ExecutiveCard(borderColor = CyberCyan.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "Mindset",
                            tint = CyberCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MODÈLES MENTAUX DES LEADERS & INVESTISSEURS",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberCyan,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Les principes intemporels qui gouvernent la prise de décision, l'allocation de capital et la discipline personnelle.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SlateMuted
                    )
                }
            }
        }

        items(principles) { principle ->
            PrincipleCard(principle = principle)
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun PrincipleCard(principle: MentalModelPrinciple) {
    ExecutiveCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(PrestigeGold.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = principle.tag,
                        style = MaterialTheme.typography.labelSmall,
                        color = PrestigeGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = principle.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = principle.title,
                style = MaterialTheme.typography.titleMedium,
                color = PlatinumWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = principle.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MidnightNavy, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Action",
                        tint = EmeraldLight,
                        modifier = Modifier.size(16.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = principle.actionableGuideline,
                        style = MaterialTheme.typography.bodySmall,
                        color = PlatinumWhite
                    )
                }
            }
        }
    }
}
