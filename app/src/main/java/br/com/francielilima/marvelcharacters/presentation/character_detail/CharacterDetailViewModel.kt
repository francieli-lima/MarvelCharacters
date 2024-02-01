package br.com.francielilima.marvelcharacters.presentation.character_detail

import android.os.Bundle
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
    arguments: Bundle,
) : ViewModel() {

    private val _state = mutableStateOf(CharacterDetailState())
    val state: State<CharacterDetailState> = _state

    init {
        getCharacter(arguments.getString("id")?.toInt() ?: 0)
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