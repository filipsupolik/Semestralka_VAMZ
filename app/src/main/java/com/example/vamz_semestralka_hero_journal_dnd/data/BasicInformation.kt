package com.example.vamz_semestralka_hero_journal_dnd.data

enum class RaceSize{
    SMALL, MEDIUM, LARGE
}

enum class RaceAttributes(attr: String){
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
    val descriptionCharacterRace: String,
    val age: String,
    val size: RaceSize,
    val speed: String,
    val fixedLanguages: List<String>, // Always spoken (1 or 2)
    val availableLanguages: List<String> = emptyList(), // Only if player picks 1
    val baseStats: Map<String, Int>,
    val baseTraits: List<RaceTrait>,
    val subraces: List<SubRace> = emptyList(),
    descriptionForLanguageChoice: String
){
    class AscendBorn: HeroRaceDesc(
        name = "Ascend-Born",
        descriptionCharacterRace = "The Ascended-Born are those whose lineage includes an entity that has Ascended or come close to it. Living in locations with powerful ties to the Celestial Realm have also been known to cause Ascended-like abilities to manifest. While not being as powerful as an Ascended, your lineage gives you special abilities. It's not uncommon for the Ascended bloodline abilities to run dormant for generations, reemerging after being hidden for some time.\n" +
                " \n" +
                "Alternatively, if you're a descendent of one of the Darkin, the Darkin-Born variant might be more appropriate.",
        age = "Ascended born mature at a similar rate humans do, and reaching adulthood in their late teens. They can live into the middle of their second century.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
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
                extraStats = mapOf(RaceAttributes.WIS.name to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(name = "Radiant Soul", desc = "Starting at 3rd level, you can use your action to unleash the divine energy within yourself, causing your eyes to glimmer and two luminous, incorporeal wings to sprout from your back.\n" +
                            "Your transformation lasts for 1 minute or until you end it as a bonus action. During it, you have a flying speed of 30 feet, and once on each of your turns, you can deal extra radiant damage to one target when you deal damage to it with an attack or a spell. The extra radiant damage equals your level.\n" +
                            "Once you use this trait, you can't use it again until you finish a long rest.")
                )
            ),
            SubRace(
                name = "Scourge",
                extraStats = mapOf(RaceAttributes.CON.name to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(name = "Radiant Consumption", desc = "Starting at 3rd level, you can use your action to unleash the divine energy within yourself, causing a searing light to radiate from you, pour out of your eyes and mouth, and threaten to char you.\n" +
                            "Your transformation lasts for 1 minute or until you end it as a bonus action. During it, you shed bright light in a 10-foot radius and dim light for an additional 10 feet, and at the end of each of your turns, you and each creature within 10 feet of you take radiant damage equal to half your level (rounded up). In addition, once on each of your turns, you can deal extra radiant damage to one target when you deal damage to it with an attack or a spell. The extra radiant damage equals your level.\n" +
                            "Once you use this trait, you can't use it again until you finish a long rest.")
                )
            )
        )
    )

    class DarkinBorn : HeroRaceDesc(
        name = "Darkin-Born",
        descriptionCharacterRace = "The Darkin-born are a variant of Ascended-born whose lineage traces back to one of the Ascended that dabbled in Hemomancy. While their ancestor may have been corrupted by the war with the Void, by no means does that mean that they themselves have any darker inclinations. Be who you wanna be. Though, there are some areas that may not be too happy seeing a Darkin-born for historical (but still sorta racist) reasons (looking at you Demacia).",
        age = "Ascended born mature at a similar rate humans do, reaching adulthood in their late teens. They can live into the middle of their second century.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(RaceAttributes.CHA.name to 2),
        baseTraits = listOf(
            RaceTrait.SimpleTraits(
                name = "Darkvision",
                desc = "You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."
            ),
            RaceTrait.SimpleTraits(
                name = "Ascended Resistance",
                desc = "You have resistance to necrotic damage and radiant damage."
            )
        ),
        subraces = listOf(
            SubRace(
                name = "Darkin's Charm",
                extraStats = mapOf(RaceAttributes.WIS.name to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Darkin's Tongue",
                        desc = "You know the friends cantrip. When you reach 3rd level, you can cast the charm person spell as a 2nd-level spell once with this trait and regain the ability to do so when you finish a long rest. When you reach 5th level, you can cast the suggestion spell once with this trait and regain the ability to do so when you finish a long rest. Charisma is your spellcasting ability for these spells."
                    )
                )
            ),
            SubRace(
                name = "Hellblade",
                extraStats = mapOf(RaceAttributes.STR.name to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Legacy of Darkins",
                        desc = "You know the thaumaturgy cantrip. When you reach 3rd level, you can cast the searing smite spell as a 2nd-level spell once with this trait and regain the ability to do so when you finish a long rest. When you reach 5th level, you can cast the branding smite spell once with this trait and regain the ability to do so when you finish a long rest. Charisma is your spellcasting ability for these spells."
                    )
                )
            )
        )
    )

    class Human: HeroRaceDesc(
        name = "Human",
        descriptionCharacterRace = "Humans are the dominant intelligent species of Runeterra. They are known for their diversity in appearance, skills, and magic. Humans mature at a similar rate to other races and usually live less than a century. They can adapt to nearly any environment and often interbreed with other magical lineages, inheriting various physical or magical traits.",
        age = "Humans reach adulthood in their late teens and typically live less than 100 years.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        descriptionForLanguageChoice = "You can speak, read, and write Common and one additional language of your choice.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(
            RaceAttributes.STR.name to 1,
            RaceAttributes.DEX.name to 1,
            RaceAttributes.CON.name to 1,
            RaceAttributes.INT.name to 1,
            RaceAttributes.WIS.name to 1,
            RaceAttributes.CHA.name to 1,
        ),
        baseTraits = listOf(
            RaceTrait.SimpleTraits(
                name = "Human Determination",
                desc = "When you make an attack roll, ability check, or saving throw, you can choose to roll with advantage. Once you use this trait, you can't use it again until you finish a short or long rest."
            ),
            RaceTrait.SimpleTraits(
                name = "Skill Versatility",
                desc = "You gain proficiency in one skill or tool of your choice."
            )
        ),
        subraces = listOf(
            SubRace(
                name = "Variant Human",
                extraStats = mapOf(
                    RaceAttributes.STR.name to 0,
                    RaceAttributes.DEX.name to 0,
                    RaceAttributes.CON.name to 0,
                    RaceAttributes.INT.name to 0,
                    RaceAttributes.WIS.name to 0,
                    RaceAttributes.CHA.name to 0
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Ability Score Increase",
                        desc = "Increase one ability score by 2 and another ability score by 1."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Skill Versatility",
                        desc = "Gain proficiency in one skill or tool of your choice."
                    )
                )
            )
        )
    )

    class Vastaya : HeroRaceDesc(
        name = "Vastaya",
        descriptionCharacterRace = "Vastaya are a chimeric race of humanoids descended from the Vastayashai'rei—mystic beings who merged with the spiritual magic of Runeterra. Each tribe varies in appearance and powers, blending human and animal traits, and all possess a deep attunement to natural magic.",
        age = "Vastaya mature at a similar rate to humans, reaching adulthood in their late teens or early twenties. They can live between 150 to 200 years.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language of your choice.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = emptyMap(), // Attribute bonuses are defined per subrace
        baseTraits = listOf(
            RaceTrait.SimpleTraits(
                name = "Vastayan Magic",
                desc = "Thanks to your Vastayashai’rei heritage, you have an innate knack for magic. Choose one cantrip from the Sorcerer spell list. Charisma is your spellcasting ability for it."
            )
        ),
        subraces = listOf(
            SubRace(
                name = "Kiilash",
                extraStats = mapOf(
                    RaceAttributes.CON.name to 2,
                    RaceAttributes.STR.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Feline Agility",
                        desc = "Your base walking speed increases to 35 feet. You have advantage on Athletics checks when jumping."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Retractable Claws",
                        desc = "You have natural weapons in the form of claws, which deal 1d4 slashing damage."
                    )
                )
            ),
            SubRace(
                name = "Marai",
                extraStats = mapOf(
                    RaceAttributes.STR.name to 1,
                    RaceAttributes.CON.name to 1,
                    RaceAttributes.CHA.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Amphibious",
                        desc = "You can breathe both air and water."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Swim Speed",
                        desc = "You have a swimming speed of 30 feet."
                    )
                )
            ),
            SubRace(
                name = "Ottrani",
                extraStats = mapOf(
                    RaceAttributes.DEX.name to 2,
                    RaceAttributes.CHA.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Sure-Footed",
                        desc = "You have advantage on saving throws against being knocked prone and on Acrobatics checks made to balance."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Natural Performer",
                        desc = "You gain proficiency in the Performance skill."
                    )
                )
            ),
            SubRace(
                name = "Shimon",
                extraStats = mapOf(
                    RaceAttributes.DEX.name to 2,
                    RaceAttributes.INT.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Climbing",
                        desc = "You have a climbing speed of 20 feet."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Prehensile Tail",
                        desc = "You can use your tail to manipulate objects. It can’t wield weapons or tools or perform somatic components."
                    )
                )
            ),
            SubRace(
                name = "Shifter",
                extraStats = mapOf(
                    RaceAttributes.CON.name to 2,
                    RaceAttributes.STR.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Shifting",
                        desc = "As a bonus action, you can shift into a more bestial form for 1 minute. You gain temporary hit points equal to your level + your Constitution modifier (minimum 1), and gain a +1 bonus to AC. You can use this feature once per short or long rest."
                    )
                )
            )
        )
    )

    class Yordle : HeroRaceDesc(
        name = "Yordle",
        descriptionCharacterRace = "Yordles are small, mammalian bipeds originating from Bandle City, a pocket of the Spirit Realm. They are known for their jovial nature, innate magic, and adaptability. Despite their size, yordles are spirited and resilient, often playing significant roles in the world of Runeterra.",
        age = "Yordles mature at a similar rate to humans, typically reaching adulthood around 40 years of age. They can live between 200 and 300 years.",
        size = RaceSize.SMALL,
        speed = "30 ft. per turn",
        fixedLanguages = listOf("Common"),
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language of your choice.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = emptyMap(), // Attribute bonuses are defined per subrace
        baseTraits = listOf(
            RaceTrait.SimpleTraits(
                name = "Darkvision",
                desc = "Accustomed to life in the Spirit Realm and dim environments, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."
            )
        ),
        subraces = listOf(
            SubRace(
                name = "Guerilla Yordle",
                extraStats = mapOf(
                    RaceAttributes.DEX.name to 2,
                    RaceAttributes.CON.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Fury of the Small",
                        desc = "When you damage a creature with an attack or a spell and the creature's size is larger than yours, you can cause the attack or spell to deal extra damage to the creature. The extra damage equals your level. Once you use this trait, you can't use it again until you finish a short or long rest."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Move Quick",
                        desc = "Your base walking speed increases to 30 feet."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Nimble Escape",
                        desc = "You can take the Disengage or Hide action as a bonus action on each of your turns."
                    )
                )
            ),
            SubRace(
                name = "Stout Yordle",
                extraStats = mapOf(
                    RaceAttributes.CON.name to 2
                    // Additional +1 to Strength or Wisdom based on variant
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Resilient Constitution",
                        desc = "You have advantage on saving throws against poison, and you have resistance against poison damage."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Stocky Build",
                        desc = "You can ignore the Heavy trait of weapons and armor. Additionally, you have proficiency with the battleaxe, handaxe, light hammer, and warhammer."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Tool Proficiency",
                        desc = "You gain proficiency with one of the following artisan's tools of your choice: smith's tools, brewer's supplies, or mason's tools."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Weaponcunning",
                        desc = "Whenever you make an Intelligence (History) check related to the origin of a weapon, you are considered proficient in the History skill and add double your proficiency bonus to the check, instead of your normal proficiency bonus."
                    )
                )
                // Note: Stout Yordle has sub-variants: Sturdy (+1 Wisdom, Yordle Toughness) and Brawny (+1 Strength, Relentless Endurance, Yordle Armor Training)
            ),
            SubRace(
                name = "Cunning Yordle",
                extraStats = mapOf(
                    RaceAttributes.INT.name to 2,
                    RaceAttributes.DEX.name to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Trickster's Cunning",
                        desc = "You have advantage on saving throws against being charmed."
                    ),
                    RaceTrait.SimpleTraits(
                        name = "Illusory Disguise",
                        desc = "You can cast the Disguise Self spell once per day without expending a spell slot. Charisma is your spellcasting ability for this spell."
                    )
                )
            )
        )
    )

}