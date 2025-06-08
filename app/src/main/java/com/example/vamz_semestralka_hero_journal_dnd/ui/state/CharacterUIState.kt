package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.Lists
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.Region
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.data.StatMethod
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import com.example.vamz_semestralka_hero_journal_dnd.data.regions

/**
 * Trieda ktora uchovava stav celej aplikacie
 * Drzi informaccie o postave ktoru vytvaram ako aj o otvoreni/zatvoreni dropdown menu
 */

data class CharacterUIState(
    val playerName: String = "",
    val characterRace: HeroRaceDesc = HeroRaceDesc.Human(),
    val characterClass: HeroClassDesc = HeroClassDesc.Paladin(),
    val characterSubRace: SubRace? = null,
    val playerRegion: Region = Region.Ionia,
    val playerLanguages: List<String> = emptyList(),
    val playerXP: Int = 0,
    val playerXPToNextLvl: Int = 100,

    val playerSkill: Pair<String, String> = "" to "",
    val allCharacters: List<HeroProfile> = emptyList(),
    val listOfRegions: List<Lists.Region> = regions,

    val playerSpell: Spell? = null,

    val selectedPlayerName:String = "",
    val selectedLanguage: String = "",
    val selectedSubRace: SubRace? = null,
    val selectedMethodStatsCounting: StatMethod = StatMethod.ROLL,

    var remainingPoints: Int = 27,

    val baseValue: Map<RaceAttributes, Int> = mutableMapOf(
        RaceAttributes.STR to 0,
        RaceAttributes.CHA to 0,
        RaceAttributes.WIS to 0,
        RaceAttributes.CON to 0,
        RaceAttributes.INT to 0,
        RaceAttributes.DEX to 0,
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

    val descriptionDialogSpell: Spell? = null,
    val expanded: Boolean = false
)
