package ca.tetervak.petdata.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.petdata.data.PetDataRepository
import ca.tetervak.petdata.model.Pet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


sealed interface PetListUiState {
    object Loading: PetListUiState
    data class Success(val pets: List<Pet>): PetListUiState
    object Error: PetListUiState
}

class PetListViewModel(
    private val repository: PetDataRepository
) : ViewModel() {
    private val _uiState: MutableState<PetListUiState> =
        mutableStateOf(PetListUiState.Loading)
    val uiState: State<PetListUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // fake 2s delay
            delay(2000.milliseconds)

            _uiState.value = try {
                PetListUiState.Success(repository.getAllPets())
            } catch (e: Exception) {
                Log.e("PetListViewModel", "loadData: ${e.message}")
                PetListUiState.Error
            }
        }
    }

    fun reloadData(){
        _uiState.value = PetListUiState.Loading
        loadData()
    }
}