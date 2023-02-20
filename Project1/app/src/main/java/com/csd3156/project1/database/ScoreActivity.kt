package com.csd3156.project1.database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.csd3156.project1.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}