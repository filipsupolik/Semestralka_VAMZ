package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.Region
import com.example.vamz_semestralka_hero_journal_dnd.data.regions

@Composable
fun StarterRgionPage(modifier: Modifier = Modifier){
    Scaffold(
        topBar = { StarterRegionTopAppBar() }
    ) {  it ->
        LazyColumn(
            contentPadding = it,
        ) {
            items(regions) {
                RegionItem(
                    region = it
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarterRegionTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(),
                    contentDescription = stringResource(R.string.region_top_app_bar_back_arrow_desc)
                )
                Text(
                    text = stringResource(R.string.region_top_app_bar)
                )
            }
        }
    )
}

@Composable
fun RegionItem(region: Region ,modifier: Modifier = Modifier) {
    Card {
        Row {
            Text(
                text = region.regionName
            )
            Image(
                painter = painterResource(region.imageResourceId),
                contentDescription = region.regionName
            )
        }
    }
}