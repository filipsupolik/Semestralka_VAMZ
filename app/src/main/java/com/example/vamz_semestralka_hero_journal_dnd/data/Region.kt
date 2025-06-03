package com.example.vamz_semestralka_hero_journal_dnd.data

sealed class Region(val regionName: String, val regionDescription: String)
{
    object BandelCity:Region(
    regionName = "Bandle City",
    regionDescription = "In Bandle City, it is said that every sensation is heightened for non-yordles. Colors are brighter. Food and drink intoxicates the senses for years and, once tasted, will never be forgotten. The sunlight is eternally golden, the waters crystal clear, and every harvest brings a fruitful bounty. Perhaps some of these claims are true, or maybe none—for no two taletellers ever seem to agree on what they actually saw."
    )
    object Bilgewater:Region(
    regionName = "Bilgewater",
    regionDescription = "Bilgewater is a port city like no other—home to monster hunters, dock-gangs, indigenous peoples, and traders from across the known world. Almost anything can be purchased here, from outlawed hextech to the favor of local crime lords. There is no better place to seek fame and fortune, though death lurks in every alleyway, and the law is almost non-existent."
    )
    object Demacia: Region(
    regionName = "Demacia",
    regionDescription = "Demacia is a proud, lawful kingdom with a prestigious military history. Founded as a refuge from magic after the Rune Wars, some might suggest that the golden age of Demacia has passed, unless it proves able to adapt to a changing world. Self-sufficient and agrarian, its society is inherently defensive and insular, valuing justice, honor, and duty above all else."
    )
    object Ionia: Region(
    regionName = "Ionia",
    regionDescription = "Known as the First Lands, Ionia is an island continent of natural beauty and magic. Its inhabitants, living in loosely collected provinces, are a spiritual people, seeking harmony with the world. They remained largely neutral until their land was invaded by Noxus—this brutal occupation forced Ionia to reassess its place in the world, and its future path remains undetermined."
    )
    object Ixtal:Region(
    regionName = "Ixtal",
    regionDescription = "Secluded deep in the wilderness of eastern Shurima, the sophisticated arcology-city of Ixaocan remains mostly free of outside influence. Having witnessed from afar the ruination of the Blessed Isles, and the softening of Buhru culture, the Ixtali view the other factions of Runeterra as little more than upstarts and pretenders, and use their powerful elemental magic to keep any intruders at bay."
    )
    object PiltoverZaun: Region(
    regionName = "Piltover & Zaun",
    regionDescription = "Dual city-states that control the major trade routes between Valoran and Shurima. Home both to visionary inventors and their wealthy patrons, the divide between social classes is becoming more dangerous."
    )
    object ShadowIsles: Region(
    regionName = "Shadow Isles",
    regionDescription = "The Shadow Isles were once a beautiful realm, long since shattered by a magical cataclysm. Now, Black Mist permanently shrouds the land, tainting and corrupting with its malevolent sorcery. Those who perish within it are condemned to become part of it for all eternity… and worse still, each year the Mist extends its grasp to reap more souls across Runeterra."
    )
    object Shurima: Region(
    regionName = "Shurima",
    regionDescription = "Shurima was once a thriving civilization that spanned the southern continent, left in ruins by the fall of its god-emperor. Over millennia, tales of its former glory became myth and ritual. Now, its nomadic inhabitants eke out a life in the deserts, or turn to mercenary work. Still, some dare to dream of a return to the old ways."
    )
    object Targon: Region(
    regionName = "Targon",
    regionDescription = "A mountainous and sparsely inhabited region to the west of Shurima, Targon boasts the tallest peak in Runeterra. Located far from civilization, Mount Targon is all but impossible to reach, save by the most determined pilgrims, chasing some soul-deep yearning to reach its summit. Those hardy few who survive the climb return haunted and empty, or changed beyond all recognition."
    )
    object Noxus: Region(
    regionName = "Noxus",
    regionDescription = "Noxus is a brutal, expansionist empire, yet those who look beyond its warlike exterior will find an unusually inclusive society. Anyone can rise to a position of power and respect if they display the necessary aptitude, regardless of social standing, background, or wealth. Noxians value strength above all, though that strength can manifest in many different ways."
    )
    object Freljord: Region(
    regionName = "The Freljord",
    regionDescription = "The Freljord is a harsh and unforgiving land, where demi-gods walk the earth and the people are born warriors. While there are many individual tribes, the three greatest are the Avarosans, the Winter’s Claw, and the Frostguard, each uniquely shaped by their will to survive. It is also the only place on Runeterra where True Ice can be found."
    )
    object TheVoid: Region(
    regionName = "The Void",
    regionDescription = "creaming into existence with the birth of the universe, the Void is a manifestation of the unknowable nothingness that lies beyond. It is a force of insatiable hunger, waiting through the eons until its masters, the mysterious Watchers, mark the final time of undoing."
    )
    companion object
    {
        fun chooseRaceFromName(name: String): Region? = when(name){
            "Freljord" -> Freljord
            "Demacia" -> Demacia
            "Noxus" -> Noxus
            "Piltover & Zaun" -> PiltoverZaun
            "Targon" -> Targon
            "Shurima" -> Shurima
            "The Void" -> TheVoid
            "Ixtal" -> Ixtal
            "Shadow Isles" -> ShadowIsles
            "Bilgewater" -> Bilgewater
            "Ionia" -> Ionia
            "Bandle City" -> BandelCity
            else -> null
        }
    }

}