package com.shaunhossain.nawspaperwithhilt.di

import com.shaunhossain.nawspaperwithhilt.network.ApiService
import com.shaunhossain.nawspaperwithhilt.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provideDataRepository(apiService: ApiService): DataRepository {
       return DataRepository(apiService)
    }

}