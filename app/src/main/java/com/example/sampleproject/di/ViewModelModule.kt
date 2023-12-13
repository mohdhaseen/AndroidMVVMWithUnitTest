package com.example.sampleproject.di

import com.example.sampleproject.api.ApiService
import com.example.sampleproject.model.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}
