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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.data.HeroClassDesc
import com.example.vamz_semestralka_hero_journal_dnd.data.Spell

@Composable
fun ClassDescriptionPage(
    className: String,
    heroClass: HeroClassDesc,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    var selectedSpell by remember { mutableStateOf<Spell?>(null) }
    var showSpellDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CharacterPageTopAppBar(description = className)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            var selectedSkills by remember { mutableStateOf(setOf<String>()) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 250.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text(
                    text = className,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text("Hit Dice: ${heroClass.hitDice}")
                Text("Saving Throws: ${heroClass.savingThrows.first} & ${heroClass.savingThrows.second}")
                Text("Armor: ${heroClass.armor}")
                Text("Weapons: ${heroClass.weapon}")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Choose 2 Skills", style = MaterialTheme.typography.titleMedium)

                heroClass.skills.forEach { skill ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = skill in selectedSkills,
                            onCheckedChange = { checked ->
                                selectedSkills = when {
                                    checked && selectedSkills.size < 2 -> selectedSkills + skill
                                    !checked -> selectedSkills - skill
                                    else -> selectedSkills // do not add more than 2
                                }
                            },
                            enabled = skill in selectedSkills || selectedSkills.size < 2
                        )
                        Text(skill)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (heroClass.spells.isNotEmpty()) {
                    Text("Choose One Spell", style = MaterialTheme.typography.titleMedium)

                    val spellNames = heroClass.spells.map { it.name }
                    DropdownSelector(
                        label = "Select Spell",
                        options = spellNames,
                        selectedOption = selectedSpell?.name,
                        onOptionSelected = { name ->
                            selectedSpell = heroClass.spells.find { it.name == name }
                        }
                    )

                    if (selectedSpell != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { showSpellDialog = true }) {
                            Icon(Icons.Default.Info, contentDescription = "Spell Info")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Info")
                        }
                    }
                }
            }

            if (showSpellDialog && selectedSpell != null) {
                AlertDialog(
                    onDismissRequest = { showSpellDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showSpellDialog = false }) {
                            Text("Close")
                        }
                    },
                    title = {
                        Text("${selectedSpell!!.name} (Lv ${selectedSpell!!.level})")
                    },
                    text = {
                        Text("${selectedSpell!!.school} – ${selectedSpell!!.description}")
                    }
                )
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

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label)
        Box {
            OutlinedTextField(
                value = selectedOption ?: "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
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
fun PreviewClassDescriptionPage() {
    val cleric = HeroClassDesc.Wizard()
    ClassDescriptionPage(
        className = "Wizard",
        heroClass = cleric,
        imageRes = R.drawable.wizard_dnd_5e_1,
        modifier = Modifier
    )
}
