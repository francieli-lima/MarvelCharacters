package br.com.francielilima.marvelcharacters.domain.use_case.get_character

import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.data.remote.dto.toCharacter
import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCharacterByIdUseCase(
    private val repository: MarvelRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            val character = repository.getCharacterById(id).toCharacter()
            emit(Resource.Success(character))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Ocorreu um erro inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("Falha na conexão com a internet."))
        }
    }
}