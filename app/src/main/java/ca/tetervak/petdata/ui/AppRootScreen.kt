package ca.tetervak.petdata.ui

import androidx.compose.runtime.Composable
import ca.tetervak.petdata.ui.screens.PetListScreen

@Composable
fun AppRootScreen() {
    PetListScreen(
        onItemClick = {}
    )
}