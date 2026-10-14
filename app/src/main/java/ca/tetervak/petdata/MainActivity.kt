package ca.tetervak.petdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ca.tetervak.petdata.ui.AppRootScreen
import ca.tetervak.petdata.ui.theme.PetDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetDataTheme {
                AppRootScreen()
            }
        }
    }
}