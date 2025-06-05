package com.example.vamz_semestralka_hero_journal_dnd.navigation

import HeroClassDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
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

sealed class Screen(val route: String) {

    object ShowAllCharacters: Screen("choose_character")

    object ShowHero: Screen("show_hero/{name}") {
        fun createRoute(heroName: String): String {
            return "show_hero/${heroName}"
        }
    }

    object NameChoosing: Screen("choose_name")

    object RaceSelection: Screen("race_selection")
    object RacePage: Screen("race_page/{raceName}"){
        fun createRoute(raceName: String): String {
            return "race_page/${raceName}"
        }
    }

    object RegionSelection: Screen("region_selection")
    object RegionPage: Screen("region_page/{region_name}") {
        fun createRoute(regionName: String): String {
            return "region_page/${regionName}"
        }
    }

    object ClassSelection : Screen("class_selection")
    object ClassPage: Screen("class_page/{class_name}") {
        fun createRoute(className: String): String {
            return "class_page/${className}"
        }
    }

    object Summary : Screen("summary")
    object MainPage: Screen("main_page")
}

@Composable
fun NavigationBetweenScreens(navController: NavHostController, viewModel: CharacterCreationViewModel)
{
    val navigationState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = Screen.MainPage.route
    ) {
        composable(
            route = Screen.MainPage.route
        ){
            MainPage(navigation = {
                navController.navigate(Screen.ShowAllCharacters.route)
            })
        }

        composable(route = Screen.ShowAllCharacters.route){
            CharacterPage(
                viewModel = viewModel,
                onCreateCharacter = {
                navController.navigate(Screen.NameChoosing.route)
            }, onShowStatsOfCharacter = {characterName ->
                navController.navigate(Screen.ShowHero.createRoute(characterName))
            },
                onBack = {
                    navController.navigate(Screen.MainPage.route)
                }
            )
        }

        composable(route = Screen.ShowHero.route){
            CharacterStatsScreen(
                characterCreationViewModel = viewModel,
                onBack = {
                    navController.navigate(Screen.ShowAllCharacters.route)
                })
        }

        composable(route = Screen.NameChoosing.route){
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

        composable(route = Screen.RegionSelection.route){
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

        composable(route = Screen.RegionPage.route){
            RegionPage(
                viewModel,
                onNextPage = {
                    navController.navigate(Screen.RaceSelection.route)
                },
                onBack = {
                    navController.popBackStack()
                })
        }

        composable(route = Screen.RaceSelection.route){
            HeroListPage(
                whatToSelect = "Race",
                listOfDifferentTypes = races,
                characterCreationViewModel = viewModel,
                onNextPage = { raceName ->
                    navController.navigate(Screen.RacePage.createRoute(raceName = raceName))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.RacePage.route){
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

        composable(route = Screen.ClassSelection.route){
            HeroListPage(
                whatToSelect = "Class",
                listOfDifferentTypes = classes,
                characterCreationViewModel = viewModel,
                onNextPage = {className->
                    navController.navigate(Screen.ClassPage.createRoute(className))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.ClassPage.route){
            HeroClassDetailScreen(
                heroClass = navigationState.characterClass ?: HeroClassDesc.Rogue(),
                imageRes = navigationState.characterClass?.imageRes,
                characterCreationViewModel = viewModel,
                onNextPage = {
                    navController.navigate(Screen.Summary.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Summary.route){
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

