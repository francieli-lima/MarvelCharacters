package br.com.francielilima.marvelcharacters.domain.repository

import br.com.francielilima.marvelcharacters.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoriteCharacterRepository {

    fun getCharacters(): Flow<List<Character>>

    suspend fun getCharacterById(id: Int): Character?

    suspend fun insert(character: Character)

    suspend fun delete(character: Character)
}