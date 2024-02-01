package br.com.francielilima.marvelcharacters.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Character(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    var isFavorite: Boolean,
)