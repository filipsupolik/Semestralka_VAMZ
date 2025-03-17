package com.example.vamz_semestralka_hero_journal_dnd.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.data.characters
import com.example.vamz_semestralka_hero_journal_dnd.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfHeroesTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Image(
                    painter = painterResource(R.drawable._92500_estate_home_house_real_icon),
                    contentDescription = stringResource(R.string.home_button_desc),
                    modifier = modifier
                        .size(dimensionResource(R.dimen.image_size))
                )
                Text(
                    text = stringResource(R.string.character_topAppBar_title)
                )
                Image(
                    painter = painterResource(R.drawable._035021_person_add_icon),
                    contentDescription = stringResource(R.string.character_create_button_desc)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { ListOfHeroesTopAppBar() },
        modifier = Modifier.fillMaxWidth()
    ) {it ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.league_of_legends_phone_dup6c9hrv47g005r),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            LazyColumn(
                contentPadding = it,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                items(characters) {
                    HeroItem(
                        heroProfile = it,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@Composable
fun HeroItem(heroProfile: HeroProfile ,modifier: Modifier = Modifier) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeroItemIcon()
            HeroItemDescription()
            HeroItemLvl()
        }
    }
}

@Composable
fun HeroItemIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable._03017_avatar_default_head_person_unknown_icon),
        contentDescription = stringResource(R.string.hero_icon),
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .clip(shape = Shapes.small)
    )
}

@Composable
fun HeroItemDescription(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = stringResource(R.string.character_name)
        )
        Row {
            Text(
                text = stringResource(R.string.character_description_race)
            )
            Text(
                text = stringResource(R.string.character_description_class)
            )
        }
    }
}

@Composable
fun HeroItemLvl(level: Int,modifier: Modifier = Modifier) {
    Row {
        Text(
            text = stringResource(R.string.level)
        )
        Text(
            text = level
        )
    }
}


@Preview
@Composable
fun ListOfHeroesPreview(){
    CharacterPage()
}