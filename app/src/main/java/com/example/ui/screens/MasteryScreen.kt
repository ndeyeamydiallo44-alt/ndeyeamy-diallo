package com.example.ui.screens

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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
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
import com.example.data.model.PersonalHabit
import com.example.data.model.StrategicGoalOKR
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
import com.example.ui.theme.SurfaceElevated
import com.example.viewmodel.MasterySummary

@Composable
fun MasteryScreen(
    masterySummary: MasterySummary,
    habits: List<PersonalHabit>,
    goals: List<StrategicGoalOKR>,
    onToggleHabit: (PersonalHabit) -> Unit,
    onDeleteHabit: (PersonalHabit) -> Unit,
    onAddHabit: () -> Unit,
    onUpdateGoalProgress: (StrategicGoalOKR, Double) -> Unit,
    onDeleteGoal: (StrategicGoalOKR) -> Unit,
    onAddGoal: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Routines & Habitudes", "Objectifs OKR (Trimestre)")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDark)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MidnightNavy,
            contentColor = CyberCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = CyberCyan
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
                            color = if (selectedTab == index) CyberCyan else SlateMuted,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> HabitsTab(
                masterySummary = masterySummary,
                habits = habits,
                onToggleHabit = onToggleHabit,
                onDeleteHabit = onDeleteHabit,
                onAddHabit = onAddHabit
            )
            1 -> GoalsTab(
                goals = goals,
                onUpdateGoalProgress = onUpdateGoalProgress,
                onDeleteGoal = onDeleteGoal,
                onAddGoal = onAddGoal
            )
        }
    }
}

@Composable
private fun HabitsTab(
    masterySummary: MasterySummary,
    habits: List<PersonalHabit>,
    onToggleHabit: (PersonalHabit) -> Unit,
    onDeleteHabit: (PersonalHabit) -> Unit,
    onAddHabit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mastery Score Header Card
        item {
            ExecutiveCard(borderColor = CyberCyan.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "INDICE DE DISCIPLINE & MAÎTRISE",
                            style = MaterialTheme.typography.labelSmall,
                            color = SlateMuted,
                            letterSpacing = 1.sp
                        )

                        Box(
                            modifier = Modifier
                                .background(CyberCyan.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "NIVEAU ÉLITE",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${masterySummary.masteryIndexScore} / 100",
                            style = MaterialTheme.typography.headlineMedium,
                            color = PlatinumWhite,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${masterySummary.completedHabitsToday} / ${masterySummary.totalHabits} validées aujourd'hui (${masterySummary.habitCompletionPercent}%)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CyberCyan
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { (masterySummary.masteryIndexScore / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = CyberCyan,
                        trackColor = MidnightNavy
                    )
                }
            }
        }

        // Add Habit Button
        item {
            Button(
                onClick = onAddHabit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberCyan,
                    contentColor = ObsidianDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_open_add_habit")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Créer une Nouvelle Routine d'Excellence", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Text(
                text = "RITUELS QUOTIDIENS (${habits.size})",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        items(habits) { habit ->
            HabitFullCard(
                habit = habit,
                onToggle = { onToggleHabit(habit) },
                onDelete = { onDeleteHabit(habit) }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun HabitFullCard(
    habit: PersonalHabit,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    ExecutiveCard(
        modifier = Modifier.clickable { onToggle() },
        backgroundColor = if (habit.isCompletedToday) SurfaceElevated else SurfaceCard,
        borderColor = if (habit.isCompletedToday) CyberCyan.copy(alpha = 0.5f) else BorderSubtle
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
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            (if (habit.isCompletedToday) CyberCyan else SlateMuted).copy(alpha = 0.15f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (habit.isCompletedToday) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Check",
                        tint = if (habit.isCompletedToday) CyberCyan else SlateMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (habit.isCompletedToday) PlatinumWhite else SlateMuted,
                        fontWeight = if (habit.isCompletedToday) FontWeight.Bold else FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = habit.category,
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(PrestigeGold.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Série : ${habit.currentStreak} j (Record : ${habit.bestStreak} j)",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrestigeGoldLight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
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
    }
}

@Composable
private fun GoalsTab(
    goals: List<StrategicGoalOKR>,
    onUpdateGoalProgress: (StrategicGoalOKR, Double) -> Unit,
    onDeleteGoal: (StrategicGoalOKR) -> Unit,
    onAddGoal: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Button(
                onClick = onAddGoal,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrestigeGold,
                    contentColor = ObsidianDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_open_add_goal")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Définir un Nouvel Objectif OKR", fontWeight = FontWeight.Bold)
            }
        }

        item {
            Text(
                text = "OBJECTIFS & KEY RESULTS TRIMESTRIELS (${goals.size})",
                style = MaterialTheme.typography.labelSmall,
                color = SlateMuted,
                letterSpacing = 1.sp
            )
        }

        items(goals) { goal ->
            GoalFullCard(
                goal = goal,
                onDelta = { delta -> onUpdateGoalProgress(goal, delta) },
                onDelete = { onDeleteGoal(goal) }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun GoalFullCard(
    goal: StrategicGoalOKR,
    onDelta: (Double) -> Unit,
    onDelete: () -> Unit
) {
    ExecutiveCard(
        borderColor = if (goal.isCompleted) EmeraldLight.copy(alpha = 0.5f) else PrestigeGold.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            (if (goal.isCompleted) EmeraldLight else PrestigeGold).copy(alpha = 0.15f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (goal.isCompleted) "OBJECTIF ATTEINT" else "${goal.pillar} • ${goal.targetDate}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (goal.isCompleted) EmeraldLight else PrestigeGold,
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
                color = if (goal.isCompleted) EmeraldLight else PrestigeGold,
                trackColor = MidnightNavy
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${goal.currentValue.toInt()} / ${goal.targetValue.toInt()} ${goal.unit}",
                        style = MaterialTheme.typography.titleSmall,
                        color = PlatinumWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${(goal.progressRatio * 100).toInt()}% accompli",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (goal.isCompleted) EmeraldLight else PrestigeGold
                    )
                }

                // Interactive increment / decrement buttons
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val step = if (goal.targetValue >= 1000) 500.0 else 1.0

                    Box(
                        modifier = Modifier
                            .background(MidnightNavy, RoundedCornerShape(8.dp))
                            .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                            .clickable { onDelta(-step) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "-${step.toInt()}",
                            color = SlateMuted,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(PrestigeGold, RoundedCornerShape(8.dp))
                            .clickable { onDelta(step) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "+${step.toInt()}",
                            color = ObsidianDark,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
