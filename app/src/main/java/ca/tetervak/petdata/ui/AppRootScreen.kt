package ca.tetervak.petdata.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import ca.tetervak.petdata.ui.screens.petdetails.PetDetailsScreen
import ca.tetervak.petdata.ui.screens.petdetails.PetDetailsViewModel
import ca.tetervak.petdata.ui.screens.petlist.PetListScreen
import ca.tetervak.petdata.ui.screens.petlist.PetListViewModel
import org.koin.androidx.compose.koinViewModel

@Serializable
object PetListRoute

@Serializable
data class PetDetailsRoute(val petId: Int)



@Composable
fun AppRootScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PetListRoute
    ) {
        // Destination 1: Home
        composable<PetListRoute> {
            val viewModel: PetListViewModel = koinViewModel()

            PetListScreen(
                viewModel = viewModel,
                onItemClick = { petId ->
                    navController.navigate(PetDetailsRoute(petId = petId))
                }
            )
        }

        // Destination 2: Detail
        composable<PetDetailsRoute> {
            // Scoped strictly to this specific back-stack entry!
            val viewModel: PetDetailsViewModel = koinViewModel()

            PetDetailsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }

}