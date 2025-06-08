package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.StatMethod
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

/**
 * Obrazovka je vytvorena s pomocou ChatGPT, spytal som sa na kostru celej stranky
 * ktoru som si upravoval podla vlastnej potreby
 * Pridaval som vlastne parametre, upravovanie hodnot pomocou viewModelu...
 */


@Composable
fun AbilityScreen(
    completeCharacterCreationViewModel: CharacterCreationViewModel,
    modifier: Modifier,
    onNextPage: () -> Unit,
    onBack: () -> Unit
) {
    val statsPageState by completeCharacterCreationViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            StatsCheckTopAppBar(viewModel = completeCharacterCreationViewModel, modifier, onNextPage = onNextPage, onBackClick = onBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(stringResource(R.string.abilities), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(stringResource(R.string.select_a_method), style = MaterialTheme.typography.titleMedium)

            MethodSelector(viewModel = completeCharacterCreationViewModel)

            if (statsPageState.selectedMethodStatsCounting == StatMethod.POINT_BUY) {
                Text(stringResource(R.string.remaining_points, statsPageState.remainingPoints), style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            statsPageState.baseValue.forEach { (attribute, base) ->
                AbilityRow(
                    label = attribute.name,
                    base = base,
                    raceBonus = statsPageState.raceStats[attribute] ?: 0,
                    method = statsPageState.selectedMethodStatsCounting,
                    onBaseChange = { newValue ->
                        if (statsPageState.selectedMethodStatsCounting == StatMethod.POINT_BUY) {
                            val cost = newValue - (statsPageState.baseValue[attribute] ?: 0)
                            if (statsPageState.remainingPoints - cost >= 0 && newValue in 8..15) {
                                completeCharacterCreationViewModel.updateBaseValue(attribute, newValue)
                                completeCharacterCreationViewModel.setRemainingStatsPoints(cost)
                            }
                        } else {
                            completeCharacterCreationViewModel.updateBaseValue(attribute, newValue)
                        }

                        completeCharacterCreationViewModel.setTotalStat(attribute, newValue + (statsPageState.raceStats[attribute] ?: 0))
                    },
                    viewModel = completeCharacterCreationViewModel
                )
            }

            BottomButtons(
                completeCharacterCreationViewModel = completeCharacterCreationViewModel,
                onNextPage = onNextPage,
                onBack = onBack
            )

        }
    }
}

/**
 * Tlacidla na presun na dalsiu/predoslu obrazovku, zaroven aj ukladaju stav schopnosti pri presune
 */

@Composable
fun BottomButtons(
    completeCharacterCreationViewModel: CharacterCreationViewModel,
    onNextPage: () -> Unit,
    onBack: () -> Unit
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        Button(onClick = {
            completeCharacterCreationViewModel.resetAbilities()
            onBack()
        }) {
            Text(text = stringResource(R.string.back_label_button))
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            completeCharacterCreationViewModel.addCreatedCharacterToList()
            completeCharacterCreationViewModel.reset()
            onNextPage()
        }) {
            Text(text = stringResource(R.string.next_label_button))
        }
    }
}

/**
 * Sluzi na zvolenie konkretnej metody pre pocitanie hodnot
 */

@Composable
fun MethodSelector(
    viewModel: CharacterCreationViewModel
) {
    val statsPageState by viewModel.uiState.collectAsState()

    StatMethod.entries.forEach { method ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = statsPageState.selectedMethodStatsCounting == method,
                onClick = {
                    viewModel.setSelectedMethod(method)
                    when (method) {
                        StatMethod.ROLL -> viewModel.setBaseValues(3)
                        StatMethod.STANDARD_ARRAY -> viewModel.setBaseValues(-1)
                        StatMethod.POINT_BUY -> viewModel.setBaseValues(8)
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = method.label, style = MaterialTheme.typography.titleMedium)
        }
    }
}

/**
 * Zobrazuje a nastavuje hodnoty pre 1 konkretnu schopnost
 */

@Composable
fun AbilityRow(
    viewModel: CharacterCreationViewModel,
    label: String,
    base: Int,
    raceBonus: Int,
    method: StatMethod,
    onBaseChange: (Int) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (method == StatMethod.STANDARD_ARRAY) {
                val options = (8..15).toList()
                DropdownMenuBox(
                    selected = if (base == -1) "--" else base.toString(),
                    options = options.map { it.toString() },
                    onOptionSelected = { onBaseChange(it.toInt()) },
                    setValue = { viewModel.setBaseValues(it.toInt()) }
                )
            } else {
                val min = if (method == StatMethod.ROLL) 3 else 8
                val max = if (method == StatMethod.ROLL) 18 else 15

                Button(onClick = { if (base > min) onBaseChange(base - 1) }) {
                    Text("-")
                }
                Text(base.toString(), modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { if (base < max) onBaseChange(base + 1) }) {
                    Text("+")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.value_of_stats, raceBonus, base + raceBonus))
        }
    }
}

/**
 * Dropdown menu s ktorym mi pomahal chatGPT
 * Sluzi na nastavenie hodnoty pri volbe Standard array
 */

@Composable
fun DropdownMenuBox(selected: String, options: List<String>, onOptionSelected: (String) -> Unit, setValue:(String) ->Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = setValue,
            modifier = Modifier.width(180.dp),
            readOnly = true,
            label = { Text(stringResource(R.string.base)) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.dropdown))
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { value ->
                DropdownMenuItem(
                    text = { Text(value) },
                    onClick = {
                        expanded = false
                        onOptionSelected(value)
                    }
                )
            }
        }
    }
}

/**
 * Horna lista obrazovky pre Stats obrazovku
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsCheckTopAppBar(viewModel: CharacterCreationViewModel, modifier: Modifier, onBackClick: () -> Unit = {}, onNextPage: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.Stats_top_app_bar_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.arrow_forward_to_next_page),
                    modifier = Modifier.clickable {
                        viewModel.addCreatedCharacterToList()
                        onNextPage()
                    }
                )
            }
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable._34226_back_arrow_left_icon),
                contentDescription = stringResource(R.string.back_label_button),
                modifier = Modifier
                    .clickable {
                        viewModel.resetAbilities()
                        onBackClick()
                    }
                    .padding(horizontal = 16.dp)
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAbilityScreen() {
    MaterialTheme {
        AbilityScreen(
            completeCharacterCreationViewModel = viewModel(),
            modifier = Modifier,
            onBack = {},
            onNextPage = {}
        )
    }
}
