package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import androidx.lifecycle.ViewModel
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        _uiState.update { currentPlayer ->
            currentPlayer.copy(playerName = name)
        }
    }

    fun setHeroRace(race: HeroRaceDesc?)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(characterRace = race ?: HeroRaceDesc.Human())
        }
    }

    fun setHeroClass(heroClass: HeroClassDesc?)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(characterClass = heroClass ?: HeroClassDesc.Rogue())
        }
    }

    fun applyHeroRaceValueSetting(subRace: SubRace?, language: String)
    {
        calculateBAseAttribute(_uiState.value.characterRace, subRace)
        _uiState.update { currentPlayer ->
            currentPlayer.copy(
                selectedSubRace = subRace,
                selectedLanguage = language,
                allLanguages = listOf(
                    currentPlayer.characterRace.fixedLanguages, language
                )
            )
        }

    }

    fun setSelectedLanguage(language: String)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(
                selectedLanguage = language
            )
        }
    }

    fun setSelectedSubrace(subrace: SubRace?)
    {
        _uiState.update { currentPalyer ->
            currentPalyer.copy(
                selectedSubRace = subrace
            )
        }
    }

    fun setSelectedSkill(skills: List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedSkill = skills
            )
        }
    }
}