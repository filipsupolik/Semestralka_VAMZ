package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun CharacterStatsScreen(
    characterCreationViewModel: CharacterCreationViewModel,
    onBack: () -> Unit
) {
    val characterState by characterCreationViewModel.uiState.collectAsState()
    val isDrawerOpen = remember { mutableStateOf(false) }
    val showXpDialog = remember { mutableStateOf(false) }

    val characterInfo: Map<String, String?> = mapOf(
        "Name" to characterState.playerName,
        "Region" to characterState.playerRegion.regionName,
        "Class" to characterState.characterClass.name,
        "Race" to characterState.characterRace.name
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                ShowHeroTopAppBar(onMenuClick = {
                    isDrawerOpen.value = true
                }, onBack = onBack)
            }
        ) { innerPadding ->
            CharacterStatsContent(
                modifier = Modifier.padding(innerPadding),
                viewModel = characterCreationViewModel,
                onIncreaseHp = { characterCreationViewModel.increaseHp() },
                onDecreaseHp = { characterCreationViewModel.decreaseHp() }
            )
        }

        AnimatedVisibility(visible = isDrawerOpen.value) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                DrawerContent(
                    characterInfo = characterInfo,
                    viewModel = characterCreationViewModel,
                    onClose = { isDrawerOpen.value = false },
                    onAddXpClick = { showXpDialog.value = true }
                )
            }
        }

        if (showXpDialog.value) {
            AddXpDialog(
                xpToAdd = characterState.XpToAdd,
                onIncrement = { characterCreationViewModel.incrementTempXp() },
                onDecrement = { characterCreationViewModel.decrementTempXp() },
                onCancel = { showXpDialog.value = false },
                onConfirm = {
                    characterCreationViewModel.addExperience(characterState.XpToAdd)
                    showXpDialog.value = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHeroTopAppBar(onMenuClick: () -> Unit, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Stats")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home icon",
                    modifier = Modifier.clickable {
                        onBack()
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterStatsContent(
    modifier: Modifier,
    viewModel: CharacterCreationViewModel,
    onIncreaseHp: () -> Unit,
    onDecreaseHp: () -> Unit
) {
    val  characterState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable._03017_avatar_default_head_person_unknown_icon),
            contentDescription = "Character Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onDecreaseHp) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Remove HP")
            }

            LinearProgressIndicator(
                progress = {
                    val current = characterState.currentHP.toFloat()
                    val max = characterState.totalHP.toFloat().coerceAtLeast(1f)
                    (current / max).coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp)
                    .padding(horizontal = 16.dp)
            )

            IconButton(onClick = onIncreaseHp) {
                Icon(Icons.Default.Favorite, contentDescription = "Add HP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Saving Throws:", style = MaterialTheme.typography.titleMedium)

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            characterState.raceStats.forEach { attribute ->
                StatCard(attribute.key.name, attribute.value.toString(), width = 80.dp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Abilities:", style = MaterialTheme.typography.titleMedium)

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            characterState.totalStatsValue.forEach { attribute ->
                StatCard(attribute.key.name, "${attribute.value} (${characterState.raceStats.forEach{attr-> attr.value}})", width = 80.dp)
            }
        }
    }
}


@Composable
fun DrawerContent(
    characterInfo: Map<String, String?>,
    viewModel: CharacterCreationViewModel,
    onClose: () -> Unit,
    onAddXpClick: () -> Unit
) {
    val characterUIState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable._03017_avatar_default_head_person_unknown_icon),
            contentDescription = "Character Image",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )

        Text(
            text = characterUIState.playerName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
        )

        Text(
            text = "Lvl. ${characterUIState.playerLevel} ${characterUIState.characterRace.name}, ${characterUIState.characterClass.name}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
        )

        val progress = characterUIState.playerXP.toFloat() / characterUIState.playerXPToNextLvl
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )

        Text(
            text = "${characterUIState.playerXP} / ${characterUIState.playerXPToNextLvl} XP",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = onAddXpClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        ) {
            Text("+")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Base Info", style = MaterialTheme.typography.titleMedium)
        characterInfo.forEach { (label, value) ->
            Text("$label: ${value ?: "N/A"}", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Zatvoriť",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onClose() }
                .padding(top = 8.dp)
        )
    }
}




@Composable
fun StatCard(title: String, value: String, width: Dp = 120.dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(4.dp)
            .background(Color.LightGray, RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(value.toString(), style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun AddXpDialog(
    xpToAdd: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        },
        title = { Text("Add XP") },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    onDecrement()
                }) {
                    Text("-")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = xpToAdd.toString())

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    onIncrement()
                }) {
                    Text("+")
                }
            }
        }
    )
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterStatsScreenPreview() {
    MaterialTheme {
        CharacterStatsScreen(characterCreationViewModel= viewModel(), onBack = {},)
    }
}

