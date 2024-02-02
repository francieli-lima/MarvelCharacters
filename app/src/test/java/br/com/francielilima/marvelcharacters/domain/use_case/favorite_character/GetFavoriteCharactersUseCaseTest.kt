package br.com.francielilima.marvelcharacters.domain.use_case.favorite_character

import br.com.francielilima.marvelcharacters.common.Resource
import br.com.francielilima.marvelcharacters.domain.model.Character
import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFavoriteCharactersUseCaseTest {

    private var getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase = mockk()


    private val characters = listOf(
        Character(1100, "Hulk", "Very green", "thumb.url", false)
    )

    @Test
    fun verifyIfCharactersAreLoading() {
        coEvery { getFavoriteCharactersUseCase.invoke() } returns flowOf(characters)

        runBlocking {
            val result = getFavoriteCharactersUseCase.invoke().first()
            TestCase.assertEquals(result, characters)
        }
    }

}