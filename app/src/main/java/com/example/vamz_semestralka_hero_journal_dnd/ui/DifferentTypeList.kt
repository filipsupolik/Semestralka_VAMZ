package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.Lists
import com.example.vamz_semestralka_hero_journal_dnd.data.classes
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun HeroListPage(
    whatToSelect: String, listOfDifferentTypes: List<Lists>,
    characterCreationViewModel: CharacterCreationViewModel,
    modifier: Modifier = Modifier,
    onNextPage: (String) -> Unit,
    onBack: () -> Unit
){
    Scaffold(
        topBar = { HeroListTopAppBar(
            whatToSelect = whatToSelect,
            onBack = onBack
        ) }
    ) {  innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            contentPadding = innerPadding,
        ) {
            items(listOfDifferentTypes) { item ->
                when(item){
                    is Lists.HeroRace -> HeroRaceItem(
                        heroRace = item,
                        onClick = {
                            onNextPage(item.raceName)
                            characterCreationViewModel.setHeroRace(
                                item.raceName
                            )
                        })
                    is Lists.HeroClasses -> HeroClassItem(
                        heroClass = item,
                        onClick = {
                            onNextPage(item.className)
                            characterCreationViewModel.setHeroClass(
                                item.className
                            )
                        })
                    is Lists.Region -> RegionItem(
                        region = item,
                        onClick = {
                            onNextPage(item.regionName)
                            characterCreationViewModel.setRegion(
                                item.regionName
                            )
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroListTopAppBar(whatToSelect: String,modifier: Modifier = Modifier, onBack: () -> Unit){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.region_top_app_bar, whatToSelect)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.backArrow),
                modifier = Modifier.clickable {
                    onBack()
                }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun RegionItem(region: Lists.Region, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = region.regionName,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(region.imageResourceId),
                contentDescription = region.regionName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
            )
        }
    }
}

@Composable
fun HeroClassItem(heroClass: Lists.HeroClasses, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = heroClass.className,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(heroClass.imageResourceId),
                contentDescription = heroClass.className,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
            )
        }
    }
}

@Composable
fun HeroRaceItem(heroRace: Lists.HeroRace, onClick:() -> Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = heroRace.raceName,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(heroRace.iconImageResourceId),
                contentDescription = heroRace.raceName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
            )
        }
    }
}

@Preview
@Composable
fun HeroListPreview() {
    HeroListPage("class", listOfDifferentTypes = classes, viewModel(), onNextPage = {}, onBack = {})
}