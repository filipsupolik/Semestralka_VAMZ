package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun RegionPage(viewModel: CharacterCreationViewModel)
{
    val regionState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            RegionTopAppBar(title = regionState.selectedRegion?.regionName ?: "")
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Button(
                        onClick = {
                            viewModel.resetRegion()
                        },
                    ) {
                        Text(text = "Back")
                    }
                    Button(
                        onClick = {
                            viewModel.setRegion(
                                name = regionState.selectedRegion?.regionName ?: ""
                            )
                        }
                    ) {
                        Text(text = "Next")
                    }
                }
            )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            regionState.selectedRegion?.imageRes?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "Image of the ${regionState.selectedRegion?.regionName}"
                )
            }
            Card() { }
        }
    }
}

@Composable
fun RegionTopAppBar(title: String) {
    TODO("Not yet implemented")
}
