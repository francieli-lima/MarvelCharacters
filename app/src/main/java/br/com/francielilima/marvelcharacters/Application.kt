package br.com.francielilima.marvelcharacters

import android.app.Application
import br.com.francielilima.marvelcharacters.di.appModule
import br.com.francielilima.marvelcharacters.di.databaseModule
import br.com.francielilima.marvelcharacters.di.networkModule
import br.com.francielilima.marvelcharacters.di.repositoryModule
import br.com.francielilima.marvelcharacters.di.useCasesModule
import br.com.francielilima.marvelcharacters.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(appModule, viewModelModule, repositoryModule, networkModule, useCasesModule, databaseModule)
        }
    }
}