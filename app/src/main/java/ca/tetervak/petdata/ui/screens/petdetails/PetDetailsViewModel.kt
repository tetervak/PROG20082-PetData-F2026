package ca.tetervak.petdata.ui.screens.petdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import ca.tetervak.petdata.data.PetDataRepository
import ca.tetervak.petdata.model.Pet
import ca.tetervak.petdata.ui.PetDetailsRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

sealed interface PetDetailsUiState {
    object Loading: PetDetailsUiState
    data class Success(val pet: Pet): PetDetailsUiState
    object Error: PetDetailsUiState
}
class PetDetailsViewModel(
    private val repository: PetDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Type-safe argument extraction
    val args: PetDetailsRoute = savedStateHandle.toRoute<PetDetailsRoute>()
    private val petId: Int = args.petId

    private val _uiState: MutableState<PetDetailsUiState> =
        mutableStateOf(PetDetailsUiState.Loading)
    val uiState: State<PetDetailsUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // fake 2s delay
            delay(2000.milliseconds)

            _uiState.value =
                try {
                    val pet: Pet? = repository.getPetById(petId)
                    if (pet != null) {
                        PetDetailsUiState.Success(pet = pet)
                    } else {
                        PetDetailsUiState.Error
                    }
                } catch (_: Exception) {
                    PetDetailsUiState.Error
                }
        }
    }

    fun reloadData(){
        _uiState.value = PetDetailsUiState.Loading
        loadData()
    }
}

