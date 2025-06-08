package com.example.vamz_semestralka_hero_journal_dnd.navigation

import HeroClassDetailScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vamz_semestralka_hero_journal_dnd.data.classes
import com.example.vamz_semestralka_hero_journal_dnd.data.races
import com.example.vamz_semestralka_hero_journal_dnd.data.regions
import com.example.vamz_semestralka_hero_journal_dnd.ui.AbilityScreen
import com.example.vamz_semestralka_hero_journal_dnd.ui.CharacterNameChoice
import com.example.vamz_semestralka_hero_journal_dnd.ui.CharacterPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.CharacterStatsScreen
import com.example.vamz_semestralka_hero_journal_dnd.ui.HeroListPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.HeroRace_Description_Page
import com.example.vamz_semestralka_hero_journal_dnd.ui.MainPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.RegionPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

/**
 * Sealed class representing all the possible screens/routes in the app
 */
sealed class Screen(val route: String) {

    // Shows list of all created characters
    object ShowAllCharacters : Screen("choose_character")

    // Displays the details of a selected hero by their name
    object ShowHero : Screen("show_hero/{name}") {
        fun createRoute(heroName: String): String {
            return "show_hero/${heroName}"
        }
    }

    // Screen for choosing the hero's name
    object NameChoosing : Screen("choose_name")

    // Screen where the user selects a race
    object RaceSelection : Screen("race_selection")

    // Screen showing detailed information about the selected race
    object RacePage : Screen("race_page/{raceName}") {
        fun createRoute(raceName: String): String {
            return "race_page/${raceName}"
        }
    }

    // Screen where the user selects a region
    object RegionSelection : Screen("region_selection")

    // Screen showing detailed information about the selected region
    object RegionPage : Screen("region_page/{region_name}") {
        fun createRoute(regionName: String): String {
            return "region_page/${regionName}"
        }
    }

    // Screen for selecting the character class
    object ClassSelection : Screen("class_selection")

    // Screen showing detailed information about the selected class
    object ClassPage : Screen("class_page/{class_name}") {
        fun createRoute(className: String): String {
            return "class_page/${className}"
        }
    }

    // Final summary screen where player sets attributes and finishes character creation
    object Summary : Screen("summary")

    // Initial screen with navigation to character list
    object MainPage : Screen("main_page")
}

/**
 * Composable function managing navigation between all app screens using NavHostController.
 *
 * @param navController NavHostController used to control navigation.
 * @param viewModel ViewModel managing character creation state.
 */
@Composable
fun NavigationBetweenScreens(navController: NavHostController, viewModel: CharacterCreationViewModel) {
    val navigationState by viewModel.uiState.collectAsState()

    // Define the navigation host with starting destination
    NavHost(
        navController = navController,
        startDestination = Screen.MainPage.route
    ) {
        // Main menu screen
        composable(route = Screen.MainPage.route) {
            MainPage(navigation = {
                navController.navigate(Screen.ShowAllCharacters.route)
            })
        }

        // Character list screen
        composable(route = Screen.ShowAllCharacters.route) {
            CharacterPage(
                viewModel = viewModel,
                onCreateCharacter = {
                    navController.navigate(Screen.NameChoosing.route)
                },
                onShowStatsOfCharacter = { characterName ->
                    viewModel.setSelectedPlayerName(characterName)
                    navController.navigate(Screen.ShowHero.createRoute(characterName))
                },
                onBack = {
                    navController.navigate(Screen.MainPage.route)
                }
            )
        }

        // Character stats detail screen
        composable(route = Screen.ShowHero.route) {
            CharacterStatsScreen(
                characterCreationViewModel = viewModel,
                onBack = {
                    navController.navigate(Screen.ShowAllCharacters.route)
                }
            )
        }

        // Screen to choose character name
        composable(route = Screen.NameChoosing.route) {
            CharacterNameChoice(
                characterCreationViewModel = viewModel,
                onRaceSelection = {
                    navController.navigate(Screen.RegionSelection.route)
                },
                onHome = {
                    navController.navigate(Screen.MainPage.route)
                }
            )
        }

        // Screen to select region
        composable(route = Screen.RegionSelection.route) {
            HeroListPage(
                whatToSelect = "Region",
                listOfDifferentTypes = regions,
                characterCreationViewModel = viewModel,
                onNextPage = { chosenName ->
                    navController.navigate(Screen.RegionPage.createRoute(regionName = chosenName))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Region description screen
        composable(route = Screen.RegionPage.route) {
            RegionPage(
                viewModel,
                onNextPage = {
                    navController.navigate(Screen.RaceSelection.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Race selection screen
        composable(route = Screen.RaceSelection.route) {
            HeroListPage(
                whatToSelect = "Race",
                listOfDifferentTypes = races,
                characterCreationViewModel = viewModel,
                onNextPage = { raceName ->
                    Log.d("RaceNav", "Navigating to race: $raceName")
                    navController.navigate(Screen.RacePage.createRoute(raceName = raceName))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Race description screen
        composable(route = Screen.RacePage.route) {
            HeroRace_Description_Page(
                description = navigationState.characterRace.name,
                race = navigationState.characterRace,
                characterCreationViewModel = viewModel,
                onNextPage = {
                    navController.navigate(Screen.ClassSelection.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Class selection screen
        composable(route = Screen.ClassSelection.route) {
            HeroListPage(
                whatToSelect = "Class",
                listOfDifferentTypes = classes,
                characterCreationViewModel = viewModel,
                onNextPage = { className ->
                    navController.navigate(Screen.ClassPage.createRoute(className))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Class description screen
        composable(route = Screen.ClassPage.route) {
            HeroClassDetailScreen(
                heroClass = navigationState.characterClass,
                characterCreationViewModel = viewModel,
                onNextPage = {
                    navController.navigate(Screen.Summary.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Final summary screen where stats and attributes are set
        composable(route = Screen.Summary.route) {
            AbilityScreen(
                viewModel,
                modifier = Modifier,
                onNextPage = {
                    navController.navigate(Screen.ShowAllCharacters.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

