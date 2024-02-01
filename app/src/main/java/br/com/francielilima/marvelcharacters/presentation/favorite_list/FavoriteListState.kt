package br.com.francielilima.marvelcharacters.presentation.favorite_list

import br.com.francielilima.marvelcharacters.domain.model.Character

data class FavoriteListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String = ""
)