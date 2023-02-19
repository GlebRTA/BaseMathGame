package com.example.basemathgame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basemathgame.R
import com.example.basemathgame.data.GameRepositoryImpl
import com.example.basemathgame.domain.entity.GameResult
import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import com.example.basemathgame.domain.entity.Question
import com.example.basemathgame.domain.usecases.GenerateQuestionUseCase
import com.example.basemathgame.domain.usecases.GetGameSettingsUseCase
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val generateQuestionUseCase: GenerateQuestionUseCase,
    private val getGameSettingsUseCase: GetGameSettingsUseCase,
    private val application: Application,
    private val level: Level
) : ViewModel() {

    private lateinit var gameSetting: GameSetting

    private var timer: CountDownTimer? = null

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers


    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.correct_answers),
            countOfRightAnswers,
            gameSetting.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSetting.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSetting.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) return 0

        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) countOfRightAnswers++
        countOfQuestions++
    }

    private fun getGameSettings() {
        this.gameSetting = getGameSettingsUseCase(level)
        _minPercent.value = gameSetting.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSetting.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millsUntilFinished: Long) {
                _formattedTime.value = formatTime(millsUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun formatTime(millsUntilFinished: Long): String {
        val sec = millsUntilFinished / MILLIS_IN_SECONDS
        val min = sec / SECONDS_IN_MINUTES
        val leftSec = sec - (min * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", min, leftSec)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSetting
        )
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

    }
}