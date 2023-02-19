package com.example.basemathgame.di

import android.app.Application
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.presentation.GameFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: GameFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            @BindsInstance level: Level
        ): ApplicationComponent
    }
}