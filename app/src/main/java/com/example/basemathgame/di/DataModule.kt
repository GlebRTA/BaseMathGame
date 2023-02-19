package com.example.basemathgame.di

import com.example.basemathgame.data.GameRepositoryImpl
import com.example.basemathgame.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindsRepository(impl: GameRepositoryImpl): GameRepository

    companion object {

        /*@Provides
        fun provideRepository(impl: GameRepositoryImpl): GameRepository {
            return GameRepositoryImpl
        }*/
    }
}