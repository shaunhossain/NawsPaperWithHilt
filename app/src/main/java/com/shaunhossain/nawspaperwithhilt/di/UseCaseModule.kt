package com.shaunhossain.nawspaperwithhilt.di

import com.shaunhossain.nawspaperwithhilt.repository.DataRepository
import com.shaunhossain.nawspaperwithhilt.usecase.ArticleDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object UseCaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provideUseCase(dataRepository: DataRepository): ArticleDataUseCase {
        return ArticleDataUseCase(dataRepository)
    }
}