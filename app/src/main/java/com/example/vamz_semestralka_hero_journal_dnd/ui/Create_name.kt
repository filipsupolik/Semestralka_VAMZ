package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vamz_semestralka_hero_journal_dnd.R

@Composable
fun CharacterNameChoice(modifier: Modifier = Modifier) {
    var nameOfCharacter by remember { mutableStateOf("") }
    Box{
        Image(
            painter = painterResource(R.drawable.battlegrounds_mobile_india_is_currently_only_out_for_android_users__ios_users_will_have_to_wait_a_li),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            CreateNameTitle()
            CreateNameTextField(nameOfCharacter)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CreateNameAddButton()
                CreateNameCancelButton()
            }
        }
    }
}

@Composable
fun CreateNameCancelButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.cancel_button_label)
        )
    }
}

@Composable
fun CreateNameAddButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.add_button_label)
        )
    }
}

@Composable
fun CreateNameTextField(nameOfCharacter: String,modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = nameOfCharacter,
        onValueChange = {},
        label = { Text(
            text = stringResource(R.string.name_text_field_label)
        ) },
        keyboardOptions = KeyboardOptions.Default,
        modifier = modifier
    )
}

@Composable
fun CreateNameTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.create_name_text),
        color = Color.Blue,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    )
}

@Preview
@Composable
fun AddNamePreview() {
    CharacterNameChoice()
}
