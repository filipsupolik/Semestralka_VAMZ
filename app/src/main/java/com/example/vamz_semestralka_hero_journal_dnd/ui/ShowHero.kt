package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterUIState

/**
 * Obrazovka pre zobrazenie hodnot schopnosti a zakladnych statistik hraca
 * Obrazovka je vytvorena pomocou ChatGPT
 * ChatGPT mi dal zakladny navrh obrazovky, ktory som si nasledne upravil tak ako som ja potreboval
 */


/**
 * Zakladna obrazovk, ktora obsahuje vsetky komponenty pokope
 * @param characterCreationViewModel (viewModel)
 * @param onBack (void), funkcia pouzita v navigacii pomocou ktore sa navigujem spat do zoznamu postav
 */
@Composable
fun CharacterStatsScreen(
    characterCreationViewModel: CharacterCreationViewModel,
    onBack: () -> Unit
) {
        val characterState: CharacterUIState by characterCreationViewModel.uiState.collectAsState()
        val currentHero = characterCreationViewModel.getChosenHeroProfile(characterState.selectedPlayerName)

        Scaffold(
            topBar = {
                ShowHeroTopAppBar(onBack = onBack, onDelete = { characterCreationViewModel.deleteCharacterFromList(currentHero?.name?:"") })
            }
        ) { innerPadding ->
            CharacterStatsMainPage(
                modifier = Modifier.padding(innerPadding),
                viewModel = characterCreationViewModel,
                onIncreaseHp = { characterCreationViewModel.increaseHp() },
                onDecreaseHp = { characterCreationViewModel.decreaseHp() },
                currentHero = currentHero
            )
        }
}

/**
 * Composable pre vrchnu listu obrazovky CharacterStatsScreen
 * @param onBack (void), funkcia pouzita v navigacii pomocou ktore sa navigujem spat do zoznamu postav
 * @param onDelete, funkcia pouzita na vymazanie aktualnej postavy zo zoznamu postav
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHeroTopAppBar(onBack: () -> Unit, onDelete: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.stats))
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_icon),
                    modifier = Modifier.clickable {
                        onBack()
                        onDelete()
                    }
                )
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(R.string.home_icon),
                modifier = Modifier.clickable {
                    onBack()
                }
            )
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

/**
 * Obrazovka pre zobrazenie statistik bez vrchnej listy
 */
@Composable
fun CharacterStatsMainPage(
    modifier: Modifier,
    viewModel: CharacterCreationViewModel,
    onIncreaseHp: () -> Unit,
    onDecreaseHp: () -> Unit,
    currentHero: HeroProfile?
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
                    ((currentHero?.hp?.toFloat()
                        ?: 0f) / (currentHero?.hp?: 1)).coerceIn(0f, 1f)
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
                currentHero?.hp?:0,
                currentHero?.maxHp?:0
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
            items(currentHero?.raceStats?.toList()?: emptyList()) {(key, value) ->
                AbilitiesValueCard(title = key.name, abilititesValue = value.toString())
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
            items(currentHero?.totalStatsValue?.toList()?: emptyList()) {(key, value) ->
                AbilitiesValueCard(key.name,
                    stringResource(
                        R.string.abilities_values,
                        value,
                        characterState.raceStats[key]?.toString() ?: ""
                    ), width = 79.dp)
            }
        }
    }
}

/**
 * Karta pre zobrazenie hodnoty schopnosti
 */

@Composable
fun AbilitiesValueCard(title: String, abilititesValue: String, width: Dp = 120.dp) {
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
        Text(abilititesValue, style = MaterialTheme.typography.headlineMedium)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterStatsScreenPreview() {
    MaterialTheme {
        CharacterStatsScreen(characterCreationViewModel= viewModel(), onBack = {},)
    }
}

