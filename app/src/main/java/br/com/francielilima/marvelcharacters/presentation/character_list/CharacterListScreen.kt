package br.com.francielilima.marvelcharacters.presentation.character_list

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import br.com.francielilima.marvelcharacters.presentation.Screen
import br.com.francielilima.marvelcharacters.presentation.character_list.components.CharacterListItem
import br.com.francielilima.marvelcharacters.presentation.components.OnLifecycleEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel
) {
    val state = viewModel.state.value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(CharacterEvent.Reload)
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(4.dp)
    ) {
        Box {
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
                                        if (character.isFavorite) {
                                            viewModel.onEvent(
                                                CharacterEvent.UnfavoriteCharacter(
                                                    character
                                                )
                                            )
                                        } else {
                                            viewModel.onEvent(
                                                CharacterEvent.FavoriteCharacter(
                                                    character
                                                )
                                            )
                                        }
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
                Row(
                    modifier = Modifier
                ) {
                    var text by remember { mutableStateOf("") }

                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                            viewModel.onEvent(CharacterEvent.Search(it))
                        },
                        label = { Text("Search") },
                        modifier = Modifier
                            .weight(4F)
                    )

                    IconButton(
                        modifier = Modifier
                            .weight(1F)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            navController.navigate(Screen.FavoriteCharacterScreen.route)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            tint = Color.White,
                            contentDescription = "Favorites"
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
}