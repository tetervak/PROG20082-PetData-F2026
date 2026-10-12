package ca.tetervak.petdata.di

import ca.tetervak.petdata.data.PetDataRepository
import ca.tetervak.petdata.data.NetworkPetDataRepository
import ca.tetervak.petdata.network.PetDataApi
import ca.tetervak.petdata.ui.screens.PetListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    single<PetDataApi> {
        get<Retrofit>().create(PetDataApi::class.java)
    }

    singleOf(::NetworkPetDataRepository) bind PetDataRepository::class

    viewModelOf(::PetListViewModel)
}

