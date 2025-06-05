package com.example.vamz_semestralka_hero_journal_dnd.data

import com.example.vamz_semestralka_hero_journal_dnd.R

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
    val extraStats: Map<RaceAttributes, Int> = emptyMap(),
    val extraTraits: List<RaceTrait> = emptyList()
)

sealed class RaceTrait(
    val name: String,
    val desc: String
) {
    class SimpleTraits(name: String, desc: String): RaceTrait(name, desc) }

data class Spell(
    val name: String,
    val level: Int,
    val school: String,
    val description: String
)

sealed class HeroRaceDesc(
    val name: String,
    val descriptionCharacterRace: String,
    val age: String,
    val size: RaceSize,
    val speed: String,
    val fixedLanguages: String,
    val availableLanguages: List<String> = emptyList(),
    val baseStats: Map<RaceAttributes, Int>,
    val baseTraits: List<RaceTrait>,
    val subraces: List<SubRace> = emptyList(),
    val descriptionForLanguageChoice: String,
    val imageRes: Int,
){
    class AscendBorn: HeroRaceDesc(
        name = "Ascend-Born",
        descriptionCharacterRace = "The Ascended-Born are those whose lineage includes an entity that has Ascended or come close to it. Living in locations with powerful ties to the Celestial Realm have also been known to cause Ascended-like abilities to manifest. While not being as powerful as an Ascended, your lineage gives you special abilities. It's not uncommon for the Ascended bloodline abilities to run dormant for generations, reemerging after being hidden for some time.\n" +
                " \n" +
                "Alternatively, if you're a descendent of one of the Darkin, the Darkin-Born variant might be more appropriate.",
        age = "Ascended born mature at a similar rate humans do, and reaching adulthood in their late teens. They can live into the middle of their second century.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = "Common",
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(RaceAttributes.CHA to 2),
        baseTraits = listOf(
            RaceTrait.SimpleTraits(name = "Darkvision", desc = "You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."),
            RaceTrait.SimpleTraits(name = "Ascended Resistance", desc = "You have resistance to necrotic damage and radiant damage."),
            RaceTrait.SimpleTraits(name = "Healing Hand", desc = "As an action, you can touch a creature and cause it to regain a number of hit points equal to your level. Once you use this trait, you can't use it again until you finish a long rest."),
            RaceTrait.SimpleTraits(name = "Light Bearer", desc = "You know the light cantrip. Charisma is your spellcasting ability for it."),
        ),
        subraces = listOf(
            SubRace(
                name = "Protector",
                extraStats = mapOf(RaceAttributes.WIS to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(name = "Radiant Soul", desc = "Starting at 3rd level, you can use your action to unleash the divine energy within yourself, causing your eyes to glimmer and two luminous, incorporeal wings to sprout from your back.\n" +
                            "Your transformation lasts for 1 minute or until you end it as a bonus action. During it, you have a flying speed of 30 feet, and once on each of your turns, you can deal extra radiant damage to one target when you deal damage to it with an attack or a spell. The extra radiant damage equals your level.\n" +
                            "Once you use this trait, you can't use it again until you finish a long rest.")
                )
            ),
            SubRace(
                name = "Scourge",
                extraStats = mapOf(RaceAttributes.CON to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(name = "Radiant Consumption", desc = "Starting at 3rd level, you can use your action to unleash the divine energy within yourself, causing a searing light to radiate from you, pour out of your eyes and mouth, and threaten to char you.\n" +
                            "Your transformation lasts for 1 minute or until you end it as a bonus action. During it, you shed bright light in a 10-foot radius and dim light for an additional 10 feet, and at the end of each of your turns, you and each creature within 10 feet of you take radiant damage equal to half your level (rounded up). In addition, once on each of your turns, you can deal extra radiant damage to one target when you deal damage to it with an attack or a spell. The extra radiant damage equals your level.\n" +
                            "Once you use this trait, you can't use it again until you finish a long rest.")
                )
            )
        ),
        imageRes = R.drawable.azir
    )

    class DarkinBorn : HeroRaceDesc(
        name = "Darkin-Born",
        descriptionCharacterRace = "The Darkin-born are a variant of Ascended-born whose lineage traces back to one of the Ascended that dabbled in Hemomancy. While their ancestor may have been corrupted by the war with the Void, by no means does that mean that they themselves have any darker inclinations. Be who you wanna be. Though, there are some areas that may not be too happy seeing a Darkin-born for historical (but still sorta racist) reasons (looking at you Demacia).",
        age = "Ascended born mature at a similar rate humans do, reaching adulthood in their late teens. They can live into the middle of their second century.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = "Common",
        descriptionForLanguageChoice = "You can speak, read, and write Common and one regional language.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(RaceAttributes.CHA to 2),
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
                extraStats = mapOf(RaceAttributes.WIS to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Darkin's Tongue",
                        desc = "You know the friends cantrip. When you reach 3rd level, you can cast the charm person spell as a 2nd-level spell once with this trait and regain the ability to do so when you finish a long rest. When you reach 5th level, you can cast the suggestion spell once with this trait and regain the ability to do so when you finish a long rest. Charisma is your spellcasting ability for these spells."
                    )
                )
            ),
            SubRace(
                name = "Hellblade",
                extraStats = mapOf(RaceAttributes.STR to 1),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Legacy of Darkins",
                        desc = "You know the thaumaturgy cantrip. When you reach 3rd level, you can cast the searing smite spell as a 2nd-level spell once with this trait and regain the ability to do so when you finish a long rest. When you reach 5th level, you can cast the branding smite spell once with this trait and regain the ability to do so when you finish a long rest. Charisma is your spellcasting ability for these spells."
                    )
                )
            )
        ),
        imageRes = R.drawable.aatrox
    )

    class Human: HeroRaceDesc(
        name = "Human",
        descriptionCharacterRace = "Humans are the dominant intelligent species of Runeterra. They are known for their diversity in appearance, skills, and magic. Humans mature at a similar rate to other races and usually live less than a century. They can adapt to nearly any environment and often interbreed with other magical lineages, inheriting various physical or magical traits.",
        age = "Humans reach adulthood in their late teens and typically live less than 100 years.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = "Common",
        descriptionForLanguageChoice = "You can speak, read, and write Common and one additional language of your choice.",
        availableLanguages = listOf("Ionian", "Ixtali", "NorthSpeak", "Noxian", "Shuriman", "Valerian"),
        baseStats = mapOf(
            RaceAttributes.STR to 1,
            RaceAttributes.DEX to 1,
            RaceAttributes.CON to 1,
            RaceAttributes.INT to 1,
            RaceAttributes.WIS to 1,
            RaceAttributes.CHA to 1,
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
                    RaceAttributes.STR to 0,
                    RaceAttributes.DEX to 0,
                    RaceAttributes.CON to 0,
                    RaceAttributes.INT to 0,
                    RaceAttributes.WIS to 0,
                    RaceAttributes.CHA to 0
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
        ),
        imageRes = R.drawable.fiora
    )

    class Vastaya : HeroRaceDesc(
        name = "Vastaya",
        descriptionCharacterRace = "Vastaya are a chimeric race of humanoids descended from the Vastayashai'rei—mystic beings who merged with the spiritual magic of Runeterra. Each tribe varies in appearance and powers, blending human and animal traits, and all possess a deep attunement to natural magic.",
        age = "Vastaya mature at a similar rate to humans, reaching adulthood in their late teens or early twenties. They can live between 150 to 200 years.",
        size = RaceSize.MEDIUM,
        speed = "30 ft. per turn",
        fixedLanguages = "Common",
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
                    RaceAttributes.CON to 2,
                    RaceAttributes.STR to 1
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
                    RaceAttributes.STR to 1,
                    RaceAttributes.CON to 1,
                    RaceAttributes.CHA to 1
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
                    RaceAttributes.DEX to 2,
                    RaceAttributes.CHA to 1
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
                    RaceAttributes.DEX to 2,
                    RaceAttributes.INT to 1
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
                    RaceAttributes.CON to 2,
                    RaceAttributes.STR to 1
                ),
                extraTraits = listOf(
                    RaceTrait.SimpleTraits(
                        name = "Shifting",
                        desc = "As a bonus action, you can shift into a more bestial form for 1 minute. You gain temporary hit points equal to your level + your Constitution modifier (minimum 1), and gain a +1 bonus to AC. You can use this feature once per short or long rest."
                    )
                )
            )
        ),
        imageRes = R.drawable.xayah
    )

    class Yordle : HeroRaceDesc(
        name = "Yordle",
        descriptionCharacterRace = "Yordles are small, mammalian bipeds originating from Bandle City, a pocket of the Spirit Realm. They are known for their jovial nature, innate magic, and adaptability. Despite their size, yordles are spirited and resilient, often playing significant roles in the world of Runeterra.",
        age = "Yordles mature at a similar rate to humans, typically reaching adulthood around 40 years of age. They can live between 200 and 300 years.",
        size = RaceSize.SMALL,
        speed = "30 ft. per turn",
        fixedLanguages = "Common",
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
                    RaceAttributes.DEX to 2,
                    RaceAttributes.CON to 1
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
                    RaceAttributes.CON to 2
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
            ),
            SubRace(
                name = "Cunning Yordle",
                extraStats = mapOf(
                    RaceAttributes.INT to 2,
                    RaceAttributes.DEX to 1
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
        ),
        imageRes = R.drawable.teemo
    )

}

