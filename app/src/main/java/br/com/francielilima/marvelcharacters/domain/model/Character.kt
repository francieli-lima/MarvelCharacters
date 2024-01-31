package br.com.francielilima.marvelcharacters.domain.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val comics: List<String>,
    val events: List<String>,
    val series: List<String>
)