package com.locotoinnovations.composelearnings.network

import com.locotoinnovations.composelearnings.network.posts.TodoService
import com.locotoinnovations.composelearnings.ui.screen.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Network {

    private const val API_PROD = "https://60ab48uiy3.execute-api.us-east-1.amazonaws.com/dev/"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(API_PROD)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesPostService(retrofit: Retrofit): TodoService {
        return retrofit.create(TodoService::class.java)
    }

    @Provides
    @Singleton
    fun providesPostRepository(todoService: TodoService): TodoRepository {
        return TodoRepository(todoService)
    }
}