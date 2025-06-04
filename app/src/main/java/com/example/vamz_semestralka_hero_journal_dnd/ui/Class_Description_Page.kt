
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroClassDetailScreen(
    heroClass: HeroClassDesc,
    imageRes: Int?,
    characterCreationViewModel: CharacterCreationViewModel
) {
    val characterUIState by characterCreationViewModel.uiState.collectAsState()
    var mExpanded by remember { mutableStateOf(false) }
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
            imageRes?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = heroClass.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = heroClass.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text("Hit Dice: ${heroClass.hitDice}", style = MaterialTheme.typography.bodyLarge)
            Text("Saving Throws: ${heroClass.savingThrows.first}, ${heroClass.savingThrows.second}", style = MaterialTheme.typography.bodyLarge)
            Text("Armor Proficiencies: ${heroClass.armor}", style = MaterialTheme.typography.bodyLarge)
            Text("Weapon Proficiencies: ${heroClass.weapon}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Text("Choose ${heroClass.skillChoices} skills:", style = MaterialTheme.typography.titleMedium)

            Column(Modifier.padding(20.dp)) {
                DropdownMenu(
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    heroClass.skills.forEach { label ->
                        DropdownMenuItem(
                            text = { Text(text = label) },
                            onClick = {
                                characterCreationViewModel.setPlayerSkill(label)
                                mExpanded = false
                            }
                        )
                    }
                }
                DropdownMenu(
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    heroClass.skills.forEach { label ->
                        DropdownMenuItem(
                            text = { Text(text = label) },
                            onClick = {
                                characterCreationViewModel.setPlayerSkill(label)
                                mExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Choose one starting spell:", style = MaterialTheme.typography.titleMedium)
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
                            Icon(Icons.Default.Info, contentDescription = "Spell Info")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

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

