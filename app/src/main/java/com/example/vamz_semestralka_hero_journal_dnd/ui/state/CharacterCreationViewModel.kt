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

    /**
     * Vypočíta základné atribúty na základe rasy a subrasy a uloží ich do stavu.
     */
    fun calculateBAseAttribute(race: HeroRaceDesc, subRace: SubRace?) {
        for ((attr, value) in race.baseStats) {
            totalRaceStats[attr] = totalRaceStats[attr]?.plus(value) ?: value
        }

        if (subRace != null) {
            for ((attr, bonus) in subRace.extraStats) {
                totalRaceStats[attr] = totalRaceStats[attr]?.plus(bonus) ?: bonus
            }
        }

        _uiState.value = _uiState.value.copy(raceStats = totalRaceStats)
    }

    /**
     * Nastaví meno hráča.
     */
    fun setName(name: String) {
        _uiState.update { it.copy(playerName = name) }
    }

    /**
     * Nastaví rasu postavy podľa jej názvu.
     */
    fun setHeroRace(raceName: String) {
        _uiState.update {
            it.copy(characterRace = HeroClassDesc.HeroRace.chooseRaceFromName(raceName) ?: HeroRaceDesc.Human())
        }
    }

    /**
     * Nastaví triedu postavy podľa jej názvu.
     */
    fun setHeroClass(className: String) {
        _uiState.update {
            it.copy(characterClass = HeroClassDesc.HeroClass.chooseRaceFromName(className) ?: HeroClassDesc.Rogue())
        }
    }

    /**
     * Pridá jazyk do zoznamu jazykov hráča (okrem pevných jazykov rasy).
     */
    fun setPlayerLanguage(language: String) {
        _uiState.update {
            it.copy(playerLanguages = it.playerLanguages + it.characterRace.fixedLanguages + language)
        }
    }

    /**
     * Nastaví začiatočné kúzlo hráča.
     */
    fun setStartingSpell(spell: String) {
        _uiState.update {
            val findSpell = it.characterClass.spells.find { s -> s.name == spell }
            it.copy(playerSpell = findSpell)
        }
    }

    /**
     * Nastaví subrasu postavy.
     */
    fun setPlayerSubRace(subRace: SubRace?) {
        _uiState.update { it.copy(characterSubRace = subRace) }
    }

    /**
     * Nastaví región pôvodu hráča.
     */
    fun setRegion(name: String) {
        _uiState.update { it.copy(playerRegion = Region.chooseRegionFromName(name)) }
    }

    /**
     * Resetuje región hráča na predvolený (Ionia).
     */
    fun resetRegion() {
        _uiState.update { it.copy(playerRegion = Region.Ionia) }
    }

    /**
     * Nastaví schopnosti hráča – maximálne dve.
     */
    fun setPlayerSkill(label: String) {
        _uiState.update {
            val (first, second) = it.playerSkill
            val updatedSkill = when {
                first.isEmpty() -> label to second
                second.isEmpty() && label != first -> first to label
                else -> first to second
            }
            it.copy(playerSkill = updatedSkill)
        }
    }

    /**
     * Nastaví kúzlo hráča.
     */
    fun setPlayerSpell(spell: Spell) {
        _uiState.update { it.copy(playerSpell = spell) }
    }

    /**
     * Aktualizuje zostávajúce body pre systém bodového nákupu.
     */
    fun setRemainingStatsPoints(cost: Int) {
        _uiState.update { it.copy(remainingPoints = it.remainingPoints - cost) }
    }

    /**
     * Pridá aktuálne vytvorenú postavu do zoznamu postáv.
     */
    fun addCreatedCharacterToList() {
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

        _uiState.update {
            it.copy(allCharacters = it.allCharacters + newCharacter)
        }

        Log.d("CreateChar", "All characters: ${_uiState.value.allCharacters}")
    }

    /**
     * Nastaví vybraný jazyk v rozhraní.
     */
    fun setSelectedLanguage(language: String) {
        _uiState.update { it.copy(selectedLanguage = language) }
    }

    /**
     * Nastaví vybranú subrasu v rozhraní.
     */
    fun setSelectedSubrace(subrace: SubRace?) {
        _uiState.update { it.copy(selectedSubRace = subrace) }
    }

    /**
     * Nastaví základné hodnoty atribútov na rovnakú hodnotu.
     */
    fun setBaseValues(value: Int) {
        _uiState.update {
            it.copy(baseValue = it.baseValue.mapValues { value })
        }
    }

    /**
     * Nastaví konečnú hodnotu konkrétneho atribútu (základ + bonus).
     */
    fun setTotalStat(attribute: RaceAttributes, value: Int) {
        _uiState.update {
            it.copy(totalStatsValue = it.totalStatsValue.toMutableMap().apply { this[attribute] = value })
        }
    }

    /**
     * Aktualizuje základnú hodnotu konkrétneho atribútu.
     */
    fun updateBaseValue(attribute: RaceAttributes, value: Int) {
        _uiState.update {
            it.copy(baseValue = it.baseValue.toMutableMap().apply { this[attribute] = value })
        }
    }

    /**
     * Resetuje atribúty na nulové hodnoty a vráti dostupné body na 27.
     */
    fun resetAbilities() {
        _uiState.update {
            it.copy(
                baseValue = it.baseValue.mapValues { 0 },
                totalStatsValue = it.totalStatsValue.mapValues { 0 }.toMutableMap(),
                remainingPoints = 27
            )
        }
    }

    /**
     * Zvýši HP vybranej postavy (ak ešte nedosiahla maximum).
     */
    fun increaseHp() {
        val currentHero = getChosenHeroProfile(_uiState.value.selectedPlayerName)
        currentHero?.copy(hp = if (currentHero.hp >= currentHero.maxHp) currentHero.maxHp else currentHero.hp + 1)
            ?.let { updateHero(it) }
    }

    /**
     * Zníži HP vybranej postavy (ak má viac ako 0).
     */
    fun decreaseHp() {
        val currentHero = getChosenHeroProfile(_uiState.value.selectedPlayerName)
        currentHero?.copy(hp = if (currentHero.hp <= 0) 0 else currentHero.hp - 1)
            ?.let { updateHero(it) }
    }

    /**
     * Vymaže postavu zo zoznamu podľa jej mena.
     */
    fun deleteCharacterFromList(name: String) {
        _uiState.update {
            it.copy(
                allCharacters = it.allCharacters.filterNot { it.name == name },
                selectedPlayerName = ""
            )
        }
    }

    /**
     * Nastaví meno vybranej postavy.
     */
    fun setSelectedPlayerName(name: String) {
        _uiState.update { it.copy(selectedPlayerName = name) }
    }

    /**
     * Resetuje všetky hodnoty v stave späť na predvolené.
     */
    fun reset() {
        _uiState.update {
            it.copy(
                playerName = "",
                characterRace = HeroRaceDesc.Human(),
                characterClass = HeroClassDesc.Paladin(),
                characterSubRace = null,
                playerRegion = Region.Ionia,
                playerLanguages = emptyList(),
                playerXP = 0,
                playerXPToNextLvl = 100,
                playerSkill = "" to "",
                listOfRegions = regions,
                playerSpell = null,
                selectedPlayerName = "",
                selectedLanguage = "",
                selectedSubRace = null,
                selectedMethodStatsCounting = StatMethod.ROLL,
                remainingPoints = 27,
                baseValue = mutableMapOf(
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

    /**
     * Získa profil postavy podľa mena.
     */
    fun getChosenHeroProfile(name: String): HeroProfile? {
        return _uiState.value.allCharacters.find { it.name == name }
    }

    /**
     * Aktualizuje údaje konkrétnej postavy v zozname.
     */
    fun updateHero(heroToUpdate: HeroProfile) {
        val updatedList = _uiState.value.allCharacters.map {
            if (it.name == heroToUpdate.name) heroToUpdate else it
        }
        _uiState.update { it.copy(allCharacters = updatedList) }
    }

    /**
     * Nastaví metódu počítania štatistík (napr. ROLL, POINT_BUY, STANDARD_ARRAY).
     */
    fun setSelectedMethod(method: StatMethod) {
        _uiState.update { it.copy(selectedMethodStatsCounting = method) }
    }

    /**
     * Nastaví kúzlo pre zobrazenie v dialógu s popisom.
     */
    fun setDescriptionSpell(spell: Spell?) {
        _uiState.update { it.copy(descriptionDialogSpell = spell) }
    }

    /**
     * Nastaví stav rozbalovacieho menu (rozbalené/zbalené).
     */
    fun setStateForDropDownMenu(state: Boolean) {
        _uiState.update { it.copy(expanded = state) }
    }
}
