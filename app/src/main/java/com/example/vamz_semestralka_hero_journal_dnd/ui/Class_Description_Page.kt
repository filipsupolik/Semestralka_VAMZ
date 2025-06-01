import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
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
    onClassConfirmed: (selectedSkills: List<String>, selectedSpell: Spell?) -> Unit,
    imageRes: Int,
    characterCreationViewModel: CharacterCreationViewModel = viewModel()
) {
    val characterUIState by characterCreationViewModel.uiState.collectAsState()
    var selectedSkills by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedSpell by remember { mutableStateOf<Spell?>(null) }
    var descriptionDialogSpell by remember { mutableStateOf<Spell?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(heroClass.name, style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Obrázok triedy hore
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = heroClass.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Popis triedy (Text nad kartami)
            Text(
                text = heroClass.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Základné info (texty bez kariet)
            Text("Hit Dice: ${heroClass.hitDice}", style = MaterialTheme.typography.bodyLarge)
            Text("Saving Throws: ${heroClass.savingThrows.first}, ${heroClass.savingThrows.second}", style = MaterialTheme.typography.bodyLarge)
            Text("Armor Proficiencies: ${heroClass.armor}", style = MaterialTheme.typography.bodyLarge)
            Text("Weapon Proficiencies: ${heroClass.weapon}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            // Výber skillov (zoznam, klikací text, max podľa skillChoices)
            Text("Choose ${heroClass.skillChoices} skills:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            heroClass.skills.forEach { skill ->
                val isSelected = skill in characterUIState.selectedSkill
                Text(
                    text = skill,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            characterCreationViewModel.setSelectedSkill(if (isSelected) {
                                selectedSkills - skill
                            } else if (selectedSkills.size < heroClass.skillChoices) {
                                selectedSkills + skill
                            } else selectedSkills)
                        }
                        .padding(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Výber spellov (klikací zoznam textov + info button)
            Text("Choose one starting spell:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            heroClass.spells.forEach { spell ->
                val isSelected = spell == selectedSpell
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedSpell = spell }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = spell.name,
                        modifier = Modifier.weight(1f),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = { descriptionDialogSpell = spell }) {
                        Icon(Icons.Default.Info, contentDescription = "Spell Info")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm button aktivny len ked je vybranych dost skillov a spell
            Button(
                onClick = { onClassConfirmed(selectedSkills, selectedSpell) },
                enabled = selectedSkills.size == heroClass.skillChoices && selectedSpell != null,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Confirm")
            }
        }
    }

    // Dialog s detailom spellu
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
                    Text("${spell.school} (Level ${spell.level})")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(spell.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { descriptionDialogSpell = null }, modifier = Modifier.align(Alignment.End)) {
                        Text("Close")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRogueClassDetailScreen() {
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

