package com.example.vamz_semestralka_hero_journal_dnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.vamz_semestralka_hero_journal_dnd.navigation.NavigationBetweenScreens
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    val viewModel: CharacterCreationViewModel = viewModel()
                    NavigationBetweenScreens(navController, viewModel)
                }
            }
        }
    }
}



