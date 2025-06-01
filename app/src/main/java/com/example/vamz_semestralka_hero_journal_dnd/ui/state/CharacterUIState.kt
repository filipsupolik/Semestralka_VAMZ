package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace

data class CharacterUIState(
    val playerName: String = "",
    val characterRace: HeroRaceDesc = HeroRaceDesc.Human(),
    val characterClass: HeroClassDesc? = null,

    val selectedSpells: List<Spell> = emptyList(),
    val selectedSkill: List<String> = emptyList(),
    val selectedLanguage: String = "",
    val allLanguages: List<String> = emptyList(),
    val selectedSubRace: SubRace? = null,

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
