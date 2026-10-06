package ca.tetervak.petdata.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteData(
    val pets: List<RemotePet>
)