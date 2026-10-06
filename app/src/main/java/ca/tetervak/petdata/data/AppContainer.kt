package ca.tetervak.petdata.data

import ca.tetervak.petdata.network.PetDataApi
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val petDataRepository: PetDataRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://files.tetervak.com/mobile/pet-data-app/data/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: PetDataApi by lazy {
        retrofit.create(PetDataApi::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val petDataRepository: PetDataRepository by lazy {
        NetworkPetDataRepository(retrofitService)
    }
}
