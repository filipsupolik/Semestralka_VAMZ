package com.example.vamz_semestralka_hero_journal_dnd.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vamz_semestralka_hero_journal_dnd.R

data class HeroProfile(
    @DrawableRes val imageResourceId: Int,
    @StringRes val lvlDescription: Int,
    val name: String,
    val descriptionCharacterRace: String,
    val descriptionCharacterClass: String,
    val lvl: Int
)

val characters = listOf(
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jinx",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Hunter",
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jhin",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Hunter",
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jax",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Monk",
        lvlDescription = R.string.level,
        lvl = 1
    ),
)
