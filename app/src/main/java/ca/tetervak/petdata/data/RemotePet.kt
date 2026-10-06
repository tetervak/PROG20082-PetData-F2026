package ca.tetervak.petdata.data

import kotlinx.serialization.Serializable

@Serializable
data class RemotePet(
    val id: Int,
    val name: String,
    val petKind: String,
    val age: Int,
    val image: String
)
