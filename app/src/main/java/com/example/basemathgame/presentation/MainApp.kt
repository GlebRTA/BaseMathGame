package com.example.basemathgame.presentation

import android.app.Application
import com.example.basemathgame.di.ApplicationComponent
import com.example.basemathgame.di.DaggerApplicationComponent
import com.example.basemathgame.domain.entity.Level

class MainApp : Application() {

    fun getComponent(level: Level): ApplicationComponent {
        return DaggerApplicationComponent
                .factory()
                .create(this, level)
    }
}