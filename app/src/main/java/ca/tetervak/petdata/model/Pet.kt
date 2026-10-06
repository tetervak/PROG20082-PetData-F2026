package ca.tetervak.petdata.model

data class Pet(
    val id: Int,
    val name: String,
    val petKind: String,
    val age: Int,
    val imageUrl: String
)
