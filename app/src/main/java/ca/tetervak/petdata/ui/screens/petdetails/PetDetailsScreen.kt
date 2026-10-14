package ca.tetervak.petdata.ui.screens.petdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.tetervak.petdata.R
import ca.tetervak.petdata.model.Pet
import ca.tetervak.petdata.ui.screens.common.ErrorScreenContent
import ca.tetervak.petdata.ui.screens.common.LoadingScreenContent
import ca.tetervak.petdata.ui.screens.common.PetDataTopAppBar
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(
    viewModel: PetDetailsViewModel,
    modifier: Modifier = Modifier,
    onNavigateBack: (() -> Unit)? = null
) {
    val state: State<PetDetailsUiState> = viewModel.uiState
    val uiState: PetDetailsUiState = state.value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PetDataTopAppBar(
                title = stringResource(R.string.list_title),
                scrollBehavior = scrollBehavior,
                onReloadButtonClick = viewModel::reloadData,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->

        when (uiState) {
            is PetDetailsUiState.Loading -> {
                LoadingScreenContent(
                    modifier = modifier.padding(innerPadding)
                )
            }

            is PetDetailsUiState.Error -> {
                ErrorScreenContent(
                    onRetry = viewModel::reloadData,
                    modifier = modifier.padding(innerPadding)
                )
            }

            is PetDetailsUiState.Success ->
                DetailsScreenContent(
                    pet = uiState.pet,
                    modifier = modifier.padding(innerPadding)
                )
        }


    }
}

@Composable
fun DetailsScreenContent(pet: Pet, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(32.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = pet.petKind.capitalize(Locale.current),
                    fontSize = 28.sp
                )
                Text(
                    text = pet.name,
                    fontSize = 28.sp
                )
            }

            AsyncImage(
                model = pet.imageUrl,
                contentDescription = "${pet.petKind} ${pet.name}",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Fit,
            )

            Text(
                text = pluralStringResource(id = R.plurals.years_old, pet.age, pet.age),
                fontSize = 24.sp
            )
        }
    }
}