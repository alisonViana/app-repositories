package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.database.FavoriteDatabase
import br.com.dio.app.repositories.data.repositories.FavoriteRepository
import br.com.dio.app.repositories.data.repositories.RepoRepository
import br.com.dio.app.repositories.data.repositories.RepoRepositoryInterface
import br.com.dio.app.repositories.data.services.GitHubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val OK_HTTP = "OK_HTTP"

    fun load() {
        loadKoinModules(networkModules()
                + daoModule()
                + repositoriesModules())
    }

    private fun networkModules(): Module {
        return module {
            // single - o koin devolve sempre a mesma instância
            // factory - o koin devolve uma instância nova a cada chamada
            single {
                val interceptor = HttpLoggingInterceptor{
                    Log.i(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GitHubService>(get(), get())
            }
        }
    }

    private fun repositoriesModules(): Module {
        return module {
            single<RepoRepositoryInterface> {RepoRepository(get())}
            single { FavoriteRepository(get()) }
        }
    }

    private fun daoModule(): Module {
        return module {
            single { FavoriteDatabase.getInstance(androidContext()).favoriteDao }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}