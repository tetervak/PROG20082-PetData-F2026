package ca.tetervak.petdata

import android.app.Application
import ca.tetervak.petdata.data.AppContainer
import ca.tetervak.petdata.data.DefaultAppContainer

class PetDataApplication: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}