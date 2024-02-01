package br.com.francielilima.marvelcharacters.di

import android.os.Bundle
import androidx.room.Room
import br.com.francielilima.marvelcharacters.common.Constants
import br.com.francielilima.marvelcharacters.data.data_source.MarvelDatabase
import br.com.francielilima.marvelcharacters.data.remote.MarvelApi
import br.com.francielilima.marvelcharacters.data.repository.FavoriteCharacterRepositoryImpl
import br.com.francielilima.marvelcharacters.data.repository.MarvelRepositoryImpl
import br.com.francielilima.marvelcharacters.domain.repository.FavoriteCharacterRepository
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.AddFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.DeleteFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.GetFavoriteCharacterUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.favorite_character.GetFavoriteCharactersUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.get_character.GetCharacterByIdUseCase
import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase
import br.com.francielilima.marvelcharacters.presentation.character_detail.CharacterDetailViewModel
import br.com.francielilima.marvelcharacters.presentation.character_list.CharacterListViewModel
import br.com.francielilima.marvelcharacters.presentation.favorite_list.FavoriteListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    fun provideApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }
    single { provideApi(get()) }
}

val repositoryModule = module {

    fun provideMarvelRepository(
        marvelApi: MarvelApi
    ): MarvelRepository {
        return MarvelRepositoryImpl(marvelApi)
    }

    single { provideMarvelRepository(get()) }

    fun provideFavoriteRepository(
        database: MarvelDatabase
    ): FavoriteCharacterRepository {
        return FavoriteCharacterRepositoryImpl(database.characterDao)
    }

    single { provideFavoriteRepository(get()) }
}

val networkModule = module {

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        provideRetrofit(get(), Constants.BASE_URL)
    }
}

val viewModelModule = module {

    viewModel {
        CharacterListViewModel(get(), get(), get())
    }
    viewModel { (bundle: Bundle) ->
        CharacterDetailViewModel(get(), bundle)
    }
    viewModel {
        FavoriteListViewModel(get(), get())
    }
}

val useCasesModule = module {
    single { GetCharactersUseCase(get()) }
    single { GetCharacterByIdUseCase(get()) }
    single { AddFavoriteCharacterUseCase(get()) }
    single { DeleteFavoriteCharacterUseCase(get()) }
    single { GetFavoriteCharacterUseCase(get()) }
    single { GetFavoriteCharactersUseCase(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MarvelDatabase::class.java,
            MarvelDatabase.DATABASE_NAME
        ).build()
    }
}
