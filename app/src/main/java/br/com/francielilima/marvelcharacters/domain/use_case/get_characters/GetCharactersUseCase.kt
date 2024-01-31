package br.com.francielilima.marvelcharacters.domain.use_case.get_characters

import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.data.remote.dto.toCharacter
import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharactersUseCase(
    private val repository: MarvelRepository
) {
    operator fun invoke(): Flow<Resource<List<Character>>> = flow {
        try {
            emit(Resource.Loading())
            val characters = repository.getCharacters()?.map { it.toCharacter() }
            emit(Resource.Success(characters ?: emptyList()))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}