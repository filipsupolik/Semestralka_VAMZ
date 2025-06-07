package com.example.vamz_semestralka_hero_journal_dnd.ui.state

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroProfile
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.RaceAttributes
import com.example.vamz_semestralka_hero_journal_dnd.data.Region
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.data.StatMethod
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import com.example.vamz_semestralka_hero_journal_dnd.data.regions
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
        val newCharacter = HeroProfile(
            imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
            lvlDescription = R.string.level,
            name = _uiState.value.playerName,
            characterRace = _uiState.value.characterRace,
            characterClass = _uiState.value.characterClass,
            characterSubRace = _uiState.value.characterSubRace,
            raceStats = _uiState.value.raceStats,
            totalStatsValue = _uiState.value.totalStatsValue,
            hp = 20,
            maxHp = 20,
            attributes = _uiState.value.totalStatsValue.toMap(),
            languages = _uiState.value.playerLanguages,
            skills = _uiState.value.playerSkill,
            spell = _uiState.value.playerSpell,
            region = _uiState.value.playerRegion
        )


        _uiState.update {currentState ->
            currentState.copy(
                allCharacters = currentState.allCharacters.plus(newCharacter)
            )
        }

        Log.d("CreateChar", "All characters: ${_uiState.value.allCharacters}")
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

    fun increaseHp() {
        val currentHero = getChosenHeroProfile(_uiState.value.selectedPlayerName)
        currentHero?.copy(
            hp = if (currentHero.hp >= currentHero.maxHp) currentHero.maxHp else currentHero.hp + 1
        )

        updateHero(currentHero!!)
    }

    fun decreaseHp() {
        val currentHero = getChosenHeroProfile(_uiState.value.selectedPlayerName)
        currentHero?.copy(
            hp = if (currentHero.hp <= 0) currentHero.maxHp else currentHero.hp - 1
        )

        updateHero(currentHero!!)
    }

    fun deleteCharacterFromList(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                allCharacters = currentState.allCharacters.filterNot { it.name == name },
                selectedPlayerName = ""
            )
        }
    }

    fun setSelectedPlayerName(name: String){
        _uiState.update {
            it.copy(
                selectedPlayerName = name
            )
        }
    }

    fun reset() {
        _uiState.update {
            it.copy(
                playerName= "",
                characterRace = HeroRaceDesc.Human(),
                characterClass = HeroClassDesc.Paladin(),
                characterSubRace = null,
                playerRegion= Region.Ionia,
                playerLanguages= emptyList(),
                playerXP= 0,
                playerXPToNextLvl= 100,

                playerSkill = "" to "",
                listOfRegions= regions,

                playerSpell = null,

                selectedPlayerName= "",
                selectedLanguage= "",
                selectedSubRace = null,
                selectedMethodStatsCounting= StatMethod.ROLL,

                remainingPoints= 27,

                baseValue= mutableMapOf(
                    RaceAttributes.STR to 0,
                    RaceAttributes.CHA to 0,
                    RaceAttributes.WIS to 0,
                    RaceAttributes.CON to 0,
                    RaceAttributes.INT to 0,
                    RaceAttributes.DEX to 0,
            ),

            raceStats = mutableMapOf(
                RaceAttributes.STR to 0,
                RaceAttributes.CHA to 0,
                RaceAttributes.WIS to 0,
                RaceAttributes.CON to 0,
                RaceAttributes.INT to 0,
                RaceAttributes.DEX to 0,
            ),
            totalStatsValue = mutableMapOf(
                RaceAttributes.STR to 0,
                RaceAttributes.CHA to 0,
                RaceAttributes.WIS to 0,
                RaceAttributes.CON to 0,
                RaceAttributes.INT to 0,
                RaceAttributes.DEX to 0,
            ),
            )
        }
    }

    fun setOpenDrawer(init: Boolean) {
        _uiState.update {
            it.copy(
                openDrawer = init
            )
        }
    }

    fun getChosenHeroProfile(name: String): HeroProfile?{
        return _uiState.value.allCharacters.find { it.name == name }
    }

    /*
    * Metoda s ktorou mi pomahal ChatGPT opravit problem,
    * metoda je pozmenena mnou aby robila to co som potreboval spravne
    */
    fun updateHero(heroToUpdate: HeroProfile) {
        val updatedList = _uiState.value.allCharacters.map { hero ->
            if (hero.name == heroToUpdate.name) heroToUpdate else hero
        }
        _uiState.update {
            it.copy(
                allCharacters = updatedList
            )
        }
    }

    fun setSelectedMethod(method: StatMethod) {
        _uiState.update {
            it.copy(
                selectedMethodStatsCounting = method
            )
        }
    }
}