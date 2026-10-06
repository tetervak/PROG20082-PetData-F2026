package ca.tetervak.petdata.network

import ca.tetervak.petdata.data.RemoteData
import retrofit2.http.GET

interface PetDataApi {

    @GET("pets.json")
    suspend fun getRemoteData(): RemoteData
}