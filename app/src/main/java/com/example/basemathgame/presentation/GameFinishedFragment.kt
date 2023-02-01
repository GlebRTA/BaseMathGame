package com.example.basemathgame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.basemathgame.R
import com.example.basemathgame.databinding.FragmentChoosLevelBinding
import com.example.basemathgame.databinding.FragmentGameFinishedBinding
import com.example.basemathgame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy {
        args.gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("Fragment welcome binding == null")

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

    private fun restartGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}