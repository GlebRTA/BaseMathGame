package com.example.basemathgame.domain.usecases

import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSetting {
        return repository.getGameSettings(level)
    }
}