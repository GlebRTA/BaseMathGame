package com.example.basemathgame.domain.repository

import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSetting
}