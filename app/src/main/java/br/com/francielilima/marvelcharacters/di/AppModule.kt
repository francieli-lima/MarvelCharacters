package br.com.francielilima.marvelcharacters.di

import android.util.Config.DEBUG
import br.com.francielilima.marvelcharacters.common.Constants
import br.com.francielilima.marvelcharacters.data.repository.MarvelRepositoryImpl
import br.com.francielilima.marvelcharacters.data.remote.MarvelApi
import br.com.francielilima.marvelcharacters.domain.repository.MarvelRepository
import br.com.francielilima.marvelcharacters.domain.use_case.get_characters.GetCharactersUseCase
import br.com.francielilima.marvelcharacters.presentation.character_list.CharacterListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

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
        CharacterListViewModel(get())
    }
}

val useCasesModule = module {
    single { GetCharactersUseCase(get())}
}
