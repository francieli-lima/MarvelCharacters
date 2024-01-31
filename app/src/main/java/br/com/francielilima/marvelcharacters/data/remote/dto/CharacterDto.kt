package br.com.francielilima.marvelcharacters.data.remote.dto

import br.com.francielilima.marvelcharacters.domain.model.Character

data class CharacterDto(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: ImageDto?,
    val comics: ComicListDto?,
    val events: EventListDto?,
    val series: SeriesListDto?
)

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id ?: -1,
        name = name ?: "",
        description = description ?: "",
        thumbnail = thumbnail?.path ?: "",
        comics = comics?.items?.map { name ?: "" }?.toList() ?: listOf<String>(),
        events = events?.items?.map { name ?: "" }?.toList() ?: listOf<String>(),
        series = series?.items?.map { name ?: "" }?.toList() ?: listOf<String>(),
    )
}