package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import androidx.lifecycle.ViewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
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

    fun setHeroRace(raceName: String)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(characterRace = HeroClassDesc.HeroRace.chooseRaceFromName(raceName) ?: HeroRaceDesc.Human())
        }
    }

    fun setHeroClass(className: String)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(characterClass = HeroClassDesc.HeroClass.chooseRaceFromName(className) ?: HeroClassDesc.Rogue())
        }
    }

    fun setPlayerLanguage(language: String)
    {
        _uiState.update { currentPlayer ->
            currentPlayer.copy(
                playerLanguages = currentPlayer.playerLanguages.plus(
                    currentPlayer.characterRace.fixedLanguages
                ).plus(language)
            )
        }
    }

    fun setStartingSpell(spell: String) {
        _uiState.update { currentState ->
            val findSpell = currentState.characterClass.spells.find { it.name == spell }
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
                playerRegion = Region.chooseRegionFromName(name)
            )
        }
    }

    fun resetRegion() {
        _uiState.update { currentUiState->
            currentUiState.copy(
                playerRegion = Region.Ionia
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

    fun addCreatedCharacterToList(){
        _uiState.update {currentState ->
            currentState.copy(
                allCharacters = currentState.allCharacters.plus(HeroProfile(
                    imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
                    lvlDescription = R.string.level,
                    name = currentState.playerName,
                    descriptionCharacterRace = currentState.characterRace.name,
                    descriptionCharacterClass = currentState.characterClass.name,
                    lvl = currentState.playerLevel
                ))
            )
        }
    }

    fun setSelectedLanguage(language: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedLanguage = language
            )
        }
    }

    fun setSelectedSubrace(subrace: SubRace?) {
        _uiState.update {currentState ->
            currentState.copy(
                selectedSubRace = subrace
            )
        }
    }

    fun setBaseValues(value: Int){
        _uiState.update {currentState ->
            currentState.copy(
                baseValue = currentState.baseValue.mapValues { value }
            )
        }
    }

    fun setTotalStat(attribute: RaceAttributes, value: Int) {
        _uiState.update { current ->
            current.copy(
                totalStatsValue = current.totalStatsValue.toMutableMap().apply {
                    this[attribute] = value
                }
            )
        }
    }

    fun updateBaseValue(attribute: RaceAttributes, value: Int) {
        _uiState.update { current ->
            current.copy(
                baseValue = current.baseValue.toMutableMap().apply {
                    this[attribute] = value
                }
            )
        }
    }

    fun resetAbilities() {
        _uiState.update { currentState ->
            currentState.copy(
                baseValue = currentState.baseValue.mapValues { 0 },
                totalStatsValue = currentState.totalStatsValue.mapValues { 0 }.toMutableMap(),
                remainingPoints = 27
            )

        }
    }

    fun addExperience(amount: Int) {
        _uiState.update { currentState ->
            var newXP = currentState.playerXP + amount
            var newLevel = currentState.playerLevel
            var xpToNext = currentState.playerXPToNextLvl

            while (newXP >= xpToNext) {
                newXP -= xpToNext
                newLevel++
                xpToNext += 50
            }

            currentState.copy(
                playerXP = newXP,
                playerLevel = newLevel,
                playerXPToNextLvl = xpToNext
            )
        }
    }

    fun incrementTempXp() {
        _uiState.update { currentState ->
            currentState.copy(
                XpToAdd = currentState.XpToAdd + 1
            )
        }
    }

    fun decrementTempXp() {
        _uiState.update { currentState ->
            currentState.copy(
                XpToAdd = if (currentState.XpToAdd <= 0) 0 else currentState.XpToAdd - 1
            )
        }
    }

    fun increaseHp() {
        _uiState.update { currentState ->
            currentState.copy(
                currentHP = if (currentState.currentHP >= currentState.totalHP) currentState.totalHP else currentState.currentHP + 1
            )
        }
    }

    fun decreaseHp() {
        _uiState.update { currentState ->
            currentState.copy(
                XpToAdd = if (currentState.XpToAdd <= 0) 0 else currentState.XpToAdd - 1
            )
        }
    }


}