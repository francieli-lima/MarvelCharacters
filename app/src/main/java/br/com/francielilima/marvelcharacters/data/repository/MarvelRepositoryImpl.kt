package br.com.francielilima.marvelcharacters.data.repository

import br.com.francielilima.marvelcharacters.data.remote.MarvelApi
import br.com.francielilima.marvelcharacters.data.remote.dto.CharacterDto
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import java.security.MessageDigest

class MarvelRepositoryImpl(
    private val api: MarvelApi
) : MarvelRepository {

    override suspend fun getCharacters(): List<CharacterDto>? {
       return api.getCharacters()?.data?.results
    }
}