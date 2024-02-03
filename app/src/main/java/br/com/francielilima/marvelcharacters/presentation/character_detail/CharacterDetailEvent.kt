package br.com.francielilima.marvelcharacters.presentation.character_detail

sealed class CharacterDetailEvent {
    data class Reload(val id: Int) : CharacterDetailEvent()
}