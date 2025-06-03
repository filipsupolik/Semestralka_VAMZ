package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun RegionPage(viewModel: CharacterCreationViewModel = viewModel())
{
    val regionState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            RegionTopAppBar(title = regionState.selectedRegion?.regionName ?: "")
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Image()
            Card() { }
        }
    }
}

@Composable
fun RegionTopAppBar(title: String) {
    TODO("Not yet implemented")
}
