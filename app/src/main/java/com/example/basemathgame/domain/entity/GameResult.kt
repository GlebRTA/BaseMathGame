package com.example.basemathgame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSetting: GameSetting
) : Parcelable {

    val countPercentOfRightAnswers: Int
        get() {
            return try {
                countOfRightAnswers * 100 / countOfQuestions
            } catch (e: java.lang.Exception) {
                0
            }
        }
}