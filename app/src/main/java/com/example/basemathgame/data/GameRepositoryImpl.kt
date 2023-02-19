package com.example.basemathgame.data

import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.domain.entity.Question
import com.example.basemathgame.domain.repository.GameRepository
import javax.inject.Inject
import kotlin.random.Random

class GameRepositoryImpl @Inject constructor() : GameRepository {

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = maxOf(rightAnswer - countOfOptions, MIN_SUM_VALUE)
        val to = minOf(rightAnswer + countOfOptions, maxSumValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.shuffled())
    }

    override fun getGameSettings(level: Level): GameSetting {
        return when (level) {
            Level.TEST -> {
                GameSetting(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY -> {
                GameSetting(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.MEDIUM -> {
                GameSetting(
                    50,
                    20,
                    80,
                    50
                )
            }
            Level.HARD -> {
                GameSetting(
                    100,
                    30,
                    90,
                    40
                )
            }
        }
    }

    companion object {
        private const val MIN_SUM_VALUE = 2
        private const val MIN_ANSWER_VALUE = 1
    }

}