package com.example.basemathgame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.basemathgame.R
import com.example.basemathgame.databinding.FragmentGameBinding
import com.example.basemathgame.databinding.FragmentGameFinishedBinding
import com.example.basemathgame.domain.entity.GameResult
import com.example.basemathgame.domain.entity.GameSetting
import com.example.basemathgame.domain.entity.Level
import javax.inject.Inject

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    /*private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }*/

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    private val component by lazy {
        (requireActivity().application as MainApp).getComponent(args.level)
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("Fragment welcome binding == null")


    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGame()
    }

    private fun initGame() {
        setTimer()
        initQuestion()
        initStatistics()
        finishGame()
        checkCorrectAnswerListener()
    }

    private fun checkCorrectAnswerListener() {
        for (option in tvOptions) {
            option.setOnClickListener {
                viewModel.chooseAnswer(option.text.toString().toInt())
            }
        }
    }

    private fun setTimer() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
    }

    private fun initQuestion() {
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvVisibleNumber.text = it.visibleNumber.toString()
                for ((i, view) in tvOptions.withIndex()) {
                    view.text = it.options[i].toString()
                }
            }
        }
    }

    private fun initStatistics() = with(viewModel) {
        percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }

        enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }

        progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun getColorByState(state: Boolean): Int {
        val colorId =
            if (state) android.R.color.holo_green_light else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorId)
    }

    private fun finishGame() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment2(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}