package com.example.vamz_semestralka_hero_journal_dnd.navigation

import HeroClassDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.vamz_semestralka_hero_journal_dnd.ui.Description_Page
import com.example.vamz_semestralka_hero_journal_dnd.ui.HeroListPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.MainPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.RegionPage
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

sealed class Screen(val route: String) {
    object EventSelection: Screen("event_selection")

    object ShowAllCharacters: Screen("choose_character")

    object ShowHero: Screen("show_hero/{name}") {
        fun createRoute(heroName: String): String {
            return "show_hero/$heroName"
        }
    }

    object NameChoosing: Screen("choose_name")

    object RaceSelection: Screen("race_selection")
    object RacePage: Screen("race_page/{raceName}"){
        fun createRoute(raceName: String): String {
            return "race_page/$raceName"
        }
    }

    object RegionSelection: Screen("region_selection")
    object RegionPage: Screen("region_page/{region_name}") {
        fun createRoute(regionName: String): String {
            return "region_page/$regionName"
        }
    }

    object ClassSelection : Screen("class_selection")
    object ClassPage: Screen("class_page/{class_name}") {
        fun createRoute(className: String): String {
            return "class_page/$className"
        }
    }

    object Summary : Screen("summary")

    class DetailScreen(raceId: String) : Screen("detail/$raceId") {
        val raceIdParam = raceId

        fun createRoute(raceId: String): String {
            return "detail/$raceId"
        }
    }
}

@Composable
fun NavigationBetweenScreens(navController: NavHostController, )
{
    val viewModel: CharacterCreationViewModel = viewModel()
    val characterUIState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.EventSelection
    ) {
        composable(
            route = Screen.EventSelection.route
        ){
            MainPage()
        }

        composable(route = Screen.ShowAllCharacters.route){
            CharacterPage()
        }

        composable(route = Screen.ShowHero.route){
            CharacterStatsScreen(name = characterUIState.playerName,characterCreationViewModel = viewModel)
        }

        composable(route = Screen.NameChoosing.route){
            CharacterNameChoice()
        }

        composable(route = Screen.RegionSelection.route){
            HeroListPage(
                whatToSelect = "Region",
                listOfDifferentTypes = regions,
                characterCreationViewModel = viewModel
            )
        }

        composable(route = Screen.RegionPage.route){
            RegionPage(viewModel)
        }

        composable(route = Screen.RaceSelection.route){
            HeroListPage(
                whatToSelect = "Race",
                listOfDifferentTypes = races,
                characterCreationViewModel = viewModel
            )
        }

        composable(route = Screen.RacePage.route){
            Description_Page(
                description = characterUIState.characterRace.descriptionCharacterRace,
                race = characterUIState.characterRace,
                characterCreationViewModel = viewModel
            )
        }

        composable(route = Screen.ClassSelection.route){
            HeroListPage(
                whatToSelect = "Class",
                listOfDifferentTypes = classes,
                characterCreationViewModel = viewModel
            )
        }

        composable(route = Screen.ClassPage.route){
            HeroClassDetailScreen(
                heroClass = characterUIState.characterClass ?: HeroClassDesc.Rogue(),
                imageRes = characterUIState.characterClass?.imageRes,
                characterCreationViewModel = viewModel
            )
        }

        composable(route = Screen.Summary.route){
            AbilityScreen(viewModel, modifier = Modifier)
        }
    }
}

