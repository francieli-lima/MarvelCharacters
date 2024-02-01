package br.com.francielilima.marvelcharacters.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.DeleteFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.GetFavoriteCharactersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteListViewModel(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val deleteFavoriteCharacterUseCase: DeleteFavoriteCharacterUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteListState())
    val state: State<FavoriteListState> = _state

    init {
        getFavoriteCharacters()
    }

    fun onEvent(event: FavoriteCharacterEvent) {
        when (event) {
            is FavoriteCharacterEvent.UnfavoriteCharacter -> {
                viewModelScope.launch {
                    deleteFavoriteCharacterUseCase.invoke(event.character)
                }
            }
        }
    }

    private fun getFavoriteCharacters() {
        getFavoriteCharactersUseCase.invoke()
            .onEach { characters ->
                _state.value = state.value.copy(
                    characters = characters,
                )
            }
            .launchIn(viewModelScope)
    }
}