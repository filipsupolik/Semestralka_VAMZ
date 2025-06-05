package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun CharacterStatsScreen(
    characterCreationViewModel: CharacterCreationViewModel,
    onBack: () -> Unit
) {
    val characterState by characterCreationViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val characterInfo: Map<String, String?> = mapOf(
        "Name" to characterState.playerName,
        "Region" to characterState.playerRegion.regionName,
        "Class" to characterState.characterClass.name,
        "Race" to characterState.characterRace.name
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(characterInfo) {
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                ShowHeroTopAppBar(characterInfo, onBack) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
        ) { innerPadding ->
            CharacterStatsContent(modifier = Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHeroTopAppBar(characterInfo: Map<String, String?>, onMenuClick:()->Unit, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Text("Stats")
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

@Composable
fun ShowMenu(characterInfo: Map<String, String?>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = characterInfo.get("Name") ?: "", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        characterInfo.forEach { (label, value) ->
            Text("$label: $value", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterStatsContent(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable._03017_avatar_default_head_person_unknown_icon), // Replace with your image resource
            contentDescription = "Character Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Speed", "10")
            StatCard("HP", "19/19")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Strength Attack", "2")
            StatCard("Dex Attack", "5")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Abilities:", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        val abilities = listOf(
            "STR" to "10(0)",
            "Con" to "14(2)",
            "Dex" to "16(3)",
            "Int" to "14(2)",
            "Wis" to "8(-1)",
            "Cha" to "8(-1)"
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            abilities.forEach { (name, value) ->
                StatCard(name, value, width = 80.dp)
            }
        }
    }
}

@Composable
fun DrawerContent(characterInfo: Map<String, String?>, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Informácie o postave", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        characterInfo.forEach { (label, value) ->
            Text("$label: ${value ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
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
        Text(value, style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterStatsScreenPreview() {
    MaterialTheme {
        CharacterStatsScreen(characterCreationViewModel= viewModel(), onBack = {},)
    }
}

