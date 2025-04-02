package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.Lists

@Composable
fun HeroListPage(listOfDifferentTypes: List<Lists>,modifier: Modifier = Modifier){
    Scaffold(
        topBar = { HeroListTopAppBar() }
    ) {  it ->
        LazyColumn(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            contentPadding = it,
        ) {
            items(listOfDifferentTypes) { item ->
                when(item){
                    is Lists.HeroRaces -> HeroRaceItem(heroRace = item)
                    is Lists.HeroClasses -> HeroClassItem(heroClass = item)
                    is Lists.Region -> RegionItem(region = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroListTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.region_top_app_bar)
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun RegionItem(region: Lists.Region, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
fun HeroClassItem(heroClass: Lists.HeroClasses, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = heroClass.regionName,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(heroClass.imageResourceId),
                contentDescription = heroClass.regionName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
            )
        }
    }
}

@Composable
fun HeroRaceItem(heroRace: Lists.HeroRaces, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = heroRace.regionName,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(heroRace.imageResourceId),
                contentDescription = heroRace.regionName,
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
    HeroListPage()
}