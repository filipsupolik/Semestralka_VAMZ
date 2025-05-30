package com.example.vamz_semestralka_hero_journal_dnd

import HeroClassDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.compose.AppTheme
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface {
                    //HeroListPage( "region",regions)
                    //HeroListPage( "race",races)
                    //HeroListPage( "class",classes)
                    val rogueClass = HeroClassDesc.Rogue()
                    val imageRes = R.drawable.rogue_dnd_5e // zmeň podľa reálneho resource ID

                    HeroClassDetailScreen(
                        heroClass = rogueClass,
                        imageRes = imageRes,
                        onClassConfirmed = { skills, spell ->
                            println("Selected skills: $skills")
                            println("Selected spell: ${spell?.name}")
                        }
                    )
                }
            }
        }
    }
}



