package br.com.francielilima.marvelcharacters.presentation.character_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.domain.use_case.get_character.GetCharacterByIdUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private var id: Int,
) : ViewModel() {

    private val _state = mutableStateOf(CharacterDetailState())
    val state: State<CharacterDetailState> = _state

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            CharacterDetailEvent.Reload -> {
                getCharacter(id)
            }
        }
    }

    private fun getCharacter(id: Int) {
        getCharacterByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { character ->
                        _state.value = CharacterDetailState(character = character)
                    }
                }

                is Resource.Error -> {
                    _state.value = CharacterDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CharacterDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}