package com.example.vamz_semestralka_hero_journal_dnd.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vamz_semestralka_hero_journal_dnd.R

sealed class Lists{
    data class Region(
        @DrawableRes val imageResourceId: Int,
        val regionName: String,
    ):Lists()
    data class HeroClasses(
        @DrawableRes val imageResourceId: Int,
        val className: String
    ):Lists()
    data class HeroRace(
        @DrawableRes val iconImageResourceId: Int,
        val raceName: String
    ):Lists()
}

 data class HeroProfile(
    @DrawableRes val imageResourceId: Int,
    @StringRes val lvlDescription: Int,
    val name: String,
    val characterRace: HeroRaceDesc,
    val characterClass: HeroClassDesc,
    val characterSubRace: SubRace?,
    val raceStats: Map<RaceAttributes, Int>,
    val totalStatsValue: Map<RaceAttributes, Int>,
    val hp: Int,
    val maxHp: Int,
    val attributes: Map<RaceAttributes, Int>,
    val languages: List<String>,
    val skills: Pair<String, String>,
    val spell: Spell? = null,
    val region: Region
)

val regions = listOf(

    Lists.Region(
        imageResourceId = R.drawable.bandle_city_crest_icon,
        regionName = "Bandle City",
    ),
    Lists.Region(
        imageResourceId = R.drawable.bilgewater_crest_icon,
        regionName = "Bilgewater",
    ),
    Lists.Region(
        imageResourceId = R.drawable.demacia_crest_icon,
        regionName = "Demacia",
    ),
    Lists.Region(
        imageResourceId = R.drawable.ionia_crest_icon,
        regionName = "Ionia",
    ),
    Lists.Region(
        imageResourceId = R.drawable.ixtal_crest_icon,
        regionName = "Ixtal",
    ),
    Lists.Region(
        imageResourceId = R.drawable.piltover_crest,
        regionName = "Piltover & Zaun",
    ),
    Lists.Region(
        imageResourceId = R.drawable.shadow_isles_crest_icon,
        regionName = "Shadow Isles",
    ),
    Lists.Region(
        imageResourceId = R.drawable.shurima_crest_icon,
        regionName = "Shurima",
    ),
    Lists.Region(
        imageResourceId = R.drawable.mount_targon_crest_icon,
        regionName = "Targon",
    ),
    Lists.Region(
        imageResourceId = R.drawable.noxus_crest_icon,
        regionName = "Noxus",
    ),
    Lists.Region(
        imageResourceId = R.drawable.freljord_crest_icon,
        regionName = "The Freljord",
    ),
    Lists.Region(
        imageResourceId = R.drawable.void_crest_icon,
        regionName = "The Void",
    )
)
val classes = listOf(
    Lists.HeroClasses(
        imageResourceId = R.drawable.dnd_cleric_5e_subclass,
        className = "Cleric"
    ),
    Lists.HeroClasses(
        imageResourceId = R.drawable.dnd_paladin_5e_dwarf,
        className = "Paladin"
    ),
    Lists.HeroClasses(
        imageResourceId = R.drawable.ranger_dnd_5e_1,
        className = "Ranger"
    ),
    Lists.HeroClasses(
        imageResourceId = R.drawable.rogue_dnd_5e,
        className = "Rogue"
    ),
    Lists.HeroClasses(
        imageResourceId = R.drawable.wizard_dnd_5e_1,
        className = "Wizard"
    )
)
val races = listOf(
    Lists.HeroRace(
        iconImageResourceId = R.drawable.azir,
        raceName = "Ascend Born"
    ),
    Lists.HeroRace(
        iconImageResourceId = R.drawable.aatrox,
        raceName = "Darkin-Born"
    ),
    Lists.HeroRace(
        iconImageResourceId = R.drawable.fiora,
        raceName = "Human"
    ),
    Lists.HeroRace(
        iconImageResourceId = R.drawable.xayah,
        raceName = "Vastaya"
    ),
    Lists.HeroRace(
        iconImageResourceId = R.drawable.teemo,
        raceName = "Yordle"
    )
)