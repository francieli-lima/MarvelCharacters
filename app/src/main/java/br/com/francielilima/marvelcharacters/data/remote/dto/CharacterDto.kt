package br.com.francielilima.marvelcharacters.data.remote.dto

import br.com.francielilima.marvelcharacters.domain.model.Character

data class CharacterDto(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: ImageDto?,
)

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id ?: -1,
        name = name ?: "",
        description = description ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        isFavorite = false
    )
}