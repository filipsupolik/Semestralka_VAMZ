package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    val openDrawer = remember { mutableStateOf(false) }
    val isDialogOpen = remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                ShowHeroTopAppBar(onMenuClick = {
                    openDrawer.value = true
                }, onBack = onBack)
            }
        ) { innerPadding ->
            CharacterStatsMainPage(
                modifier = Modifier.padding(innerPadding),
                viewModel = characterCreationViewModel,
                onIncreaseHp = { characterCreationViewModel.increaseHp() },
                onDecreaseHp = { characterCreationViewModel.decreaseHp() }
            )
        }

        AnimatedVisibility(visible = openDrawer.value) {
            Box(
                modifier = Modifier
                    .padding(top = 113.dp)
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .background(Color.White)
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                SidePanelInfo(
                    viewModel = characterCreationViewModel,
                    onClose = { openDrawer.value = false },
                    onAddXpClick = { isDialogOpen.value = true },
                    onDelete = {
                        onBack()
                        characterCreationViewModel.deleteCharacterFromList(characterState.selectedPlayerName)
                    }
                )
            }
        }

        if (isDialogOpen.value) {
            TotalXpAddingDialog(
                xpToAdd = characterState.XpToAdd,
                onIncrement = { characterCreationViewModel.incrementTempXp() },
                onDecrement = { characterCreationViewModel.decrementTempXp() },
                onCancel = { isDialogOpen.value = false },
                onConfirm = {
                    characterCreationViewModel.addExperience(characterState.XpToAdd)
                    isDialogOpen.value = false
                }
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHeroTopAppBar(onMenuClick: () -> Unit, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.stats))
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home_icon),
                    modifier = Modifier.clickable {
                        onBack()
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Composable
fun CharacterStatsMainPage(
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
            contentDescription = stringResource(R.string.character_image),
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onDecreaseHp) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = stringResource(R.string.remove_hp))
            }

            LinearProgressIndicator(
                progress = {
                    (characterState.currentHP.toFloat() / (characterState.totalHP.takeIf { it > 0 } ?: 1)).coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
            )

            IconButton(onClick = onIncreaseHp) {
                Icon(Icons.Default.Favorite, contentDescription = stringResource(R.string.add_hp))
            }
        }

        Text(
            text = stringResource(
                R.string.hp_value,
                characterState.currentHP,
                characterState.totalHP
            ),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.saving_throws),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(characterState.raceStats.toList()) {(key, value) ->
                StatCard(key.name, value.toString(), width = 76.dp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.abilities),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(characterState.totalStatsValue.toList()) {(key, value) ->
                StatCard(key.name,
                    stringResource(
                        R.string.abilities_values,
                        value,
                        characterState.raceStats[key]?.toString() ?: ""
                    ), width = 79.dp)
            }
        }
    }
}


@Composable
fun SidePanelInfo(
    viewModel: CharacterCreationViewModel,
    onClose: () -> Unit,
    onAddXpClick: () -> Unit,
    onDelete: () -> Unit
) {
    val characterUIState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable._03017_avatar_default_head_person_unknown_icon),
            contentDescription = stringResource(R.string.character_image),
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
        )

        Text(
            text = characterUIState.playerName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = dimensionResource(R.dimen.padding_small))
        )

        Text(
            text = stringResource(
                R.string.lvl,
                characterUIState.playerLevel,
                characterUIState.characterRace.name,
                characterUIState.characterClass.name
            ),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        LinearProgressIndicator(
            progress = { characterUIState.playerXP.toFloat() / characterUIState.playerXPToNextLvl.coerceIn(0, 1) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                ),
        )

        Text(
            text = stringResource(
                R.string.xp,
                characterUIState.playerXP,
                characterUIState.playerXPToNextLvl
            ),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = onAddXpClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            Text(stringResource(R.string.plus_button_label))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(stringResource(R.string.base_info_open_dialog_stats_page_title), style = MaterialTheme.typography.titleMedium)
        Text(stringResource(R.string.name_in_side_panel, characterUIState.playerName), style = MaterialTheme.typography.bodyMedium)
        Text(
            stringResource(
                R.string.class_name_side_panel_stats_page,
                characterUIState.characterClass.name
            ), style = MaterialTheme.typography.bodyMedium)
        Text(
            stringResource(
                R.string.race_name_stats_page_side_panel,
                characterUIState.characterRace.name
            ), style = MaterialTheme.typography.bodyMedium)
        Text(
            stringResource(
                R.string.region_name_stats_page_side_panel,
                characterUIState.playerRegion.regionName
            ), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDelete,
        ) {
            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_button_label))
        }

        Text(
            stringResource(R.string.close_label_button_side_panel_stats_page),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onClose() }
                .padding(top = dimensionResource(R.dimen.padding_small))
        )
    }
}




@Composable
fun StatCard(title: String, value: String, width: Dp = 120.dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(4.dp)
            .border(color = MaterialTheme.colorScheme.primary, width = 1.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(4.dp))
            .padding(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun TotalXpAddingDialog(
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
                Text(stringResource(R.string.ok_label_dialog_adding_XP))
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(stringResource(R.string.cancel_label_dialog_adding_XP))
            }
        },
        title = { Text(stringResource(R.string.Add_xp_title_dialog_adding_XP)) },
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    onDecrement()
                }) {
                    Text(stringResource(R.string.minus))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = xpToAdd.toString())

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    onIncrement()
                }) {
                    Text(stringResource(R.string.plus_button_label))
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

