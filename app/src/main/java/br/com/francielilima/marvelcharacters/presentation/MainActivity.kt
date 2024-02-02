package br.com.francielilima.marvelcharacters.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.francielilima.marvelcharacters.presentation.character_detail.CharacterDetailScreen
import br.com.francielilima.marvelcharacters.presentation.character_detail.CharacterDetailViewModel
import br.com.francielilima.marvelcharacters.presentation.character_list.CharacterListScreen
import br.com.francielilima.marvelcharacters.presentation.character_list.CharacterListViewModel
import br.com.francielilima.marvelcharacters.presentation.favorite_list.FavoriteListScreen
import br.com.francielilima.marvelcharacters.presentation.favorite_list.FavoriteListViewModel
import br.com.francielilima.marvelcharacters.presentation.ui.theme.MarvelCharactersTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelCharactersTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.CharacterListScreen.route
                ) {
                    composable(
                        route = Screen.CharacterListScreen.route
                    ) {
                        val viewModel by viewModel<CharacterListViewModel>()
                        CharacterListScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    composable(
                        route = Screen.CharacterDetailsScreen.route + "/{id}"
                    ) {
                        val viewModel = getViewModel<CharacterDetailViewModel>(
                            parameters = {
                                parametersOf(navController.currentBackStackEntry?.arguments)
                            }
                        )

                        CharacterDetailScreen(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }

                    composable(
                        route = Screen.FavoriteCharacterScreen.route
                    ) {
                        val viewModel by viewModel<FavoriteListViewModel>()
                        FavoriteListScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}