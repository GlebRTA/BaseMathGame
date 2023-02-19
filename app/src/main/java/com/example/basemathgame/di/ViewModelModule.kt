package com.example.basemathgame.di

import androidx.lifecycle.ViewModel
import com.example.basemathgame.presentation.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @ViewModelKey(GameViewModel::class)
    @IntoMap
    fun bindGameViewModel(impl: GameViewModel): ViewModel
}