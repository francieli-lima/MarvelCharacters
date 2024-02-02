package br.com.francielilima.marvelcharacters.presentation.character_list

import br.com.francielilima.marvelcharacters.domain.model.Character

sealed class CharacterEvent {
    data class UnfavoriteCharacter(val character: Character) : CharacterEvent()
    data class FavoriteCharacter(val character: Character) : CharacterEvent()
    data class Search(val search: String) : CharacterEvent()
    object Reload : CharacterEvent()
}