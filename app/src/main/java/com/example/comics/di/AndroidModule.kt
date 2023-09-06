package com.example.comics.di

import com.example.comics.data.remote.Api
import com.example.comics.repository.ComicsRepository
import com.example.comics.viewmodel.ComicsViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    factory { provideRetrofit() }
    factory { provideApi(get()) }
    single { ComicsRepository(get()) }
    factory { ComicsViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("https://gateway.marvel.com/v1/public/")
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)