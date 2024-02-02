package br.com.francielilima.marvelcharacters.domain.use_case.get_characters

import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.domain.model.Character
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCharactersUseCaseTest  {

    private var getCharactersUseCase: GetCharactersUseCase = mockk()

    private val characters = listOf(
        Character(1100, "Hulk", "Very green", "thumb.url", false)
    )

    @Test
    fun verifyIfCharactersAreLoading() {
        coEvery { getCharactersUseCase.invoke() } returns flowOf(Resource.Success(data = characters))

        runBlocking {
            val result = getCharactersUseCase.invoke().first()
            assertEquals(result.data, characters)
        }
    }

    @Test
    fun verifyIfErrorMessageIsComing() {
        coEvery { getCharactersUseCase.invoke() } returns flowOf(Resource.Error(message = "Problem"))

        runBlocking {
            val result = getCharactersUseCase.invoke().first()
            assertEquals(result.message, "Problem")
        }
    }
}