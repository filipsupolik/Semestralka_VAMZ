package com.example.vamz_semestralka_hero_journal_dnd.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vamz_semestralka_hero_journal_dnd.R

enum class RaceSize{
    SMALL, MEDIUM, LARGE
}

enum class RaceAttributes{

}

sealed class Lists{
    data class Region(
        @DrawableRes val imageResourceId: Int,
        val regionName: String
    ):Lists()
    data class HeroClasses(
        @DrawableRes val imageResourceId: Int,
        val regionName: String
    ):Lists()
    data class HeroRaces(
        @DrawableRes val iconImageResourceId: Int,
        @DrawableRes val mainImageResourceId: Int,
        val raceName: String,
        val raceBackStory: String,
        val raceSpeed: Int,
        val raceSize: RaceSize,
        val raceAbilities: RaceSize,
    ):Lists()
}

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

val regions = listOf(
    Lists.Region(
        imageResourceId = R.drawable.bandle_city_crest_icon,
        regionName = "Bandle City"
    ),
    Lists.Region(
        imageResourceId = R.drawable.bilgewater_crest_icon,
        regionName = "Bilgewater"
    ),
    Lists.Region(
        imageResourceId = R.drawable.demacia_crest_icon,
        regionName = "Demacia"
    ),
    Lists.Region(
        imageResourceId = R.drawable.ionia_crest_icon,
        regionName = "Ionia"
    ),
    Lists.Region(
        imageResourceId = R.drawable.ixtal_crest_icon,
        regionName = "Ixtal"
    ),
    Lists.Region(
        imageResourceId = R.drawable.piltover_crest,
        regionName = "Piltover"
    ),
    Lists.Region(
        imageResourceId = R.drawable.shadow_isles_crest_icon,
        regionName = "Shadow Isles"
    ),
    Lists.Region(
        imageResourceId = R.drawable.shurima_crest_icon,
        regionName = "Shurima"
    ),
    Lists.Region(
        imageResourceId = R.drawable.mount_targon_crest_icon,
        regionName = "Targon"
    ),
    Lists.Region(
        imageResourceId = R.drawable.noxus_crest_icon,
        regionName = "Noxus"
    ),
    Lists.Region(
        imageResourceId = R.drawable.freljord_crest_icon,
        regionName = "The Freljord"
    ),
    Lists.Region(
        imageResourceId = R.drawable.void_crest_icon,
        regionName = "The Void"
    ),
    Lists.Region(
        imageResourceId = R.drawable.zaun_crest_icon,
        regionName = "Zaun"
    )
)
val classes = listOf(

)
val races = listOf()