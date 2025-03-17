package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.vamz_semestralka_hero_journal_dnd.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroJournalMainPageTopAppBar(modifier: Modifier = Modifier ) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = stringResource(id = R.string.main_page_top_app_bar_decription),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

@Composable
fun MainPageButton(description: String, id: Int ,modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .alpha(0.9f)
    ){
        Button(
            onClick = {},
            modifier = Modifier.width(300.dp),
            shape = RectangleShape,
            colors = ButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id),
                    contentDescription = description
                )
                Text(
                    text = description,
                    color = Color.Black,
                    fontSize = TextUnit(
                        value = 26f,
                        type = TextUnitType.Sp
                    )
                )
            }
        }
    }
}

@Composable
fun MainPage(modifier: Modifier = Modifier){
    Scaffold(
        topBar = {
            HeroJournalMainPageTopAppBar()
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Image(
                painter = painterResource(R.drawable.league_of_legends_phone_dup6c9hrv47g005r),
                contentDescription = stringResource(R.string.background_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainPageButton(
                    stringResource(R.string.character_create_button_desc),
                    R.drawable.clipart2592289
                )
                MainPageButton(
                    stringResource(R.string.world_info_button_desc),
                    R.drawable._037118
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        MainPage()
    }
}