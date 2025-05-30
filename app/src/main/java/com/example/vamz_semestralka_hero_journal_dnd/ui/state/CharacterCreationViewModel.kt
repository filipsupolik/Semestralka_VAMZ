package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import androidx.lifecycle.ViewModel
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterCreationViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CharacterUIState())
    val uiState: StateFlow<CharacterUIState> = _uiState.asStateFlow()

    private var totalRaceStats: MutableMap<RaceAttributes, Int> = mutableMapOf()
    fun calculateBAseAttribute(race: HeroRaceDesc, subRace: SubRace?)
    {

        for ((attr, value) in race.baseStats) {
            totalRaceStats[attr] = totalRaceStats[attr]?.plus(value) ?: value
        }

        if (subRace != null) {
            for ((attr, bonus) in subRace.extraStats) {
                totalRaceStats[attr] = totalRaceStats[attr]?.plus(bonus) ?: bonus
            }
        }

        _uiState.value = _uiState.value.copy(
            raceStats = totalRaceStats
        )
    }

    fun setName(name: String)
    {
        _uiState.value.copy(
            playerName = name
        )
    }

    fun setHeroRace(race: HeroRaceDesc)
    {
        _uiState.value.copy(
            characterRace = race
        )
    }

    fun applyHeroRaceValueSetting(subRace: SubRace?, language: String)
    {
        calculateBAseAttribute(_uiState.value.characterRace, subRace)
        _uiState.value.copy(
            selectedSubRace = subRace,
            selectedLanguages = _uiState.value.selectedLanguages
                .plus(_uiState.value.characterRace.fixedLanguages)
                .plus(language)
        )

    }

    fun setCharacterClass(characterClass: HeroClassDesc)
    {
        _uiState.value.copy(

        )
    }


}