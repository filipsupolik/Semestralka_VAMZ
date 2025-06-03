package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
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

    val allSkillsOfHero: List<String> = emptyList(),
    val selectedLanguage: String = "",
    val selectedSkill: String = "",
    val selectedSpell: Spell? = null,
    val allLanguages: List<String> = emptyList(),
    val selectedRace: HeroRaceDesc? = null,
    val selectedSubRace: SubRace? = null,
    val selectedRegion: Region? = null,

    val selectedMethodStatsCounting: StatMethod = StatMethod.ROLL,
    val remainingPoints: Int = 27,
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
    val totalHP: Int = 20
)
