package br.com.francielilima.marvelcharacters.presentation.character_detail

import br.com.francielilima.marvelcharacters.domain.model.Character

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String = ""
)