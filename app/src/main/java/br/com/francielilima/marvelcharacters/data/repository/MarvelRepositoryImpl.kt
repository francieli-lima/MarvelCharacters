package br.com.francielilima.marvelcharacters.data.repository

import br.com.francielilima.marvelcharacters.common.Constants
import br.com.francielilima.marvelcharacters.data.remote.MarvelApi
import br.com.francielilima.marvelcharacters.data.remote.dto.CharacterDto
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import java.security.MessageDigest
import java.util.Date

class MarvelRepositoryImpl(
    private val api: MarvelApi
) : MarvelRepository {

    override suspend fun getCharacters(): List<CharacterDto> {
        val date = Date()

        return api.getCharacters(
            apiKey = Constants.PUBLIC_KEY,
            timestamp = date.time,
            hash = "${date.time}${Constants.PRIVATE_KEY}${Constants.PUBLIC_KEY}".md5()
        ).data.results
    }

    override suspend fun getCharacterById(id: Int): CharacterDto {
        val date = Date()

        return api.getCharacterById(
            apiKey = Constants.PUBLIC_KEY,
            timestamp = date.time,
            hash = "${date.time}${Constants.PRIVATE_KEY}${Constants.PUBLIC_KEY}".md5(),
            id = id
        ).data.results.first()
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    val hashBytes = messageDigest.digest(this.toByteArray(Charsets.UTF_8))
    return hashBytes.toHexString()
}