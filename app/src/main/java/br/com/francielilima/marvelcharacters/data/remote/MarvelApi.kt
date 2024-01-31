package br.com.francielilima.marvelcharacters.data.remote

import br.com.francielilima.marvelcharacters.data.remote.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.MessageDigest

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacters(): Query?
}

data class Query(val data: Data)
data class Data(val results: List<CharacterDto>)