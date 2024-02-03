package br.com.francielilima.marvelcharacters.presentation

sealed class Screen(val route: String) {
    object CharacterListScreen: Screen("character_list_screen")
    object CharacterDetailsScreen: Screen("character_details_screen")
    object FavoriteCharacterScreen: Screen("favorite_character_screen")
}