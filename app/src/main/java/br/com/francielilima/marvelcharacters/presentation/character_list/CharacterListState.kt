package br.com.francielilima.marvelcharacters.presentation.character_list

import br.com.francielilima.marvelcharacters.domain.model.Character

data class CharacterListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String = ""
)