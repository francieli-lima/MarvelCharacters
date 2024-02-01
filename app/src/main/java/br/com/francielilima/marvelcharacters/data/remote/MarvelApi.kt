package br.com.francielilima.marvelcharacters.data.remote

import br.com.francielilima.marvelcharacters.data.remote.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String,
    ): Result

    @GET("v1/public/characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: Long,
        @Query("hash") hash: String,
    ): Result
}

data class Result(val data: Data)
data class Data(val results: List<CharacterDto>)