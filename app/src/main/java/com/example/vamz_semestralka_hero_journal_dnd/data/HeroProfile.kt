package com.example.vamz_semestralka_hero_journal_dnd.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vamz_semestralka_hero_journal_dnd.R

data class HeroProfile(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val descriptionCharacterRace: Int,
    @StringRes val descriptionCharacterClass: Int,
    @StringRes val lvlDescription: Int,
    val lvl: Int
)

val characters = listOf(
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = R.string.character_name,
        descriptionCharacterRace = R.string.character_description_race,
        descriptionCharacterClass = R.string.character_description_class,
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = R.string.character_name,
        descriptionCharacterRace = R.string.character_description_race,
        descriptionCharacterClass = R.string.character_description_class,
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = R.string.character_name,
        descriptionCharacterRace = R.string.character_description_race,
        descriptionCharacterClass = R.string.character_description_class,
        lvlDescription = R.string.level,
        lvl = 1
    ),
)
