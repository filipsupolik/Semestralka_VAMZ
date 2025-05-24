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
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamz_semestralka_hero_journal_dnd.R

enum class StatMethod { ROLL, STANDARD_ARRAY, POINT_BUY }

@Composable
fun AbilityScreen(
    raceBonuses: Map<String, Int> = mapOf( // napríklad: podľa rasy
        "Strength" to 2,
        "Dexterity" to 0,
        "Constitution" to 1,
        "Intelligence" to 0,
        "Wisdom" to 0,
        "Charisma" to 0
    ),
    modifier: Modifier
) {
    var selectedMethod by remember { mutableStateOf(StatMethod.ROLL) }
    var remainingPoints by remember { mutableStateOf(27) }

    val stats = listOf("Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma")
    val baseValues = remember { mutableStateMapOf<String, Int>().apply { stats.forEach { this[it] = 3 } } }
Scaffold(
    topBar = {
        StatsCheckTopAppBar(modifier)
    }
) {innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
        Text("Abilities", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Select A Method", style = MaterialTheme.typography.titleMedium)
        StatMethod.entries.forEach { method ->
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMethod == method,
                    onClick = {
                        selectedMethod = method
                        when (method) {
                            StatMethod.ROLL -> {
                                stats.forEach { baseValues[it] = 3 }
                            }
                            StatMethod.STANDARD_ARRAY -> {
                                stats.forEach { baseValues[it] = -1 }
                            }
                            StatMethod.POINT_BUY -> {
                                remainingPoints = 27
                                stats.forEach { baseValues[it] = 8 }
                            }
                        }
                    }
                )
                Text(text = method.name.replace("_", " ").capitalize())
            }
        }

        if (selectedMethod == StatMethod.POINT_BUY) {
            Text("Remaining Points: $remainingPoints", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        stats.forEach { stat ->
            AbilityRow(
                label = stat,
                base = baseValues[stat] ?: 0,
                raceBonus = raceBonuses[stat] ?: 0,
                method = selectedMethod,
                onBaseChange = { newValue ->
                    val current = baseValues[stat] ?: 0
                    if (selectedMethod == StatMethod.POINT_BUY) {
                        val cost = pointBuyCost(newValue) - pointBuyCost(current)
                        if (remainingPoints - cost >= 0 && newValue in 8..15) {
                            baseValues[stat] = newValue
                            remainingPoints -= cost
                        }
                    } else {
                        baseValues[stat] = newValue
                    }
                }
            )
        }
    }
}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsCheckTopAppBar(modifier: Modifier, onBackClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.Stats_top_app_bar_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable._34226_back_arrow_left_icon),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(horizontal = 16.dp)
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun AbilityRow(
    label: String,
    base: Int,
    raceBonus: Int,
    method: StatMethod,
    onBaseChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            if (method == StatMethod.STANDARD_ARRAY) {
                val options = (8..15).toList()
                DropdownMenuBox(
                    selected = if (base == -1) "--" else base.toString(),
                    options = options.map { it.toString() },
                    onOptionSelected = { onBaseChange(it.toInt()) }
                )
            } else {
                Button(onClick = { if (base > (if (method == StatMethod.ROLL) 3 else 8)) onBaseChange(base - 1) }) {
                    Text("-")
                }
                Text(base.toString(), modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = {
                    val max = if (method == StatMethod.ROLL) 18 else 15
                    if (base < max) onBaseChange(base + 1)
                }) {
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text("+ $raceBonus = ${base + raceBonus}")
        }
    }
}

@Composable
fun DropdownMenuBox(selected: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            modifier = Modifier.width(80.dp),
            readOnly = true,
            label = { Text("Base") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
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

fun pointBuyCost(score: Int): Int = when (score) {
    8 -> 0
    9 -> 1
    10 -> 2
    11 -> 3
    12 -> 4
    13 -> 5
    14 -> 7
    15 -> 9
    else -> Int.MAX_VALUE
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAbilityScreen() {
    MaterialTheme {
        AbilityScreen(
            raceBonuses = mapOf(
                "Strength" to 2,
                "Dexterity" to 1,
                "Constitution" to 0,
                "Intelligence" to 0,
                "Wisdom" to 0,
                "Charisma" to 0
            ),
            modifier = Modifier
        )
    }
}

