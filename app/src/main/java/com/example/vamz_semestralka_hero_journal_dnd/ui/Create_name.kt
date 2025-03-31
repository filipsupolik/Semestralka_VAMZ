package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
            painter = painterResource(R.drawable._86992),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            alpha = 0.5f,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium))
        ) {
            CreateNameTitle()
            CreateNameTextField(
                nameOfCharacter = nameOfCharacter
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.Start
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
            text = stringResource(R.string.name_text_field_label),
            color = Color.White
        ) },
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        shape = shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
        ),
    )
}

@Composable
fun CreateNameTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.create_name_text),
        color = Color.White,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    )
}

@Preview
@Composable
fun AddNamePreview() {
    CharacterNameChoice()
}
