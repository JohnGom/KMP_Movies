package org.johngom.movieskmp

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.johngom.movieskmp.data.MoviesRepository
import org.johngom.movieskmp.data.RegionRepository
import org.johngom.movieskmp.data.remote.MoviesService
import org.johngom.movieskmp.data.database.MovieDatabase
import org.johngom.movieskmp.data.database.MoviesDAO
import org.johngom.movieskmp.ui.screens.detail.DetailViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.screens.home.HomeViewModel

val appModule = module {
    single(named("apiKey")) { BuildConfig.API_KEY }
    single<MoviesDAO> {
        val dbBuilder = get<RoomDatabase.Builder<MovieDatabase>>()
        dbBuilder.setDriver(BundledSQLiteDriver()).build().moviesDAO()
    }
}

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", get(named("apiKey")))
                }
            }
        }
    }
    factoryOf(::MoviesRepository)
    factoryOf(::RegionRepository)
    factoryOf(::MoviesService)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, dataModule, viewModelModule, nativeModule)
    }
}