package br.com.francielilima.marvelcharacters.data.repository

import br.com.francielilima.marvelcharacters.data.data_source.CharacterDao
import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.repository.FavoriteCharacterRepository
import kotlinx.coroutines.flow.Flow

class FavoriteCharacterRepositoryImpl(
    private val dao: CharacterDao
) : FavoriteCharacterRepository {

    override fun getCharacters(): Flow<List<Character>> {
        return dao.getCharacters()
    }

    override suspend fun getCharacterById(id: Int): Character? {
        return dao.getCharacterById(id)
    }

    override suspend fun insert(character: Character) {
        dao.insert(character)
    }

    override suspend fun delete(character: Character) {
        dao.delete(character)
    }
}