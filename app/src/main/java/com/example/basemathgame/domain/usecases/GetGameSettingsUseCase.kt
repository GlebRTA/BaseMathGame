package com.example.basemathgame.domain.usecases

import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.domain.repository.GameRepository
import javax.inject.Inject

class GetGameSettingsUseCase @Inject constructor(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSetting {
        return repository.getGameSettings(level)
    }
}