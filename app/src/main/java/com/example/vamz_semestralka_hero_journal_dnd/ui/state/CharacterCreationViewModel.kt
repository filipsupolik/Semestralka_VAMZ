package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import androidx.lifecycle.ViewModel
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.Region
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
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

    fun setSelectedLanguage(language: String)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(
                playerLanguages = currentPlayer.playerLanguages.plus(language)
            )
        }
    }

    fun setStartingSpell(spell: String) {
        _uiState.update { currentState ->
            val findSpell = currentState.characterClass?.spells?.find { it.name == spell }
            currentState.copy(
                playerSpell = findSpell
            )
        }
    }

    fun setPlayerSubRace(subRace: SubRace?){
        _uiState.update { currentState ->
            currentState.copy(
                characterSubRace = subRace
            )
        }
    }

    fun setRegion(name: String) {
        _uiState.update {currentState->
            currentState.copy(
                playerRegion = Region.chooseRaceFromName(name)
            )
        }
    }

    fun resetRegion() {
        _uiState.update { currentUiState->
            currentUiState.copy(
                playerRegion = null
            )
        }
    }

    fun setPlayerSkill(label: String) {
        _uiState.update { currentState ->
            val (first, second) = currentState.playerSkill

            val updatedSkill = when {
                first.isEmpty() -> Pair(label, second)
                second.isEmpty() && label != first -> Pair(first, label)
                else -> Pair(first, second)
            }

            currentState.copy(playerSkill = updatedSkill)
        }
    }

    fun setPlayerSpell(spell: Spell) {
        _uiState.update { currentUiState->
            currentUiState.copy(
                playerSpell = spell
            )
        }
    }

    fun setRemainingStatsPoints(cost: Int) {
        _uiState.update { currentState->
            currentState.copy(
                remainingPoints = currentState.remainingPoints - cost
            )
        }
    }
}