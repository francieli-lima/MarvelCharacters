package br.com.francielilima.marvelcharacters.presentation.favorite_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.francielilima.marvelcharacters.presentation.Screen
import br.com.francielilima.marvelcharacters.presentation.character_list.components.CharacterListItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteListScreen(
    navController: NavController,
    viewModel: FavoriteListViewModel
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favoritados",
                        color = Color.White,
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        run {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else {
                        null
                    }
                }

            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(start = 4.dp, end = 4.dp, bottom = 4.dp, top = 68.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.weight(15F)
                    ) {
                        if (state.characters.isNotEmpty()) {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Fixed(1)
                            ) {
                                items(state.characters) { character ->
                                    CharacterListItem(
                                        character = character,
                                        onItemClick = {
                                            navController.navigate(Screen.CharacterDetailsScreen.route + "/${character.id}")
                                        },
                                        onFavorite = {
                                            viewModel.onEvent(
                                                FavoriteCharacterEvent.UnfavoriteCharacter(
                                                    character
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "Sem resultados.",
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            )
                        }
                    }
                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}