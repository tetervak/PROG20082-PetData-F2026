package ca.tetervak.petdata.data

import ca.tetervak.petdata.model.Pet
import ca.tetervak.petdata.network.PetDataApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val imageFolder: String =
    "https://files.tetervak.com/mobile/pet-data-app/images/"

interface PetDataRepository {

    suspend fun getAllPets(): List<Pet>
    suspend fun getPetById(id: Int): Pet?
}

class NetworkPetDataRepository(
    private val petDataApi: PetDataApi
): PetDataRepository {

    override suspend fun getAllPets(): List<Pet> =
        withContext(Dispatchers.IO) {
            val remoteData: RemoteData = petDataApi.getRemoteData()
            remoteData.pets.map { remotePet -> remotePet.toPet() }
        }

    override suspend fun getPetById(id: Int): Pet? {
        return getAllPets().find { pet -> pet.id == id }
    }
}

fun RemotePet.toPet() = Pet(
    id = this.id,
    name = this.name,
    petKind = this.petKind,
    age = this.age,
    imageUrl = imageFolder + this.image
)