package ca.tetervak.petdata.ui.screens.petlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
    viewModel: PetListViewModel,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
){
    val state: State<PetListUiState> = viewModel.uiState
    val uiState: PetListUiState = state.value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PetDataTopAppBar(
                title = stringResource(R.string.list_title),
                onReloadButtonClick = viewModel::reloadData,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        when(uiState){
            is PetListUiState.Loading -> LoadingScreenContent()
            is PetListUiState.Error -> ErrorScreenContent(
                onRetry = viewModel::reloadData
            )
            is PetListUiState.Success -> ListScreenContent(
                petList = uiState.pets,
                onItemClick = onItemClick,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ListScreenContent(
    petList: List<Pet>,
    modifier: Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp, horizontal = 16.dp
        ), modifier = modifier
    ) {
        items(petList) { pet ->
            PetListItem(pet, { onItemClick(pet.id) })
        }
    }

}

@Composable
fun PetListItem(pet: Pet, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ){
            AsyncImage(
                model = pet.imageUrl,
                contentDescription = "${pet.petKind} ${pet.name}",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Fit,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = pet.petKind.capitalize(Locale.current),
                        fontSize = 28.sp
                    )
                    Text(
                        text = pet.name,
                        fontSize = 28.sp
                    )
                }
                Text(
                    text = pluralStringResource(id = R.plurals.years_old, pet.age, pet.age),
                    fontSize = 24.sp
                )
            }
        }
    }
}


