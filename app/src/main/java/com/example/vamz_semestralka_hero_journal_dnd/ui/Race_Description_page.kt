package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun HeroRace_Description_Page(
    description: String,
    race: HeroRaceDesc?,
    characterCreationViewModel: CharacterCreationViewModel,
    onNextPage: () -> Unit,
    onBack: () -> Unit
)
{
    val characterState by characterCreationViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold (
        topBar = {
            CharacterPageTopAppBar(viewModel = characterCreationViewModel ,description,modifier = Modifier, onBackClick = onBack, onNextClick = onNextPage)
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                characterCreationViewModel.resetRegion()
                                onBack()
                            },
                        ) {
                            Text(text = "Back")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                characterCreationViewModel.setRegion(
                                    name = characterState.playerRegion.regionName
                                )
                                characterCreationViewModel.setPlayerSubRace(
                                    subRace = characterState.selectedSubRace
                                )
                                characterCreationViewModel.setPlayerLanguage(
                                    language = characterState.selectedLanguage
                                )
                                characterCreationViewModel.calculateBAseAttribute(
                                    race = characterState.characterRace,
                                    subRace = characterState.selectedSubRace
                                )
                                onNextPage()
                            }
                        ) {
                            Text(text = "Next")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
        {
                Image(
                    painter = painterResource(id = characterState.characterRace.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 250.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.LightGray)
                    .padding(16.dp)
                    .verticalScroll(state = scrollState, enabled = true)
            ) {
                Text(
                    text = race?.name ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = race?.descriptionCharacterRace ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Speed: ${race?.speed}")
                Text("Size: ${race?.size}")
                Text("Abilities: ${race?.baseStats?.entries?.joinToString { "${it.key} +${it.value}" }}")
                Text("Languages: ${(race?.fixedLanguages)}")
                race?.availableLanguages?.let {
                    DropdownSelector(
                        label = "Choose a language",
                        description = race.descriptionCharacterRace,
                        options = it.filterNot { it in race.fixedLanguages },
                        selectedOption = characterState.selectedLanguage,
                        onOptionSelected = { language->
                            characterCreationViewModel.setSelectedLanguage(language)
                        }
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
                Text("Traits", style = MaterialTheme.typography.titleMedium)

                race?.baseTraits?.forEach {
                    TraitCard(name = it.name, description = it.desc)
                }

                if (race?.subraces?.isNotEmpty() == true) {
                    DropdownSelector(
                        label = "Choose a subrace",
                        options = race.subraces.map { it.name },
                        selectedOption = characterState.selectedSubRace?.name,
                        onOptionSelected = { name ->
                            characterCreationViewModel.setSelectedSubrace(
                                race.subraces.find { it.name == name }
                            )
                        },
                        description = race.descriptionCharacterRace
                    )

                    SubraceSection(selectedSubrace = characterState.selectedSubRace)
                }
            }
        }
    }

@Composable
fun TraitCard(name: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label)
        Box {
            OutlinedTextField(
                value = selectedOption ?: "",
                onValueChange = onOptionSelected,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Show options arrow",
                        Modifier.clickable { expanded = true }
                    )
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onOptionSelected(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharacterPageTopAppBar(
    viewModel: CharacterCreationViewModel,
    description: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {

    val characterState by viewModel.uiState.collectAsState()
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Row {
                Icon(
                    painter = painterResource(R.drawable._34226_back_arrow_left_icon),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.description_page_top_app_bar_text, description),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.arrow_forward_to_next_page),
                    modifier = Modifier
                        .clickable {
                            viewModel.setRegion(
                                name = characterState.playerRegion.regionName
                            )
                            viewModel.setPlayerSubRace(
                                subRace = characterState.selectedSubRace
                            )
                            viewModel.setPlayerLanguage(
                                language = characterState.selectedLanguage
                            )
                            viewModel.calculateBAseAttribute(
                                race = characterState.characterRace,
                                subRace = characterState.selectedSubRace
                            )
                            onNextClick()
                        }
                        .padding(horizontal = 16.dp)
                )
            }
        }
    )
}


@Composable
fun SubraceSection(selectedSubrace: SubRace?) {
    if (selectedSubrace != null) {
        Text("Subrace Traits", style = MaterialTheme.typography.titleMedium)

        selectedSubrace.extraTraits.forEach {
            TraitCard(name = it.name, description = it.desc)
        }

        Text(
            text = "Subrace Abilities: " +
                    selectedSubrace.extraStats.entries.joinToString { "${it.key} +${it.value}" }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDescriptionPage() {
    val previewRace = HeroRaceDesc.Yordle()
    HeroRace_Description_Page(
        description = "Yordle",
        race = previewRace,
        characterCreationViewModel = viewModel(),
        onBack = {},
        onNextPage = {}
    )
}
