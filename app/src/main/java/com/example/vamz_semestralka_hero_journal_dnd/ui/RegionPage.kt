package com.example.vamz_semestralka_hero_journal_dnd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamz_semestralka_hero_journal_dnd.R
import com.example.vamz_semestralka_hero_journal_dnd.ui.state.CharacterCreationViewModel

@Composable
fun RegionPage(viewModel: CharacterCreationViewModel, onNextPage: () -> Unit, onBack: () -> Unit)
{
    val regionState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            RegionTopAppBar(title = regionState.playerRegion.regionName, onBackPage = onBack, onNextPage = onNextPage)
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
                                viewModel.resetRegion()
                                onBack()
                            },
                        ) {
                            Text(text = stringResource(R.string.back_label_button))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                viewModel.setRegion(
                                    name = regionState.playerRegion.regionName
                                )
                                onNextPage()
                            }
                        ) {
                            Text(text = stringResource(R.string.next_button_label))
                        }
                    }
                }
            )
        }
    ) {innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(state = scrollState, enabled = true)
        ){
            Image(
                painter = painterResource(id = regionState.playerRegion.imageRes),
                contentDescription = stringResource(
                    R.string.Region_page_image_contentDescription,
                    regionState.playerRegion.regionName
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )

            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))

            Text(
                text = regionState.playerRegion.regionDescription,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionTopAppBar(title: String, onBackPage:() -> Unit, onNextPage: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.backArrow),
                    modifier = Modifier.clickable {
                        onBackPage()
                    })

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = title
                )

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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Preview(
    name = "Portrait Preview",
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun RegionPagePortraitPreview() {
    val fakeViewModel: CharacterCreationViewModel = viewModel()
    RegionPage(
        viewModel = fakeViewModel,
        onNextPage = {},
        onBack = {}
    )
}

@Preview(
    name = "Landscape Preview",
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=891dp,height=411dp,dpi=420" // landscape
)
@Composable
fun RegionPageLandscapePreview() {
    val fakeViewModel: CharacterCreationViewModel = viewModel()
    RegionPage(
        viewModel = fakeViewModel,
        onNextPage = {},
        onBack = {}
    )
}


