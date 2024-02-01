package br.com.francielilima.marvelcharacters.domain.use_case.favorite_character

import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase

data class FavoriteUseCases(
    val getCharacters: GetCharactersUseCase,
    val deleteCharacter: DeleteFavoriteCharacterUseCase,
    val addCharacter: AddFavoriteCharacterUseCase,
    val getCharacter: GetFavoriteCharacterUseCase
)