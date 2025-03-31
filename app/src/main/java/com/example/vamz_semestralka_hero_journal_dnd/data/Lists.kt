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

data class Region(
    @DrawableRes val imageResourceId: Int,
    val regionName: String
)

val regions = listOf(
    Region(
        imageResourceId = R.drawable.bandle_city_crest_icon,
        regionName = "Bandle City"
    ),
    Region(
        imageResourceId = R.drawable.bilgewater_crest_icon,
        regionName = "Bilgewater"
    ),
    Region(
        imageResourceId = R.drawable.demacia_crest_icon,
        regionName = "Demacia"
    ),
    Region(
        imageResourceId = R.drawable.ionia_crest_icon,
        regionName = "Ionia"
    ),
    Region(
        imageResourceId = R.drawable.ixtal_crest_icon,
        regionName = "Ixtal"
    ),
    Region(
        imageResourceId = R.drawable.piltover_crest,
        regionName = "Piltover"
    ),
    Region(
        imageResourceId = R.drawable.shadow_isles_crest_icon,
        regionName = "Shadow Isles"
    ),
    Region(
        imageResourceId = R.drawable.shurima_crest_icon,
        regionName = "Shurima"
    ),
    Region(
        imageResourceId = R.drawable.mount_targon_crest_icon,
        regionName = "Targon"
    ),
    Region(
        imageResourceId = R.drawable.noxus_crest_icon,
        regionName = "Noxus"
    ),
    Region(
        imageResourceId = R.drawable.freljord_crest_icon,
        regionName = "The Freljord"
    ),
    Region(
        imageResourceId = R.drawable.void_crest_icon,
        regionName = "The Void"
    ),
    Region(
        imageResourceId = R.drawable.zaun_crest_icon,
        regionName = "Zaun"
    )
)
