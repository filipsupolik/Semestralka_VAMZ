package com.example.vamz_semestralka_hero_journal_dnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.compose.AppTheme

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
                }
            }
        }
    }
}



