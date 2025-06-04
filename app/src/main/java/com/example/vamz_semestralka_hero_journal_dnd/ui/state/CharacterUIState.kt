package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.Region
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import com.example.vamz_semestralka_hero_journal_dnd.ui.StatMethod

data class CharacterUIState(
    val playerName: String = "",
    val characterRace: HeroRaceDesc = HeroRaceDesc.Human(),
    val characterClass: HeroClassDesc? = null,
    val characterSubRace: SubRace? = null,
    val playerRegion: Region? = null,
    val playerLanguages: List<String> = emptyList(),

    val characterList: HeroProfile? = null,
    val playerSkill: Pair<String, String> = "" to "",

    val playerSpell: Spell? = null,

    val selectedRegion: Region? = null,
    val selectedLanguage: String = "",
    val selectedSubRace: SubRace? = null,
    val selectedMethodStatsCounting: StatMethod = StatMethod.ROLL,

    var remainingPoints: Int = 27,

    val baseValue: Map<RaceAttributes, Int> = mutableMapOf(
        RaceAttributes.STR to 3,
        RaceAttributes.CHA to 3,
        RaceAttributes.WIS to 3,
        RaceAttributes.CON to 3,
        RaceAttributes.INT to 3,
        RaceAttributes.DEX to 3,
    ),

    val raceStats: MutableMap<RaceAttributes, Int> = mutableMapOf(
        RaceAttributes.STR to 0,
        RaceAttributes.CHA to 0,
        RaceAttributes.WIS to 0,
        RaceAttributes.CON to 0,
        RaceAttributes.INT to 0,
        RaceAttributes.DEX to 0,
    ),
    val totalStatsValue: MutableMap<RaceAttributes, Int> = mutableMapOf(
        RaceAttributes.STR to 0,
        RaceAttributes.CHA to 0,
        RaceAttributes.WIS to 0,
        RaceAttributes.CON to 0,
        RaceAttributes.INT to 0,
        RaceAttributes.DEX to 0,
    ),
    val totalHP: Int = 20,
    val playerLevel: Int = 1,
)
