package br.com.francielilima.marvelcharacters.presentation.favorite_list

import br.com.francielilima.marvelcharacters.domain.model.Character

sealed class FavoriteCharacterEvent {
    data class UnfavoriteCharacter(val character: Character) : FavoriteCharacterEvent()
}