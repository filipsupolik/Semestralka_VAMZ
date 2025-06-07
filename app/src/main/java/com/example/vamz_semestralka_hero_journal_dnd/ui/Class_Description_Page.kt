
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroClassDetailScreen(
    heroClass: HeroClassDesc,
    imageRes: Int?,
    characterCreationViewModel: CharacterCreationViewModel,
    onNextPage: () -> Unit,
    onBack: () -> Unit
) {
    val characterUIState by characterCreationViewModel.uiState.collectAsState()
    var descriptionDialogSpell by remember { mutableStateOf<Spell?>(null) }
    val classDescriptionScrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(heroClass.name, style = MaterialTheme.typography.titleLarge)

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(R.string.arrow_forward_to_next_page),
                            modifier = Modifier.clickable {
                                onNextPage()
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.backArrow),
                        modifier = Modifier.clickable {
                            onBack()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.padding_medium))
                .verticalScroll(state = classDescriptionScrollState)
        ) {
                Image(
                    painter = painterResource(id = heroClass.imageRes),
                    contentDescription = heroClass.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = heroClass.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            Text(stringResource(R.string.hit_dice, heroClass.hitDice), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.saving_throws, heroClass.savingThrows.first, heroClass.savingThrows.second), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.armor_proficiencies, heroClass.armor), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.weapon_proficiencies, heroClass.weapon), style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.choose_skills, heroClass.skillChoices), style = MaterialTheme.typography.titleMedium)

            Column(Modifier.padding(20.dp)) {
                DropdownSelector(
                    label = stringResource(R.string.choose_a_skill),
                    selectedOption = characterUIState.playerSkill.first,
                    options = heroClass.skills,
                    onOptionSelected = { skill->
                        characterCreationViewModel.setPlayerSkill(skill)
                    }
                )
                DropdownSelector(
                    label = stringResource(R.string.choose_a_skill),
                    options = characterUIState.characterClass.skills.filterNot { it in characterUIState.playerSkill.first},
                    selectedOption = characterUIState.playerSkill.second,
                    onOptionSelected = {skill->
                        characterCreationViewModel.setPlayerSkill(skill)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.choose_one_starting_spell), style = MaterialTheme.typography.titleMedium)
            heroClass.spells.forEach { spell ->
                val isSelected = spell == characterUIState.playerSpell
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            characterCreationViewModel.setStartingSpell(spell.name)
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = spell.name, modifier = Modifier.weight(1f))
                        IconButton(onClick = { descriptionDialogSpell = spell }) {
                            Icon(Icons.Default.Info, contentDescription = stringResource(R.string.spell_info))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    characterCreationViewModel.resetRegion()
                    onBack()
                }
            ) {
                Text(text = stringResource(R.string.back_label_button))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onNextPage()
                },
                enabled = characterUIState.playerSkill.toList().isNotEmpty() && characterUIState.playerSpell != null
            ) {
                Text(text = stringResource(R.string.next_label_button))
            }
        }
    }

    descriptionDialogSpell?.let { spell ->
        Dialog(onDismissRequest = { descriptionDialogSpell = null }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(spell.name, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(
                            R.string.spell_description_school_level,
                            spell.school,
                            spell.level
                        ))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(spell.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { descriptionDialogSpell = null }, modifier = Modifier.align(Alignment.End)) {
                        Text(stringResource(R.string.close_label_button))
                    }
                }
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun PreviewDescriptionPage() {
    val previewClass = HeroClassDesc.Paladin()
    HeroClassDetailScreen(
        heroClass = previewClass,
        characterCreationViewModel = viewModel(),
        onBack = {},
        onNextPage = {},
        imageRes = R.drawable.dnd_paladin_5e_dwarf
    )
}

