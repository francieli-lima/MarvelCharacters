package br.com.francielilima.marvelcharacters.presentation.character_detail

sealed class CharacterDetailEvent {
    object Reload : CharacterDetailEvent()
}