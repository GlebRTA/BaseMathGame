package com.example.basemathgame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basemathgame.R
import com.example.basemathgame.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}