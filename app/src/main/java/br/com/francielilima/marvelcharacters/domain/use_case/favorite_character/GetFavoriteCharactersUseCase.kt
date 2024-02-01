package br.com.francielilima.marvelcharacters.domain.use_case.favorite_character

import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.repository.FavoriteCharacterRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteCharactersUseCase(
    private val repository: FavoriteCharacterRepository
) {

    operator fun invoke(): Flow<List<Character>> {
        return repository.getCharacters()
    }
}