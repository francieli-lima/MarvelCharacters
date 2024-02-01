package br.com.francielilima.marvelcharacters.domain.use_case.favorite_character

import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.repository.FavoriteCharacterRepository

class AddFavoriteCharacterUseCase(
    private val repository: FavoriteCharacterRepository
) {

    suspend operator fun invoke(character: Character) {
        repository.insert(character)
    }
}