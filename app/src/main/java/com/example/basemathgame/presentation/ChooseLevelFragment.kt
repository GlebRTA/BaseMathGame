package com.example.basemathgame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basemathgame.R
import com.example.basemathgame.databinding.FragmentChoosLevelBinding
import com.example.basemathgame.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChoosLevelBinding? = null
    private val binding: FragmentChoosLevelBinding
        get() = _binding ?: throw RuntimeException("Fragment welcome binding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoosLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightLevel()
    }

    private fun launchRightLevel() {
        with(binding) {
            btnLvlTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            btnLvlEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            btnLvlMedium.setOnClickListener {
                launchGameFragment(Level.MEDIUM)
            }
            btnLvlHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }

    }

    private fun launchGameFragment(level: Level) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance() = ChooseLevelFragment()
    }
}