sealed class HeroClassDesc(
    val name: String,
    val description: String,
    val hitDice: String,
    val savingThrows: Pair<RaceAttributes, RaceAttributes>,
    val skills: List<String>,
    val armor: String,
    val weapon: String,
    val spells: List<Spell>,
    val skillChoices: Int = 2,
    val imageRes: Int
)
{
    class Cleric: HeroClassDesc(
        name = "Cleric",
        description = "Divine spellcaster who wields holy power.",
        hitDice = "1d8 per level",
        savingThrows = Pair(RaceAttributes.WIS, RaceAttributes.CHA),
        skills = listOf("History", "Insight", "Medicine", "Persuasion", "Religion"),
        armor = "Light and Medium armor and Shields",
        weapon = "Simple weapons",
        spells = listOf(
            Spell(
                name = "Cure Wounds",
                level = 1,
                school = "Evocation",
                description = "A creature you touch regains a number of hit points equal to 1d8 + your spellcasting ability modifier. No effect on undead or constructs."
            ),
            Spell(
                name = "Bless",
                level = 1,
                school = "Enchantment",
                description = "You bless up to three creatures of your choice within range. Whenever a target makes an attack roll or a saving throw before the spell ends, the target can roll a d4 and add the number rolled to the attack roll or saving throw."
            ),
            Spell(
                name = "Guiding Bolt",
                level = 1,
                school = "Evocation",
                description = "A flash of light streaks toward a creature of your choice within range. Make a ranged spell attack against the target. On a hit, the target takes 4d6 radiant damage, and the next attack roll made against this target before the end of your next turn has advantage."
            ),
            Spell(
                name = "Shield of Faith",
                level = 1,
                school = "Abjuration",
                description = "A shimmering field appears and surrounds a creature of your choice within range, granting it a +2 bonus to AC for the duration."
            ),
            Spell(
                name = "Spiritual Weapon",
                level = 2,
                school = "Evocation",
                description = "You create a floating, spectral weapon within range that lasts for the duration or until you cast this spell again. When you cast the spell, you can make a melee spell attack against a creature within 5 feet of the weapon."
            ),
            Spell(
                name = "Prayer of Healing",
                level = 2,
                school = "Evocation",
                description = "Up to six creatures of your choice that you can see within range each regain hit points equal to 2d8 + your spellcasting ability modifier. This spell has no effect on undead or constructs."
            )
        ),
        imageRes = R.drawable.dnd_cleric_5e_subclass
    )

    class Paladin : HeroClassDesc(
        name = "Paladin",
        description = "Holy knight, sworn to uphold divine righteousness",
        hitDice = "1d10 per level",
        savingThrows = Pair(RaceAttributes.WIS, RaceAttributes.CHA),
        skills = listOf(
            "Athletics",
            "Insight",
            "Intimidation",
            "Medicine",
            "Persuasion",
            "Religion"
        ),
        armor = "Light, Medium, and Heavy armor and Shields",
        weapon = "Simple weapons, martial weapons",
        spells = listOf(
            Spell(
                name = "Divine Favor",
                level = 1,
                school = "Evocation",
                description = "Your weapon is imbued with divine energy. On hit, the target takes an extra 1d4 radiant damage."
            ),
            Spell(
                name = "Bless",
                level = 1,
                school = "Enchantment",
                description = "Up to three creatures of your choice gain a +1d4 bonus to attack rolls and saving throws for the duration."
            ),
            Spell(
                name = "Shield of Faith",
                level = 1,
                school = "Abjuration",
                description = "A shimmering field appears around a creature, giving +2 AC for up to 10 minutes."
            ),
            Spell(
                name = "Compelled Duel",
                level = 1,
                school = "Enchantment",
                description = "A creature must make a Wisdom saving throw or be drawn to fight you, having disadvantage on attacks not targeting you."
            ),
            Spell(
                name = "Cure Wounds",
                level = 1,
                school = "Evocation",
                description = "Touch a creature to restore hit points equal to 1d8 + your spellcasting modifier."
            ),
            Spell(
                name = "Wrathful Smite",
                level = 1,
                school = "Evocation",
                description = "The next time you hit a creature with a melee weapon attack, it takes an extra 1d6 psychic damage and must succeed on a Wisdom saving throw or be frightened until the spell ends."
            )
        ),
        imageRes = R.drawable.dnd_paladin_5e_dwarf
    )

    class Ranger : HeroClassDesc(
        name = "Ranger",
        description = "Master hunter, excelling at archery and nature magic",
        hitDice = "1d10 per level",
        savingThrows = Pair(RaceAttributes.STR, RaceAttributes.DEX),
        skills = listOf(
            "Animal Handling",
            "Athletics",
            "Insight",
            "Investigation",
            "Nature",
            "Perception",
            "Stealth",
            "Survival"
        ),
        armor = "Light armor, medium armor, shields",
        weapon = "Simple weapons, martial weapons",
        spells = listOf(
            Spell(
                name = "Hunter's Mark",
                level = 1,
                school = "Divination",
                description = "Mark a creature as your quarry. Deal +1d6 extra damage on weapon attacks against it. You have advantage on Perception and Survival checks to find it."
            ),
            Spell(
                name = "Cure Wounds",
                level = 1,
                school = "Evocation",
                description = "Restore 1d8 + your spellcasting modifier hit points to a creature you touch."
            ),
            Spell(
                name = "Absorb Elements",
                level = 1,
                school = "Abjuration",
                description = "Capture incoming energy from acid, cold, fire, lightning, or thunder. You gain resistance to the triggering damage and deal extra damage with your next melee attack."
            ),
            Spell(
                name = "Speak with Animals",
                level = 1,
                school = "Divination",
                description = "You gain the ability to comprehend and verbally communicate with beasts for 10 minutes."
            ),
            Spell(
                name = "Ensnaring Strike",
                level = 1,
                school = "Conjuration",
                description = "On your next weapon hit, thorny vines attempt to restrain the target. On a failed Strength save, the target is restrained and takes 1d6 piercing damage at the start of each turn."
            ),
            Spell(
                name = "Detect Magic",
                level = 1,
                school = "Divination",
                description = "Sense the presence of magic within 30 feet of you for up to 10 minutes."
            )
        ),
        imageRes = R.drawable.ranger_dnd_5e_1
    )

    class Rogue : HeroClassDesc(
        name = "Rogue",
        description = "stealthy expert in tricks, traps, and backstabs",
        hitDice = "1d8 per level",
        savingThrows = Pair(RaceAttributes.DEX, RaceAttributes.INT),
        skills = listOf(
            "Acrobatics",
            "Athletics",
            "Deception",
            "Insight",
            "Intimidation",
            "Investigation",
            "Perception",
            "Performance",
            "Persuasion",
            "Sleight of Hand",
            "Stealth"
        ),
        armor = "Light armor",
        weapon = "Simple weapons, hand crossbows, longswords, rapiers, shortswords",
        spells = listOf(
            Spell(
                name = "Mage Hand",
                level = 0,
                school = "Conjuration (Cantrip)",
                description = "Create a spectral floating hand within 30 feet that can manipulate objects, open containers, and retrieve items."
            ),
            Spell(
                name = "Minor Illusion",
                level = 0,
                school = "Illusion (Cantrip)",
                description = "Create a sound or an image of an object within range that lasts for 1 minute to distract or deceive enemies."
            ),
            Spell(
                name = "Disguise Self",
                level = 1,
                school = "Illusion",
                description = "Change your appearance and clothing for 1 hour. Physical interaction reveals the illusion."
            ),
            Spell(
                name = "Charm Person",
                level = 1,
                school = "Enchantment",
                description = "Attempt to charm a humanoid you can see. They make a Wisdom saving throw with advantage if you're fighting them."
            ),
            Spell(
                name = "Silent Image",
                level = 1,
                school = "Illusion",
                description = "Create a visual illusion no larger than a 15-ft cube. The image moves and appears real, but has no sound or interaction."
            ),
            Spell(
                name = "Shield",
                level = 1,
                school = "Abjuration",
                description = "As a reaction, gain +5 AC until the start of your next turn. Negates triggering Magic Missile."
            )
        ),
        imageRes = R.drawable.rogue_dnd_5e
    )

    class Wizard : HeroClassDesc(
        name = "Wizard",
        description = "Spellcaster who studies magic",
        hitDice = "1d6 per level",
        savingThrows = Pair(RaceAttributes.INT, RaceAttributes.WIS),
        skills = listOf(
            "Arcana",
            "History",
            "Insight",
            "Investigation",
            "Medicine",
            "Religion"
        ),
        armor = "None",
        weapon = "Daggers, darts, slings, quarterstaffs, light crossbows",
        spells = listOf(
            Spell(
                name = "Magic Missile",
                level = 1,
                school = "Evocation",
                description = "Vytvorí tri svietiace strely magickej energie, ktoré automaticky zasiahnu ciele a spôsobia 1d4 + 1 poškodenia každá. Na vyšších úrovniach pridáva ďalšie strely."
            ),
            Spell(
                name = "Mage Armor",
                level = 1,
                school = "Abjuration",
                description = "Chráni cieľ neviditeľným silovým poľom, ktoré nastaví jeho základné AC na 13 + jeho Dexterity modifier, pokiaľ nenosí brnenie."
            ),
            Spell(
                name = "Shield",
                level = 1,
                school = "Abjuration",
                description = "Reakcia, ktorá zvýši AC o 5 až do začiatku tvojho ďalšieho ťahu a neguje útoky Magic Missile."
            ),
            Spell(
                name = "Sleep",
                level = 1,
                school = "Enchantment",
                description = "Uspí tvory v oblasti s celkovým počtom životov až do 5d8. Prioritne ovplyvňuje tvory s najnižším počtom životov."
            ),
            Spell(
                name = "Detect Magic",
                level = 1,
                school = "Divination",
                description = "Na 10 minút detekuje prítomnosť mágie v 30-stopovom okruhu. Môže byť obsiahnutý ako rituál."
            ),
            Spell(
                name = "Feather Fall",
                level = 1,
                school = "Transmutation",
                description = "Reakcia, ktorá spomalí pád až piatich tvorov na 60 stôp za kolo, čím zabráni poškodeniu z pádu."
            )
        ),
        imageRes = R.drawable.wizard_dnd_5e_1
    )

    sealed class HeroRace(val name: String, val desc: HeroRaceDesc)
    {
        object AscendBorn: HeroRace("Ascend-Born", HeroRaceDesc.AscendBorn())
        object DarkinBorn: HeroRace("Darkin-Born", HeroRaceDesc.DarkinBorn())
        object Human: HeroRace("Human", HeroRaceDesc.Human())
        object Vastaya: HeroRace("Vastaya", HeroRaceDesc.Vastaya())
        object Yordle: HeroRace("Yordle", HeroRaceDesc.Yordle())

        companion object
        {
            fun chooseRaceFromName(name: String): HeroRaceDesc? = when(name){
                "Ascend-Born" -> AscendBorn.desc
                "Darkin-Born" -> DarkinBorn.desc
                "Human" -> Human.desc
                "Vastaya" -> Vastaya.desc
                "Yordle" -> Yordle.desc
                else -> null
            }
        }
    }

    sealed class HeroClass(val name: String, val desc: HeroClassDesc)
    {
        object Rogue: HeroClass("Rogue", HeroClassDesc.Rogue())
        object Paladin: HeroClass("Paladin", HeroClassDesc.Paladin())
        object Cleric: HeroClass("Cleric", HeroClassDesc.Cleric())
        object Ranger: HeroClass("Ranger", HeroClassDesc.Ranger())
        object Wizard: HeroClass("Wizard", HeroClassDesc.Wizard())

        companion object
        {
            fun chooseRaceFromName(name: String): HeroClassDesc? = when(name){
                "Rogue" -> Rogue.desc
                "Paladin" -> Paladin.desc
                "Cleric" -> Cleric.desc
                "Ranger" -> Ranger.desc
                "Wizard" -> Wizard.desc
                else -> null
            }
        }
    }

}