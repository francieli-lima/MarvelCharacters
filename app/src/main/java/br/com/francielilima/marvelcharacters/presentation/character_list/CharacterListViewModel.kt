package br.com.francielilima.marvelcharacters.presentation.character_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.AddFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.DeleteFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val deleteFavoriteCharacterUseCase: DeleteFavoriteCharacterUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CharacterListState())
    val state: State<CharacterListState> = _state

    init {
        getCharacters()
    }

    fun onEvent(event: CharacterEvent) {
        when (event) {
            is CharacterEvent.FavoriteCharacter -> {
                viewModelScope.launch {
                    addFavoriteCharacterUseCase.invoke(event.character)
                }
            }

            is CharacterEvent.UnfavoriteCharacter -> {
                viewModelScope.launch {
                    deleteFavoriteCharacterUseCase.invoke(event.character)
                }
            }
        }
    }

    private fun getCharacters() {
        getCharactersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CharacterListState(characters = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = CharacterListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CharacterListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}