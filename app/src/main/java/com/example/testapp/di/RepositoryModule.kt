package com.example.testapp.di

import com.example.testapp.data.repository.PostRepository
import com.example.testapp.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(repositoryImpl: PostRepositoryImpl): PostRepository
}
