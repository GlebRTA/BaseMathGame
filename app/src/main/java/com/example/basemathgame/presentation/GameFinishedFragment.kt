package com.example.basemathgame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.basemathgame.R
import com.example.basemathgame.databinding.FragmentChoosLevelBinding
import com.example.basemathgame.databinding.FragmentGameFinishedBinding
import com.example.basemathgame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("Fragment welcome binding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResultText()
        restartGameByBackBtn()
        restartGameByUnderstandBtn()

    }

    private fun initResultText() = with(binding) {
        ivResultLogo.setImageResource(getIconId())
        tvRequiredAnswers.text = String.format(
            getString(R.string.required_number_answer),
            gameResult.gameSetting.minCountOfRightAnswers
        )
        tvSourceAnswers.text = String.format(
            getString(R.string.score),
            gameResult.countOfRightAnswers
        )
        tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            gameResult.gameSetting.minPercentOfRightAnswers
        )
        tvSourcePercentage.text = String.format(
            getString(R.string.your_percentage),
            gameResult.countPercentOfRightAnswers
        )
    }

    private fun getIconId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_win
        } else {
            R.drawable.ic_lose
        }
    }

    private fun restartGameByUnderstandBtn() {
        binding.btnUnderstand.setOnClickListener {
            restartGame()
        }
    }

    private fun restartGameByBackBtn() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    restartGame()
                }
            })
    }

    private fun restartGame() {
        requireActivity()
            .supportFragmentManager
            .popBackStack(WelcomeFragment.BACKSTACK_WELCOME, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult) = GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_GAME_RESULT, gameResult)
            }
        }
    }
}