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
                    startDestination = "characters_list"
                ) {
                    composable(
                        route = "characters_list"
                    ) {
                        val viewModel by viewModel<CharacterListViewModel>()
                        CharacterListScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    composable(
                        route = "character_detail" + "/{id}"
                    ) {
                        val viewModel = getViewModel<CharacterDetailViewModel>(
                            parameters = {
                                parametersOf(navController.currentBackStackEntry?.arguments)
                            }
                        )

                        CharacterDetailScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}