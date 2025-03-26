package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CharacterNameChoice() {
    var nameOfCharacter by remember { mutableStateOf("") }
    Box {
        Image()
        Column {
            CreateNameTitle()
            CreateNameTextfield()
            CreateNameAddButton()
            CreateNameCancelButton()
        }
    }
}

@Composable
fun CreateNameCancelButton() {
    Button() {
        Text()
    }
}

@Composable
fun CreateNameAddButton() {
    Button() {
        Text()
    }
}

@Composable
fun CreateNameTextfield() {
    TextField()
}

@Composable
fun CreateNameTitle() {
    Column {
        Text()
        Text()
    }
}
