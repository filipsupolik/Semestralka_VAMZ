package com.example.vamz_semestralka_hero_journal_dnd.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.vamz_semestralka_hero_journal_dnd.R

enum class RaceSize{
    SMALL, MEDIUM, LARGE
}

enum class RaceAttributes(name: String){
    STR("STR"),
    DEX("DEX"),
    CON("CON"),
    INT("INT"),
    WIS("WIS"),
    CHA("CHA")
}

data class SubRace(
    val name: String,
    val extraStats: Map<String, Int> = emptyMap(),
    val extraTraits: List<RaceTrait> = emptyList()
)

sealed class RaceTrait(
    val name: String,
    val desc: String
) {
    class SimpleTraits(name: String, desc: String): RaceTrait(name, desc)
    class ChoiceTrats(
        name: String,
        desc: String,
        val options: List<String>,
        val selectedOption: String? = null
    ): RaceTrait(name, desc)
}

sealed class HeroRaceDesc(
    val name: String,
    val age: String,
    val size: RaceSize,
    val speed: String,
    val fixedLanguages: List<String>, // Always spoken (1 or 2)
    val availableLanguages: List<String> = emptyList(), // Only if player picks 1
    val baseStats: Map<String, Int>,
    val baseTraits: List<RaceTrait>,
    val subraces: List<SubRace> = emptyList()
){
    class AscendBorn: HeroRaceDesc(
        name = "Ascend-Born",
        age = "Ascended born mature at a similar rate humans do, and reaching adulthood in their late teens. They can live into the middle of their second century.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        availableLanguages = listOf("Common", "Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(RaceAttributes.CHA.name to 2),
        baseTraits = listOf(
            RaceTrait.SimpleTraits(name = "Darkvision", desc = "You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."),
            RaceTrait.SimpleTraits(name = "Ascended Resistance", desc = "You have resistance to necrotic damage and radiant damage."),
            RaceTrait.SimpleTraits(name = "Healing Hand", desc = "As an action, you can touch a creature and cause it to regain a number of hit points equal to your level. Once you use this trait, you can't use it again until you finish a long rest."),
            RaceTrait.SimpleTraits(name = "Light Bearer", desc = "You know the light cantrip. Charisma is your spellcasting ability for it."),
        ),
        subraces = listOf(
            SubRace(
                name = "Protector",
                extraStats = mapOf(RaceAttributes.WIS.name to 1)
                )
        )
    )
}


sealed class Lists{
    data class Region(
        @DrawableRes val imageResourceId: Int,
        val regionName: String
    ):Lists()
    data class HeroClasses(
        @DrawableRes val imageResourceId: Int,
        val className: String
    ):Lists()
    data class HeroRace(
        @DrawableRes val iconImageResourceId: Int,
        val raceName: String
    ):Lists()
}

data class HeroProfile(
    @DrawableRes val imageResourceId: Int,
    @StringRes val lvlDescription: Int,
    val name: String,
    val descriptionCharacterRace: String,
    val descriptionCharacterClass: String,
    val lvl: Int
)

val characters = listOf(
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jinx",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Hunter",
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jhin",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Hunter",
        lvlDescription = R.string.level,
        lvl = 1
    ),
    HeroProfile(
        imageResourceId = R.drawable._03017_avatar_default_head_person_unknown_icon,
        name = "Jax",
        descriptionCharacterRace = "Human",
        descriptionCharacterClass = "Monk",
        lvlDescription = R.string.level,
        lvl = 1
    ),
)

val regions = listOf(
    Lists.Region(
        imageResourceId = R.drawable.bandle_city_crest_icon,
        regionName = "Bandle City"
    ),
    Lists.Region(
        imageResourceId = R.drawable.bilgewater_crest_icon,
        regionName = "Bilgewater"
    ),
    Lists.Region(
        imageResourceId = R.drawable.demacia_crest_icon,
        regionName = "Demacia"
    ),
    Lists.Region(
        imageResourceId = R.drawable.ionia_crest_icon,
        regionName = "Ionia"
    ),
    Lists.Region(
        imageResourceId = R.drawable.ixtal_crest_icon,
        regionName = "Ixtal"
    ),
    Lists.Region(
        imageResourceId = R.drawable.piltover_crest,
        regionName = "Piltover"
    ),
    Lists.Region(
        imageResourceId = R.drawable.shadow_isles_crest_icon,
        regionName = "Shadow Isles"
    ),
    Lists.Region(
        imageResourceId = R.drawable.shurima_crest_icon,
        regionName = "Shurima"
    ),
    Lists.Region(
        imageResourceId = R.drawable.mount_targon_crest_icon,
        regionName = "Targon"
    ),
    Lists.Region(
        imageResourceId = R.drawable.noxus_crest_icon,
        regionName = "Noxus"
    ),
    Lists.Region(
        imageResourceId = R.drawable.freljord_crest_icon,
        regionName = "The Freljord"
    ),
    Lists.Region(
        imageResourceId = R.drawable.void_crest_icon,
        regionName = "The Void"
    ),
    Lists.Region(
        imageResourceId = R.drawable.zaun_crest_icon,
        regionName = "Zaun"
    )
)
val classes = listOf(

)
val races = listOf(
    Lists.HeroRace(

    )
)