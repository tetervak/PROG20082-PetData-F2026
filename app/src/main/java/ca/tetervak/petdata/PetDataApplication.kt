package ca.tetervak.petdata

import android.app.Application
import ca.tetervak.petdata.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PetDataApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PetDataApplication)
            modules(appModule)
        }
    }
}