package br.com.francielilima.marvelcharacters.presentation.character_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.francielilima.marvelcharacters.domain.model.Character
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnrememberedMutableState")
@Composable
fun CharacterListItem(
    character: Character,
    onItemClick: (Character) -> Unit,
    onFavorite: (Character) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(character) }
                .padding(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .weight(1F)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .weight(4F)
            ) {

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                Text(
                    text = character.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
            }

            val isFavorite = mutableStateOf(character.isFavorite)

            IconButton(
                modifier = Modifier
                    .weight(1F),
                onClick = {
                    onFavorite.invoke(character)
                    isFavorite.value = !isFavorite.value
                },
            ) {
                Icon(
                    imageVector = if (isFavorite.value) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    tint = Color.White,
                    contentDescription = "Favorite"
                )
            }
        }
        Divider(
            color = Color(0xFF222222),
            modifier = Modifier
                .padding(4.dp)
                .height(1.dp)

        )
    }
}