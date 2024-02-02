package br.com.francielilima.marvelcharacters.presentation.character_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.AddFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.DeleteFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.GetFavoriteCharactersUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val deleteFavoriteCharacterUseCase: DeleteFavoriteCharacterUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CharacterListState())
    val state: State<CharacterListState> = _state

    private var completeCharactersList = listOf<Character>()

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

            is CharacterEvent.Search -> {
                val list = completeCharactersList.filter { it.name.lowercase().startsWith(event.search)}
                _state.value = CharacterListState(characters = list)
            }
        }
    }

    private fun getCharacters() {
        var favoriteList = listOf<Character>()

        getFavoriteCharactersUseCase.invoke().onEach {
            favoriteList = it
        }.launchIn(viewModelScope)

        getCharactersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val characterList = result.data ?: emptyList()
                    characterList.map { character ->
                        character.isFavorite = favoriteList.any { favoriteCharacter -> favoriteCharacter.id == character.id}
                        character
                    }

                    completeCharactersList = characterList
                    _state.value = CharacterListState(characters = characterList)
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