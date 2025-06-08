package com.example.vamz_semestralka_hero_journal_dnd.ui

import android.content.res.Configuration
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
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroRaceDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.SubRace
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

/**
 * Hlavná obrazovka na zobrazenie popisu vybratej rasy v procese tvorby postavy.
 * Zobrazuje obrázok rasy, základné informácie, vlastnosti, voliteľné subrasy a možnosť výberu jazyka.
 *
 * Obrazovka je vytvorena s pomocou ChatGPT, spytal som sa na kostru celej stranky do ktorej som si
 * dorabal potrebne komponenty a logiku
 */

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
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 280.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                BasicIndormationAboutRace(
                    race = race,
                    characterCreationViewModel = characterCreationViewModel
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.traits), style = MaterialTheme.typography.titleMedium)

                race?.baseTraits?.forEach {
                    TraitCard(name = it.name, description = it.desc)
                }

                if (race?.subraces?.isNotEmpty() == true) {
                    DropdownSelector(
                        label = stringResource(R.string.choose_a_subrace),
                        options = race.subraces.map { it.name },
                        selectedOption = characterState.selectedSubRace?.name,
                        onOptionSelected = { name ->
                            characterCreationViewModel.setSelectedSubrace(
                                race.subraces.find { it.name == name }
                            )
                        }
                    )

                    SubraceSelection(selectedSubrace = characterState.selectedSubRace)

                    Spacer(modifier = Modifier.height(32.dp))

                    BottomcontrolButtons(
                        viewModel = characterCreationViewModel,
                        onBack = onBack,
                        onNextPage = onNextPage
                    )
                }
            }
        }
    }
}

/**
 * Zobrazi tlacidla na spodnej strane obrazovky
 * sluzia na navigaciu dalej a spat
 */

@Composable
fun BottomcontrolButtons(
    viewModel: CharacterCreationViewModel,
    onBack: () -> Unit,
    onNextPage: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        Button(onClick = {
            onBack()
        }) {
            Text(text = stringResource(R.string.back_label_button))
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            viewModel.calculateBAseAttribute(state.characterRace, state.selectedSubRace)
            onNextPage()
        }) {
            Text(text = stringResource(R.string.next_label_button))
        }
    }
}

/**
 * Zobrazí základné informácie o rase: názov, popis, rýchlosť, veľkosť, atribúty a jazyky.
 * Tiež umožňuje výber dodatočného jazyka (ak sú dostupné).
 */

@Composable
fun BasicIndormationAboutRace(
    race: HeroRaceDesc?,
    characterCreationViewModel: CharacterCreationViewModel
) {
    val state by characterCreationViewModel.uiState.collectAsState()

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

    Text(stringResource(R.string.speed, race?.speed?: ""))
    Text(stringResource(R.string.size, race?.size?: ""))
    Text(
        stringResource(
            R.string.abilities_race_description_page,
            race?.baseStats?.entries?.joinToString { "${it.key} +${it.value}" }?:""))
    Text(stringResource(R.string.languages, (race?.fixedLanguages?:"")))
    race?.availableLanguages?.let {
        DropdownSelector(
            label = stringResource(R.string.choose_a_language),
            options = it.filterNot { it in race.fixedLanguages },
            selectedOption = state.selectedLanguage,
            onOptionSelected = { language->
                characterCreationViewModel.setSelectedLanguage(language)
            }
        )
    }
}

/**
 * Zobrazí jednotlivú vlastnosť (trait) rasy alebo subrasy vo forme karty.
 */

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

/**
 * Univerzálna rozbaľovacia ponuka (Dropdown), umožňuje výber jednej hodnoty zo zoznamu možností.
 */

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
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
                        contentDescription = stringResource(R.string.show_options_arrow),
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

/**
 * Horný panel obrazovky (Top App Bar) pre obrazovku výberu rasy.
 * Obsahuje tlačidlo späť a dopredu a názov sekcie.
 */

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
                    contentDescription = stringResource(R.string.backArrow),
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
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
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

/**
 * Sekcia zobrazená po výbere subrasy. Zobrazuje vlastnosti subrasy a extra atribúty.
 */

@Composable
fun SubraceSelection(selectedSubrace: SubRace?) {
    if (selectedSubrace != null) {
        Text(stringResource(R.string.subrace_traits), style = MaterialTheme.typography.titleMedium)

        selectedSubrace.extraTraits.forEach {
            TraitCard(name = it.name, description = it.desc)
        }

        TraitCard(name = stringResource(R.string.subrace_abilities), selectedSubrace.extraStats.entries.joinToString { "${it.key} +${it.value}" }
        )
    }
}

@Preview(showBackground = true,name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
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