package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vamz_semestralka_hero_journal_dnd.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfHeroesTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(R.drawable._92500_estate_home_house_real_icon),
                    contentDescription = stringResource(R.string.home_button_desc)
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
        modifier = modifier
    )
}

@Composable
fun CharacterPage(modifier: Modifier = Modifier) {

}



@Preview
@Composable
fun ListOfHeroesPreview(){

}