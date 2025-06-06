package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel
import com.example.vamz_semestralka_hero_journal_dnd.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfHeroesTopAppBar(modifier: Modifier = Modifier, onHome: () -> Unit, onCreateCharacter: () -> Unit){
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
                        .clickable {
                            onHome()
                        }
                )
                Text(
                    text = stringResource(R.string.character_topAppBar_title)
                )
                Image(
                    painter = painterResource(R.drawable._035021_person_add_icon),
                    contentDescription = stringResource(R.string.character_create_button_desc),
                    modifier.clickable {
                        onCreateCharacter()
                    }
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

@Composable
fun CharacterPage(
    viewModel: CharacterCreationViewModel,
    modifier: Modifier = Modifier,
    onCreateCharacter: () -> Unit,
    onShowStatsOfCharacter: (String) -> Unit,
    onBack: () -> Unit
) {
    val characterPageState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { ListOfHeroesTopAppBar(onHome = onBack, onCreateCharacter = onCreateCharacter) },
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
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                items(characterPageState.allCharacters) {
                    HeroItem(
                        heroProfile = it,
                        modifier = Modifier.clickable {
                            onShowStatsOfCharacter(it.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HeroItem(heroProfile: HeroProfile ,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.widthIn(min = 250.dp, max = 300.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeroItemIcon(paintResource = heroProfile.imageResourceId)
            HeroItemDescription(
                name = heroProfile.name,
                raceOfHero = heroProfile.descriptionCharacterRace,
                classOfHero = heroProfile.descriptionCharacterClass,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.weight(1f))

            HeroItemLvl(
                level = heroProfile.lvl,
            )
        }
    }
}

@Composable
fun HeroItemIcon(paintResource: Int,modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        Image(
            painter = painterResource(paintResource),
            contentDescription = stringResource(R.string.hero_icon),
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .clip(shape = Shapes.small)
        )
    }
}

@Composable
fun HeroItemDescription(raceOfHero: String,classOfHero: String,name: String,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = raceOfHero,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = classOfHero,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall
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
            text = stringResource(R.string.level_value, level)
        )
    }
}


@Preview
@Composable
fun ListOfHeroesPreview(){
    HeroItem(
        heroProfile = HeroProfile(
            imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
            name = "slfhnksjdfnksd",
            descriptionCharacterRace = HeroRaceDesc.DarkinBorn().name,
            descriptionCharacterClass = HeroClassDesc.Paladin().name,
            lvl = 1,
            lvlDescription = R.string.level
        )
    )
}