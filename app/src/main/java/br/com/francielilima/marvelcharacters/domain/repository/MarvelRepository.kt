package br.com.francielilima.marvelcharacters.domain.repository

import br.com.francielilima.marvelcharacters.data.remote.dto.CharacterDto

interface MarvelRepository {
    suspend fun getCharacters(): List<CharacterDto>?
    suspend fun getCharacterById(id: Int): CharacterDto
}